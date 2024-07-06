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
import javax.servlet.http.Cookie;
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
        if (shouldNotFilter(request)){
            filterChain.doFilter(request,response);
            return;
        }

        try {
            String jwtToken = extractJwtTokenFromCookie(request);
            //if username is found and also the token is not authenticated yet then start process to authenticate.
            if (jwtToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                //then validate if the token with this user is valid
                if (jwtUtil.validateJwtToken(jwtToken)) {

                    String username = jwtUtil.getUserNameFromJwtToken(jwtToken);

                    //first get all the details of the user
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                    // This object is required by Spring Security to represent the user's authentication state.
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Setting additional details in the token, such as the IP address and session ID.
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //Once this is done, Spring Security knows that the user is authenticated and has the necessary roles/authorities.
                    //This is how Spring knows to allow or deny access to resources based on the user's authentication status.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    logger.debug("Authenticated user: setting security context " + username);
                } else {
                    logger.debug("Invalid JWT token");
                }
            } else {
                logger.debug("No JWT token found or authentication already set");
            }
        } catch (Exception e){
            logger.error("Cannot set authentication: " + e.getMessage());
        }

        //Validate JWT token. -> Set up authentication. -> Pass request to the next filter.
        filterChain.doFilter(request,response);
    }

    private String extractJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }

        return null;
    }

    private String extractJwtTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies){
                if ("JWT_TOKEN" .equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{

        String path = request.getRequestURI();

        return path.startsWith("/api/public") ||
                path.contains("index.html") ||
                path.contains("login.html") ||
                path.contains("shop.html") ||
                path.contains("about.html") ||
                path.contains("contact.html") ||
                path.contains("cart.html") ||
                path.contains("sProduct.html") ||
                path.contains("reset.html") ||
                path.startsWith("/api/admin") ||
                path.equals("/admin.html") ||
                path.equals("/adminProduct.html");
    }

}
