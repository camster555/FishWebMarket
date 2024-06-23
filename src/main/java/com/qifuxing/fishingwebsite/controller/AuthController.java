package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.service.AuthService;
import com.qifuxing.fishingwebsite.specificDTO.LoginDTO;
import com.qifuxing.fishingwebsite.specificDTO.UserDTO;
import com.qifuxing.fishingwebsite.specificDTO.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages account-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;
    //so first we map to 'UserRegistrationDTO' as seen in the parameter with RequestBody annotation, then we map
    //it from 'UserRegistrationDTO' to User class in AuthService class
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        UserDTO userDTO = authService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok(userDTO);
    }

    //don't need to handle exception since will be handled by global exception class before even reaching back to AuthController class.
    @GetMapping("/user/{username}")
    public ResponseEntity<User> findUserByName(@PathVariable String username){
        User user = authService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    //don't need to handle exception since will be handled by global exception class before even reaching back to AuthController class.
    //HttpServletResponse to receive it from the Spring framework.
    @PostMapping("/login")
    public ResponseEntity<UserDetails> userLogin(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        //logger.info("Authenticating user from AuthController: {}", loginDTO.getUsername());
        UserDetails userDetails = authService.authenticateUser(loginDTO, response);
        return ResponseEntity.ok(userDetails);
    }

}
