package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.LoginFailedException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.exception.UsernameAlreadyExistsException;
import com.qifuxing.fishingwebsite.model.Admin;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.AdminRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service provides custom user details for authentication.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-23
 * @version 1.0.0
 */

//custom class needed to prevent loop in SecurityConfig class
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //logger.info("Received username: {}", username);

        // Check if the username is null or empty
        if (username == null || username.isEmpty()) {
            throw new ResourceNotFoundException("Invalid Input");
        }
        //logger.info("Attempting to load user by username: {}", username);
        User user = userRepository.findByUsername(username);
        //so user here is now fetched by using userRepository and then proceed to convert to UserDetails instance by
        //builder pattern if not null.
        if (user == null) {
            //logger.warn("User not found(Logger): {}", username);
            throw new UsernameAlreadyExistsException("Invalid username or password");
        }
        //below uses the builder pattern.
        //'org.springframework.security.core.userdetails.User.builder()' is a convenient way to create instance of
        //UserDetails since we need to return it.
        //logger.info("User found: {}", username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                //'USER' here is not the User class that's the table, it's a 'role' that gives me the ability to restrict
                //or allow access to certain parts of application
                .roles("USER")
                .build();
    }

    public UserDetails loadAdminByUsername(String username) throws UsernameNotFoundException {
        // Check if the username is null or empty
        if (username == null || username.isEmpty()) {
            throw new ResourceNotFoundException("Invalid Input");
        }
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword())
                .roles("ADMIN") // Default role for admin authentication
                .build();
    }
}