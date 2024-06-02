package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    //entityManager needed as it provides API for custom queries not provided in JPA repository
    @PersistenceContext
    private EntityManager entityManager;

    public void delete(long id){
        if (!userRepository.existsById(id)){
            throw new ResourceNotFoundException("Id:"+id+" not found.");
        }
        userRepository.deleteById(id);
    }

    public void deleteAll(){
        if (userRepository.count() == 0){
            throw new ResourceNotFoundException("User list already empty");
        }
        userRepository.deleteAll();
    }
    //Transactional annotation ensures this method is used during transaction, since when request is made, spring starts
    //new 'transaction during runtime ensuring is that any part of the method that is transactional fails that database
    //goes back to the previous state.
    @Transactional
    public void resetAutoIdIncrement(){
        if (userRepository.count()==0){
            entityManager.createNativeQuery("ALTER TABLE users AUTO_INCREMENT = 1").executeUpdate();
        }else {
            throw new InvalidInputException("User list not empty, cannot reset auto increment to 1");
        }
    }
}
