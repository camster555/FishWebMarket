package com.qifuxing.fishingwebsite.config;


import com.qifuxing.fishingwebsite.security.CustomSessionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.qifuxing.fishingwebsite.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.Cookie;
import java.io.IOException;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class configures security settings for the application.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    //NOTE, somehow when securityConfig class uses UserDetailsService interface, spring tries to create a default
    //implementation since UserDetailService is an interface and don't have any implementations so instance of the
    //interface classed can't be created, however the default one created by spring using ''InMemoryUserDetailsManager
    //makes the default also rely on SecurityConfig class which now creates a loop of
    // UserDetailsService -> SecurityConfig -> UserDetailsService which will produce error during runtime so the
    //solution is to create a custom class called CustomUserDetailsService so when spring calls, it won't call the default

    public SecurityConfig() {
        logger.info("SecurityConfig constructor called");
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomSessionFilter customSessionFilter;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //logger.info("Starting HttpSecurity configuration");

        // means that csrf token will be placed in a cookie repository that will be accessible in js
        http
                .addFilterBefore(customSessionFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )

                .addFilterAfter((request, response, filterChain) -> {
                    HttpServletRequest httpRequest = (HttpServletRequest) request;
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    Cookie[] cookies = httpRequest.getCookies();

                    if (cookies != null) {
                        for (Cookie cookie : cookies) {
                            if ("XSRF-TOKEN".equals(cookie.getName())) {
                                logger.info("Found XSRF-TOKEN cookie: Name={}, Value={}", cookie.getName(), cookie.getValue());
                                String newHeader = String.format(
                                        //need to custom set samesite to none or the default will be set to something else
                                        //and condition is that website is https and set to secure THEN can set samesite to none.
                                        "%s=%s; Path=%s; Max-Age=%d; Secure; %s SameSite=None",
                                        cookie.getName(),
                                        cookie.getValue(),
                                        cookie.getPath(),
                                        cookie.getMaxAge(),
                                        cookie.isHttpOnly() ? "HttpOnly;" : ""
                                );
                                httpResponse.addHeader("Set-Cookie", newHeader);
                            }
                        }
                    }

                    String username = httpRequest.getParameter("username");
                    String password = httpRequest.getParameter("password");
                    //logger.info("Received username: {}", username);
                    //logger.info("Received password: {}", password);

                    filterChain.doFilter(request, response);

                }, CsrfFilter.class)
                // start configuration for specific URL rules
                .authorizeRequests()
                .antMatchers("/", "/index.html", "/about.html", "/contact.html","/shop.html",
                        "/cart.html","/admin.html","/adminProduct.html","/sProduct.html","/resetP.html").permitAll()
                //remember to ADD '/' so its "/api/auth/register" and not "api/auth/register".
                .antMatchers("/api/auth/register","/api/auth/login","/api/product").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                //need to allow static files here or css won't apply.
                .antMatchers( "/img/**", "/style.css","/addProduct.js", "/authAPI.js",
                        "/admin.js", "/login.js", "/paginationP.js", "/popup.js", "/script.js", "/adminProduct.css",
                        "/styleAdmin.css", "/security.js").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login.html")
                    .permitAll();

        //logger.info("HttpSecurity configuration completed");
        return http.build();
    }

/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Starting HttpSecurity configuration");

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .httpBasic().disable()
                .formLogin().disable(); // Disable form-based login

        return http.build();
    }

 */

    @Bean
    //passwordEncoder interface main function are 'string encode' new password and also verifies it with 'boolean matches' method
    public PasswordEncoder passwordEncoder(){
        //uses hashing function to encode new passwords.
        return new BCryptPasswordEncoder();
    }

}
