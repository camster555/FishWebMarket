package com.qifuxing.fishingwebsite.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qifuxing.fishingwebsite.exception.LoginFailedException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.exception.UsernameAlreadyExistsException;
import com.qifuxing.fishingwebsite.model.Admin;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.AdminRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    //                               CUSTOMER/USER LOGIC STARTS HERE

    private UserDTO convertToUserDto(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public UserDTO registerUser(UserRegistrationDTO userRegistrationDTO){

        // Check if any of the fields are empty or null
        if (userRegistrationDTO.getUsername() == null || userRegistrationDTO.getUsername().isEmpty()) {
            throw new ResourceNotFoundException("Invalid input");
        }
        if (userRegistrationDTO.getEmail() == null || userRegistrationDTO.getEmail().isEmpty()) {
            throw new ResourceNotFoundException("Invalid input");
        }
        if (userRegistrationDTO.getPassword() == null || userRegistrationDTO.getPassword().isEmpty()) {
            throw new ResourceNotFoundException("Invalid input");
        }

        //first checking if username already exist
        if(userRepository.findByUsername(userRegistrationDTO.getUsername())!= null){
            throw new UsernameAlreadyExistsException("User already registered");
        }
        if(userRepository.findByEmail(userRegistrationDTO.getEmail())!= null){
            throw new UsernameAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        //since it is configured in securityConfig class that passwords need to be encoded so if the password saved to
        //mysql database was plain, then when pass request sent from bruno is also plain then it wouldn't be correct
        // even if the plain password is correct so make sure when it's saved to sql it is also encoded.
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setEmail(userRegistrationDTO.getEmail());

        //saving new registered user to database, and it will auto generate id.
        User savedUser = userRepository.save(user);
        return convertToUserDto(savedUser);
    }

    public UserDetails authenticateUser(LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        //logger.info("Authenticating user: {}", loginDTO.getUsername());
        //get userDetail instance which contains the username and password
        //in 'loadUserByUsername' already checks for username in the database
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDTO.getUsername());
        //so the rest is to check for null and correct password
        if (userDetails == null || !passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){
            //        logger.warn("Invalid username or password for user: {}", loginDTO.getUsername());
            throw new LoginFailedException("Invalid username or password");
        }

        //response.sendRedirect("/index.html");

        //    logger.info("User authenticated: {}", loginDTO.getUsername());
        return userDetails;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    //                                       ADMIN LOGIC STARTS HERE

    private AdminDTO convertToAdminDto(Admin admin){
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setEmail(admin.getEmail());
        return adminDTO;
    }

    public AdminDTO registerAdmin(AdminRegistrationDTO adminRegistrationDTO){

        // Check if any of the fields are empty or null
        if (adminRegistrationDTO.getUsername() == null || adminRegistrationDTO.getUsername().isEmpty()) {
            throw new ResourceNotFoundException("Invalid input");
        }
        if (adminRegistrationDTO.getEmail() == null || adminRegistrationDTO.getEmail().isEmpty()) {
            throw new ResourceNotFoundException("Invalid input");
        }
        if (adminRegistrationDTO.getPassword() == null || adminRegistrationDTO.getPassword().isEmpty()) {
            throw new ResourceNotFoundException("Invalid input");
        }

        //first checking if username already exist
        if(userRepository.findByUsername(adminRegistrationDTO.getUsername())!= null){
            throw new UsernameAlreadyExistsException("Admin already registered");
        }
        if(userRepository.findByEmail(adminRegistrationDTO.getEmail())!= null){
            throw new UsernameAlreadyExistsException("Admin Email already registered");
        }

        Admin admin = new Admin();
        admin.setUsername(adminRegistrationDTO.getUsername());
        //since it is configured in securityConfig class that passwords need to be encoded so if the password saved to
        //mysql database was plain, then when pass request sent from bruno is also plain then it wouldn't be correct
        // even if the plain password is correct so make sure when it's saved to sql it is also encoded.
        admin.setPassword(passwordEncoder.encode(adminRegistrationDTO.getPassword()));
        admin.setEmail(adminRegistrationDTO.getEmail());

        //saving new registered user to database, and it will auto generate id.
        Admin savedAdmin = adminRepository.save(admin);
        return convertToAdminDto(savedAdmin);
    }

    public UserDetails authenticateAdmin(LoginDTO loginDTO, HttpServletResponse response) throws IOException {

        UserDetails userDetails = customUserDetailsService.loadAdminByUsername(loginDTO.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginDTO.getPassword(), userDetails.getPassword())){
            throw new LoginFailedException("Invalid username or password");
        }
        //return userDetails;

        //UUID creates a random id.
        String sessionId = UUID.randomUUID().toString();

        UserSessionIdDetails userSessionIdDetails = new UserSessionIdDetails(userDetails.getUsername(), "ADMIN");

        String userSessionJson = convertToJson(userSessionIdDetails);

        //stores session id and other values so when server needs to verify session id, they can just check redis.
        redisTemplate.opsForValue().set("Session:" + sessionId, userSessionJson, 30, TimeUnit.MINUTES);

        //Cookies need specific values (e.g., session ID) that are generated at runtime based on the current user session. Therefore,
        //they must be created and set within the context of handling a specific HTTP request.
        Cookie cookie = new Cookie("SESSIONID", sessionId);
        cookie.setHttpOnly(true);
        //setMaxAge is in seconds.
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");

        logger.info("Creating cookie: Name={} Value={} Path={}", cookie.getName(), cookie.getValue(),cookie.getPath());

        //using HttpServletResponse to return the cookie back to client alongside login info.
        response.addCookie(cookie);

        //response.sendRedirect("/admin.html");
        return userDetails;
    }

    public Admin findAdminByUsername(String username){
        return adminRepository.findByUsername(username);
    }

    //turn value to JSON so can add as many things in values as needed in the future.
    //object mapper part of jackson library to convert to json objects and vise versa
    private String convertToJson(UserSessionIdDetails userSessionIdDetails){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(userSessionIdDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert user session details to JSON", e);
        }
    }
}
