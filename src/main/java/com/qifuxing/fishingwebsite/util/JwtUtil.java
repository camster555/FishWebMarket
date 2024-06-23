package com.qifuxing.fishingwebsite.util;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.JwtErrorException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.Date;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This util class is for JWT services.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-16
 * @version 1.0.0
 */


@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String jwtKey;
    @Value("${jwt.expirationMs}")
    private int jwtExpireTime;

    private Key signInKeyDecoded(){
        //decoding back to original binary data
        byte[] keyBytes = Decoders.BASE64.decode(jwtKey);
        //method takes the byte array (decoded key) and creates a javax.crypto.SecretKey suitable for use with HMAC-SHA algorithms.
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpireTime))
                .signWith(signInKeyDecoded())
                .compact();
    }

    //verify the token's structurally valid, signed correctly, and not expired but does not verify user.
    public boolean validateJwtToken(String authToken){
        try {
            //builds the machine : then sets the key to the machine : finish the machine ready to use : use machine
            //to validate the given token against the key already set in the machine.
            //Jws also checks for signature but if we use 'parseClaimsJwt' it does not check for signature.
            Jwts.parserBuilder().setSigningKey(signInKeyDecoded()).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e){
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtErrorException("Invalid JWT signature" + e);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtErrorException("JWT token is expired" + e);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtErrorException("JWT token is unsupported"+ e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtErrorException("JWT claims string is empty"+ e);
        }
    }

    //don't need exception here because this method is intended to be used when token is already validated.
    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signInKeyDecoded())
                .build()
                .parseClaimsJws(token)
                //gets the body(payload) where the claims are store in JWT which contains the username that is needed
                .getBody()
                //this part gets the username
                .getSubject();
    }
}
