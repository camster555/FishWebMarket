package com.qifuxing.fishingwebsite.exception;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This exception is thrown when login fails.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-22
 * @version 1.0.0
 */

public class LoginFailedException extends RuntimeException{
    public LoginFailedException(String msg){
        super(msg);
    }
}
