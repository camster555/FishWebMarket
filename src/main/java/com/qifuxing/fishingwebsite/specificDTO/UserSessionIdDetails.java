package com.qifuxing.fishingwebsite.specificDTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents user session in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-22
 * @version 1.0.0
 */


public class UserSessionIdDetails {

    private String username;
    private String role;

    public UserSessionIdDetails() {}

    @JsonCreator
    public UserSessionIdDetails(@JsonProperty("username") String username,
                                @JsonProperty("role") String role) {
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
