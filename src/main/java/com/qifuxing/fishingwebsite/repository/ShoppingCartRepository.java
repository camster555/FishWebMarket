package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for shopping cart.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
