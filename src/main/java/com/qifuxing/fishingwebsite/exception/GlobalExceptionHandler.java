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
    public ResponseEntity<?> handleUsernameDuplicateException(UsernameAlreadyExistsException eMsg){
        //logger.error("UsernameAlreadyExistsException: " + eMsg);
        //need to create custom class to return json body as spring auto converts it to json but only if it is
        //structured in a class correctly.
        ErrorResponse errorResponse = new ErrorResponse("409", eMsg.getMessage());
        //the second part 'HttpStatus.UNAUTHORIZED)' is the status code that will be returned to the client and what we
        //see on login page inspect mode.
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<?> handlerUserLoginFailed(LoginFailedException eMsg){
        ErrorResponse errorResponse = new ErrorResponse("401", eMsg.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handlerInvalidInputException(InvalidInputException eMsg){
        //logger.error("InvalidInputException: " + eMsg.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("400", eMsg.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    //this exception happens spring tries to convert HTTP message but since it is empty it won't even reach controller
    //class or it's methods.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHTTPMessageNotReadableException(HttpMessageNotReadableException eMsg){
        ErrorResponse errorResponse = new ErrorResponse("400", eMsg.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException eMsg){
        ErrorResponse errorResponse = new ErrorResponse("404", eMsg.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(JwtErrorException.class)
    public ResponseEntity<?> handleJwtErrorException(JwtErrorException eMsg){
        ErrorResponse errorResponse = new ErrorResponse("400", eMsg.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
