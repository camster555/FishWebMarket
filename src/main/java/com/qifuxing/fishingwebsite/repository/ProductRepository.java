package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for products.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-25
 * @version 1.0.0
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
