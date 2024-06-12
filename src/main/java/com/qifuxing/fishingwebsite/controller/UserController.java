package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages user-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    /*
    @Autowired
    private UserService userService;

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrement(){
        userService.resetAutoIdIncrement();
        return ResponseEntity.noContent().build();
    }
     */

}
