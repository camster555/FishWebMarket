package com.qifuxing.fishingwebsite.specificDTO;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This DTO is used for login requests.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-23
 * @version 1.0.0
 */

public class LoginDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
