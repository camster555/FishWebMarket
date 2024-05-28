package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.LoginFailedException;
import com.qifuxing.fishingwebsite.exception.UsernameAlreadyExistsException;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.LoginDTO;
import com.qifuxing.fishingwebsite.specificDTO.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service manages authentication and registration logic.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-23
 * @version 1.0.0
 */

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void registerUser(UserRegistrationDTO userRegistrationDTO){
        //first checking if username already exist
        if(userRepository.findByUsername(userRegistrationDTO.getUsername())!= null){
            throw new UsernameAlreadyExistsException("User already registered");
        }

        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        //since it is configured in securityconfig class that passwords need to be encoded so if the password saved to
        //mysql database was plain, then when pass request sent from bruno is also plain then it wouldn't be correct
        // even if the plain password is correct so make sure when it's saved to sql it is also encoded.
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setEmail(userRegistrationDTO.getEmail());

        //saving new registered user to database, and it will auto generate id.
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public UserDetails authenticateUser(LoginDTO loginDTO){
    //    logger.info("Authenticating user: {}", loginDTO.getUsername());
        //get userdetail instance which contains the username and password
        //in 'loadUserByUsername' already checks for username in the database
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDTO.getUsername());
        //so the rest is to check for null and correct password
        if (userDetails == null || !passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){
    //        logger.warn("Invalid username or password for user: {}", loginDTO.getUsername());
            throw new LoginFailedException("Invalid username or password");
        }
    //    logger.info("User authenticated: {}", loginDTO.getUsername());
        return userDetails;
    }
}