package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for accounts.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-10
 * @version 1.0.0
 */


//Used on DOA(data access object) or repository classes which will interact with database.
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
