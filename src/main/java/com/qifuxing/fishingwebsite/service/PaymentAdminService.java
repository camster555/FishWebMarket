package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Order;
import com.qifuxing.fishingwebsite.model.Payment;
import com.qifuxing.fishingwebsite.repository.PaymentRepository;
import com.qifuxing.fishingwebsite.specificDTO.OrderDTO;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing payment admin service.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@Service
public class PaymentAdminService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDTO> getAllPayments() {
        List<Payment> paymentList = paymentRepository.findAll();
        if (paymentList.isEmpty()) {
            throw new ResourceNotFoundException("Empty list");
        }
        return paymentList.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

    public PaymentDTO findPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Payment with id not found: " + id));
        return paymentService.convertToDTO(payment);
    }

    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Payment with id not found: " + id));
        paymentRepository.delete(payment);
    }

    public void deleteAllPayment() {
        if (paymentRepository.count() == 0) {
            throw new ResourceNotFoundException("Payment list already empty");
        }
        paymentRepository.deleteAll();
    }

    @Transactional
    public void resetAutoIdIncrementPayment() {
        if (paymentRepository.count() == 0) {
            entityManager.createNativeQuery("ALTER TABLE payment AUTO_INCREMENT = 1").executeUpdate();
        } else {
            throw new InvalidInputException("Payment list not empty, cannot reset auto increment to 1");
        }
    }

    public List<PaymentDTO> findByStatus(String status) {
        List<Payment> findByStatus = paymentRepository.findByStatus(status);
        if (findByStatus.isEmpty()) {
            throw new ResourceNotFoundException("Payments with status: " + status + " is empty.");
        }
        return findByStatus.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> findPaymentsByUserId(Long userId) {
        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        if (paymentList.isEmpty()) {
            throw new ResourceNotFoundException("User id payment list is empty: " + userId);
        }
        return paymentList.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> findPaymentsByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Payment> paymentList = paymentRepository.findByTransactionDateBetween(startDate, endDate);
        if (paymentList.isEmpty()) {
            throw new ResourceNotFoundException("Payments from the start and end date not found: " + startDate + " to " + endDate);
        }
        return paymentList.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> findByPaymentMethod(String paymentMethod) {
        List<Payment> paymentList = paymentRepository.findByPaymentMethod(paymentMethod);
        if (paymentList.isEmpty()) {
            throw new ResourceNotFoundException("Payments with this type not found: " + paymentMethod);
        }
        return paymentList.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> findByMerchantId(String merchantId) {
        List<Payment> paymentList = paymentRepository.findByMerchantId(merchantId);
        if (paymentList.isEmpty()) {
            throw new ResourceNotFoundException("Merchant with this id not found: " + merchantId);
        }
        return paymentList.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

    public PaymentDTO findByNonceStr(String nonceStr) {
        Optional<Payment> paymentOptional = paymentRepository.findByNonceStr(nonceStr);
        Payment payment = paymentOptional.orElseThrow(() -> new ResourceNotFoundException("Payment with nonce string not found: " + nonceStr));
        return paymentService.convertToDTO(payment);
    }

    public List<PaymentDTO> findByProviderId(String providerId) {
        List<Payment> paymentList = paymentRepository.findByPaymentProviderId(providerId);
        if (paymentList.isEmpty()) {
            throw new ResourceNotFoundException("Payments with this provider id not found: " + providerId);
        }
        return paymentList.stream().map(paymentService::convertToDTO).collect(Collectors.toList());
    }

}
