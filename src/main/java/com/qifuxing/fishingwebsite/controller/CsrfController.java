package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.CsrfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages csrf token operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-05
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api")
public class CsrfController {

    @Autowired
    private CsrfService csrfService;

    //for 'cross site request forgery' initial page load
    @GetMapping("/csrf-token")
    public ResponseEntity<?> getCsrfToken(HttpServletRequest request){

        String token = csrfService.getCsrfToken(request);
        return ResponseEntity.ok(token);

    }

}
