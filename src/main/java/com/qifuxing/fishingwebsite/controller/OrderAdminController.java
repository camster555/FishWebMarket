package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.OrderAdminService;
import com.qifuxing.fishingwebsite.specificDTO.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages admin order-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/adminorder")
public class OrderAdminController {
    @Autowired
    private OrderAdminService orderAdminService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orders = orderAdminService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderAdminService.deleteOrder(id);
        return  ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(){
        orderAdminService.deleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrement(){
        orderAdminService.resetAutoIdIncrement();
        return ResponseEntity.noContent().build();
    }
}
