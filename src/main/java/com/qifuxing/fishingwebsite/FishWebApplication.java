package com.qifuxing.fishingwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This is the main Spring Boot application class.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-10
 * @version 1.0.0
 */

//'@SpringBootApplication' this is important and without it, we will be missing ServletWebServerFactory bean error which
//will cause the program to not run properly, and SpringBootApplication is responsible for 3 abilities including
// component scanning, autoconfiguration which have the 'ServletWebServerFactory bean' problem and last ability is
// spring boot configuration.
@SpringBootApplication
public class FishWebApplication {

    public static void main(String[] args){
        SpringApplication.run(FishWebApplication.class, args);
    }
}
