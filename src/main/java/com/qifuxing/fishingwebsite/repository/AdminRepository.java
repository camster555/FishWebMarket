package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.Admin;
import com.qifuxing.fishingwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for admins.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-30
 * @version 1.0.0
 */

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByUsername(String username);
    Admin findByEmail(String username);

}
