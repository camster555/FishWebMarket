package com.qifuxing.fishingwebsite.security;

import com.qifuxing.fishingwebsite.exception.JwtErrorException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.service.CustomUserDetailsService;
import com.qifuxing.fishingwebsite.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This filters HTTP request and authenticate token.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-16
 * @version 1.0.0
 */

//needs to have doFilterInternal for 'OncePerRequestFilter' abstract class.
//'OncePerRequestFilter' this is so every request is executed once only.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Authorization which is in the header of the request that contains JWT token credentials with prefix 'Bearer'.
        final String header = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        //When you extract the token from the Authorization header, you are getting the entire JWT.
        if (header != null && header.startsWith("Bearer ")){

            // http request will look like 'Authorization: Bearer <your-jwt-token-here>' so after 7 index our jwt
            //token starts.
            jwtToken = header.substring(7);

            try {
                username = jwtUtil.getUserNameFromJwtToken(jwtToken);
            } catch (Exception e){
                logger.error("Cannot set user authentication: {}", e.getMessage());
                throw new JwtErrorException("Username not found in token" + e);
            }

        }

        //if username is found and also the token is not authenticated yet then start process to authenticate.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //first get all the details of the user
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            //then validate if the token with this user is valid
            if (jwtUtil.validateJwtToken(jwtToken)){

                // This object is required by Spring Security to represent the user's authentication state.
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Setting additional details in the token, such as the IP address and session ID.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //Once this is done, Spring Security knows that the user is authenticated and has the necessary roles/authorities.
                //This is how Spring knows to allow or deny access to resources based on the user's authentication status.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //Validate JWT token. -> Set up authentication. -> Pass request to the next filter.
        filterChain.doFilter(request,response);
    }

}
