package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.specificDTO.EmailRequestDTO;
import com.qifuxing.fishingwebsite.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages email operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-01
 * @version 1.0.0
 */

@RestController
@RequestMapping("api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("send-email")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO){

        emailService.sendEmail(emailRequestDTO.getTo(), emailRequestDTO.getSubject(), emailRequestDTO.getBody());
        return ResponseEntity.ok("Email sent success.");
    }

}
