package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.PaymentAdminService;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/payment")
public class PaymentAdminController {
    @Autowired
    private PaymentAdminService paymentAdminService;

    @GetMapping("/all")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> payments = paymentAdminService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        PaymentDTO payment = paymentAdminService.findPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentAdminService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllPayments() {
        paymentAdminService.deleteAllPayment();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrementPayments() {
        paymentAdminService.resetAutoIdIncrementPayment();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByStatus(@PathVariable String status) {
        List<PaymentDTO> payments = paymentAdminService.findByStatus(status);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByUserId(@PathVariable Long userId) {
        List<PaymentDTO> payments = paymentAdminService.findPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/date")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByDateBetween(
            @RequestParam("startDate") LocalDateTime startDate,
            @RequestParam("endDate") LocalDateTime endDate) {
        List<PaymentDTO> payments = paymentAdminService.findPaymentsByDateBetween(startDate, endDate);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/method/{paymentMethod}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByMethod(@PathVariable String paymentMethod) {
        List<PaymentDTO> payments = paymentAdminService.findByPaymentMethod(paymentMethod);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByMerchantId(@PathVariable String merchantId) {
        List<PaymentDTO> payments = paymentAdminService.findByMerchantId(merchantId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("nonce/{nonceStr}")
    public ResponseEntity<PaymentDTO> findPaymentByNonceStr(@PathVariable String nonceStr) {
        PaymentDTO payment = paymentAdminService.findByNonceStr(nonceStr);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("provider/{providerId}")
    public ResponseEntity<List<PaymentDTO>> findPaymentsByProviderId(@PathVariable String providerId) {
        List<PaymentDTO> payments = paymentAdminService.findByProviderId(providerId);
        return ResponseEntity.ok(payments);
    }

}
