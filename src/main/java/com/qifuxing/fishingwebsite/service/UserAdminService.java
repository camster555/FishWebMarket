package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing user admin.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@Service
public class UserAdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public void deleteUser(Long id){
        if (id == null) {
            throw new InvalidInputException("Invalid input: ID is required.");
        }

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    public void deleteAllUser(){
        if (userRepository.count() == 0){
            throw new ResourceNotFoundException("User list already empty");
        }
        userRepository.deleteAll();
    }
    //Transactional annotation ensures this method is used during transaction, since when request is made, spring starts
    //new 'transaction during runtime ensuring is that any part of the method that is transactional fails that database
    //goes back to the previous state.
    @Transactional
    public void resetAutoIdIncrementUser(){
        if (userRepository.count()==0){
            entityManager.createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1").executeUpdate();
        }else {
            throw new InvalidInputException("User list not empty, cannot reset auto increment to 1");
        }
    }


}
