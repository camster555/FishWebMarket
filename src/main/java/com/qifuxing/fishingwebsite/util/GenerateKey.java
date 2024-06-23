package com.qifuxing.fishingwebsite.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This util class is for generating secret keys.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-16
 * @version 1.0.0
 */

public class GenerateKey {

    public static void main(String[] args){
        //creating a secure random generator
        SecureRandom secureRandom = new SecureRandom();

        //create an array to gold the 32 bytes or 256 bites
        byte[] key = new byte[32];

        //creating the key
        secureRandom.nextBytes(key);

        //base64 is a binary-to-text encoding scheme that is used to represent binary data.
        //withoutPadding() ensures the encoded string does not include padding characters.
        String encodeKey = Base64.getUrlEncoder().withoutPadding().encodeToString(key);

        System.out.println(encodeKey);
    }

}
