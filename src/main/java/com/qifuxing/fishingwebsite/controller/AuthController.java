package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.model.Admin;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.service.AuthService;
import com.qifuxing.fishingwebsite.specificDTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    //                                     CUSTOMER/USER LOGIC STARTS HERE
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
    public ResponseEntity<UserDetails> userLogin(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        //logger.info("Authenticating user from AuthController: {}", loginDTO.getUsername());
        UserDetails userDetails = authService.authenticateUser(loginDTO, response);
        return ResponseEntity.ok(userDetails);
    }

    //                                       ADMIN LOGIC STARTS HERE

    @PostMapping("/admin-register")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody AdminRegistrationDTO adminRegistrationDTO){
        AdminDTO adminDTO = authService.registerAdmin(adminRegistrationDTO);
        return ResponseEntity.ok(adminDTO);
    }

    @GetMapping("/admin/{username}")
    public ResponseEntity<Admin> findAdminByName(@PathVariable String username){
        Admin admin = authService.findAdminByUsername(username);
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/admin-login")
    public ResponseEntity<?> adminLogin(@RequestBody LoginDTO loginDTO, HttpServletResponse response) throws IOException {
        logger.info("Authenticating admin from AuthController: {}", loginDTO.getUsername());
        UserDetails userDetails = authService.authenticateAdmin(loginDTO, response);
        return ResponseEntity.ok(userDetails);
    }

}
