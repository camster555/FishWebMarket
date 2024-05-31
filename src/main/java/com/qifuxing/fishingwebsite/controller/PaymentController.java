package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.PaymentService;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages payment-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-31
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        PaymentDTO createdPayment = paymentService.createPayment(paymentDTO);
        return ResponseEntity.ok(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updatedPayment = paymentService.updatePayment(paymentDTO);
        return ResponseEntity.ok(updatedPayment);
    }

    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> paymentDTOList = paymentService.getAllPayments();
        return ResponseEntity.ok(paymentDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> findPaymentById(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.findPaymentById(id);
        return ResponseEntity.ok(paymentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDTO>> findByStatus(@PathVariable String status) {
        List<PaymentDTO> paymentDTOList = paymentService.findByStatus(status);
        return ResponseEntity.ok(paymentDTOList);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentDTO> paymentDTOList = paymentService.findPaymentsByUserId(userId);
        return ResponseEntity.ok(paymentDTOList);
    }

    @GetMapping("/date")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByDateBetween(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<PaymentDTO> paymentDTOList = paymentService.findPaymentsByDateBetween(startDate, endDate);
        return ResponseEntity.ok(paymentDTOList);
    }

    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<PaymentDTO>> findByPaymentMethod(@PathVariable String paymentMethod) {
        List<PaymentDTO> paymentDTOList = paymentService.findByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(paymentDTOList);
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<PaymentDTO>> findByMerchantId(@PathVariable String merchantId) {
        List<PaymentDTO> paymentDTOList = paymentService.findByMerchantId(merchantId);
        return ResponseEntity.ok(paymentDTOList);
    }

    @GetMapping("/nonce/{nonceStr}")
    public ResponseEntity<PaymentDTO> findByNonceStr(@PathVariable String nonceStr) {
        PaymentDTO paymentDTO = paymentService.findByNonceStr(nonceStr);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<PaymentDTO>> findByProviderId(@PathVariable String providerId) {
        List<PaymentDTO> paymentDTOList = paymentService.findByProviderId(providerId);
        return ResponseEntity.ok(paymentDTOList);
    }
}
