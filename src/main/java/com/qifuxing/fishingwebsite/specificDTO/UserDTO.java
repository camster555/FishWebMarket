package com.qifuxing.fishingwebsite.specificDTO;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This DTO is used for user requests.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

public class UserDTO {

    private String username;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
