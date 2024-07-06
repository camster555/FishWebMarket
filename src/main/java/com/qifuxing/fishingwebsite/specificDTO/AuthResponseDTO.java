package com.qifuxing.fishingwebsite.specificDTO;

import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents auth response request.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-03
 * @version 1.0.0
 */

public class AuthResponseDTO {

    private final String token;
    private final String username;
    private final List<String> roles;

    public AuthResponseDTO(String token,String username,List<String> roles){
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    public String getToken(){
        return token;
    }

    public String getUsername(){
        return username;
    }

    public List<String> getRoles(){
        return roles;
    }

}
