package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for users.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //custom method for Jpa to use are 'findBy', 'readyBy', 'queryBy', follow by property name of 'User' entity and
    //jpa will auto map it to the field, but the User class must have Username field or would produce error.
    //during runtime jpa will sent query when this method is called to the mysql to find the username for user.
    User findByUsername(String username);
    User findByEmail(String username);
}
