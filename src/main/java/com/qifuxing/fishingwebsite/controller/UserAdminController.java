package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages user admin related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/admin/user")
public class UserAdminController {

    @Autowired
    private UserAdminService userAdminService;

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteByIdUser(@PathVariable long id){
        userAdminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteAllUsers(){
        userAdminService.deleteAllUser();
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/users/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrementUser(){
        userAdminService.resetAutoIdIncrementUser();
        return ResponseEntity.noContent().build();
    }

}
