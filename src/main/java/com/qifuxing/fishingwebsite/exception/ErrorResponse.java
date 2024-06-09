package com.qifuxing.fishingwebsite.exception;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class handles error exceptions.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-07
 * @version 1.0.0
 */

public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
