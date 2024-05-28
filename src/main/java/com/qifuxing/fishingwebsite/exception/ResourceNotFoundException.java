package com.qifuxing.fishingwebsite.exception;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This exception is thrown when resources is not found.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
