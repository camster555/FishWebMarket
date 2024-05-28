package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for orders.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-26
 * @version 1.0.0
 */

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
