package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.service.AdminService;
import com.qifuxing.fishingwebsite.service.UserService;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages admin related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin")
    public ResponseEntity<User> addAdmin(@RequestBody User user){
        User admin = adminService.addAdmin(user.getUsername(), user.getPassword(), user.getEmail());
        return ResponseEntity.ok(admin);
    }


}
