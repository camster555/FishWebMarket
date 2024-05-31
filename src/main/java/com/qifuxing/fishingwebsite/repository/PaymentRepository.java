package com.qifuxing.fishingwebsite.repository;

import com.qifuxing.fishingwebsite.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This repository provides CRUD operations for payments.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-29
 * @version 1.0.0
 */

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    //only specific keywords work as JPA uses these to create sql queries to database like findBy, readBy, queryBy,
    //getBy, countBy
    List<Payment> findByStatus(String status);
    List<Payment> findByUserId(Long id);
    //JPA is case-insensitive so even though it is transactionDate, it will still match 'findByTransactionDate' to it,
    //between here is also a custom query by JPA.
    List<Payment> findByTransactionDateBetween(LocalDateTime startDate,LocalDateTime endDate);
    List<Payment> findByPaymentMethod(String paymentMethod);
    List<Payment> findByMerchantId(String merchantId);
    Optional<Payment> findByNonceStr(String nonceStr);
    List<Payment> findByPaymentProviderId(String paymentProviderId);

}