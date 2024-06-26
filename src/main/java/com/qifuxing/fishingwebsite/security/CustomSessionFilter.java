package com.qifuxing.fishingwebsite.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.service.CustomUserDetailsService;
import com.qifuxing.fishingwebsite.specificDTO.UserSessionIdDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This filters HTTP request and authenticate session id in cookie.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-22
 * @version 1.0.0
 */


@Component
public class CustomSessionFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomSessionFilter.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    protected boolean shouldFilter(HttpServletRequest request){
        String path = request.getRequestURI();
        return path.startsWith("/api/admin/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {

        if (!shouldFilter(request)){
            filterChain.doFilter(request,response);
            return;
        }

        logger.debug("Processing admin request to {}", request.getRequestURI());

        String sessionId = null;

        if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if ("SESSIONID".equals(cookie.getName())){
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        if (sessionId != null){

            //getting the value by getting the key
            String userValueJson = redisTemplate.opsForValue().get("Session:"+sessionId);

            if (userValueJson != null){

                //convert json back to userSessionDetails object
                ObjectMapper objectMapper = new ObjectMapper();
                UserSessionIdDetails userSessionIdDetails;
                try {
                    userSessionIdDetails = objectMapper.readValue(userValueJson, UserSessionIdDetails.class);
                } catch (JsonProcessingException e) {
                    throw new ResourceNotFoundException("Failed to change JSON object to UserSessionDetails object" +e);
                }

                String username = userSessionIdDetails.getUsername();
                String role = userSessionIdDetails.getRole();

                //creates an authentication token for the user "john_doe" with the role "CUSTOMER."
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                        (username, null, AuthorityUtils.createAuthorityList(role));

                //updates the security system's records to indicate that "john_doe" is authenticated as a CUSTOMER.
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request,response);
    }

}
