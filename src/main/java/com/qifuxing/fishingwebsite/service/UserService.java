package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing users.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void delete(long id){
        if (!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Id:"+id+" not found.");
        }
        userRepository.deleteById(id);
    }
}
