package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.exception.UsernameAlreadyExistsException;
import com.qifuxing.fishingwebsite.model.Order;
import com.qifuxing.fishingwebsite.model.Payment;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.OrderRepository;
import com.qifuxing.fishingwebsite.repository.PaymentRepository;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.OrderDTO;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing admin.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public User addAdmin(String username,String password,String email){
        if (username == null || username.isEmpty() || password == null || password.isEmpty()
                || email == null || email.isEmpty());

        if (userRepository.findByUsername(username) != null){
            throw new UsernameAlreadyExistsException("Username already registered");
        }
        if (userRepository.findByEmail(email) != null){
            throw new UsernameAlreadyExistsException("Email already registered.");
        }

        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setEmail(email);
        admin.setRole(User.Role.ADMIN);
        return userRepository.save(admin);
    }

}
