package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Order;
import com.qifuxing.fishingwebsite.repository.OrderRepository;
import com.qifuxing.fishingwebsite.specificDTO.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing order admin.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@Service
public class OrderAdminService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("Order list empty, no order found.");
        }
        return orders.stream().map(orderService::convertToOrderDto).collect(Collectors.toList());
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order by this id not found so can't be deleted");
        }
        orderRepository.deleteById(id);
    }

    public void deleteAll() {
        if (orderRepository.count() == 0) {
            throw new ResourceNotFoundException("Order list already empty");
        }
        orderRepository.deleteAll();
    }

    @Transactional
    public void resetAutoIdIncrement() {
        if (orderRepository.count() == 0) {
            entityManager.createNativeQuery("ALTER TABLE orders AUTO_INCREMENT = 1").executeUpdate();
        } else {
            throw new InvalidInputException("Order list not empty, cannot reset auto increment to 1");
        }
    }
}
