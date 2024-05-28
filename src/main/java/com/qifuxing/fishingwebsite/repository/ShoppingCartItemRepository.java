package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for shopping cart item.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@Repository
public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem,Long> {

}
