package com.qifuxing.fishingwebsite.exception;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This exception is thrown when JWT token is invalid.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-16
 * @version 1.0.0
 */

public class JwtErrorException extends RuntimeException{

    public JwtErrorException (String msg){
        super(msg);
    }

}
