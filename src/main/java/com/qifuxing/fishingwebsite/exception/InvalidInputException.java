package com.qifuxing.fishingwebsite.exception;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This exception is thrown when input is invalid.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-25
 * @version 1.0.0
 */

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String msg){
        super(msg);
    }
}
