package com.qifuxing.fishingwebsite.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class handles global exceptions.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-11
 * @version 1.0.0
 */

//this annotation means it's a handler applied to all controllers.
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //will handle specific exceptions from 'UsernameAlreadyExistsException.class'
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    //what type of http status code to return which is 409 conflict, this way don't have to explicitly set in return
    //line of code
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<String> handleUsernameDuplicateException(UsernameAlreadyExistsException eMsg){
        return new ResponseEntity<>(eMsg.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlerUserLoginFailed(LoginFailedException eMsg){
        return new ResponseEntity<>(eMsg.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handlerInvalidInputException(InvalidInputException eMsg){
        logger.error("InvalidInputException: " + eMsg.getMessage());
        return new ResponseEntity<>(eMsg.getMessage(), HttpStatus.BAD_REQUEST);
    }

    //this exception happens spring tries to convert HTTP message but since it is empty it won't even reach controller
    //class or it's methods.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHTTPMessageNotReadableException(HttpMessageNotReadableException eMsg){
        return new ResponseEntity<>("Request body is missing or invalid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException eMsg){
        return new ResponseEntity<>(eMsg.getMessage(),HttpStatus.NOT_FOUND);
    }

}
