package com.qifuxing.fishingwebsite.exception;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This exception is thrown when a username already exists.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-22
 * @version 1.0.0
 */

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException (String msg){
        //so the msg gets passed to RuntimeException constructor and sets the message.
        super(msg);
    }

}
