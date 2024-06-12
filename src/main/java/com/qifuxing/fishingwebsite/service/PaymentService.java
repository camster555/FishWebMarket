package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Order;
import com.qifuxing.fishingwebsite.model.Payment;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.OrderRepository;
import com.qifuxing.fishingwebsite.repository.PaymentRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service manages business logic for managing payment.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-29
 * @version 1.0.0
 */

@Service
public class PaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager entityManager;

    public Payment convertToEntity(PaymentDTO paymentDTO){
        Payment existingPayment = new Payment();
        //usually when convert to Entity you setId manually in the update method or it would create a new Payment and
        //would not update correctly.
        existingPayment.setId(paymentDTO.getId());
        existingPayment.setAmount(paymentDTO.getAmount());
        existingPayment.setCurrency(paymentDTO.getCurrency());
        existingPayment.setStatus(paymentDTO.getStatus());
        existingPayment.setPaymentMethod(paymentDTO.getPaymentMethod());
        existingPayment.setPaymentProviderId(paymentDTO.getPaymentProviderId());
        existingPayment.setPaymentDetails(paymentDTO.getPaymentDetails());
        existingPayment.setMerchantId(paymentDTO.getMerchantId());
        existingPayment.setNonceStr(paymentDTO.getNonceStr());
        existingPayment.setSignature(paymentDTO.getSignature());
        existingPayment.setDescription(paymentDTO.getDescription());
        Order order = orderRepository.findById(paymentDTO.getOrderId()).orElseThrow(
                ()->new ResourceNotFoundException("Order with this id not found:"+paymentDTO.getOrderId()));
        existingPayment.setOrderId(order);
        //don't need to search for userid since it is already set in order entity.
        existingPayment.setUserId(order.getUser());
        existingPayment.setTransactionDate(paymentDTO.getTransactionDate());
        return existingPayment;
    }

    public PaymentDTO convertToDTO(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setCurrency(payment.getCurrency());
        paymentDTO.setStatus(payment.getStatus());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        paymentDTO.setPaymentProviderId(payment.getPaymentProviderId());
        paymentDTO.setPaymentDetails(payment.getPaymentDetails());
        paymentDTO.setMerchantId(payment.getMerchantId());
        paymentDTO.setNonceStr(payment.getNonceStr());
        paymentDTO.setSignature(payment.getSignature());
        paymentDTO.setDescription(payment.getDescription());
        paymentDTO.setOrderId(payment.getOrderId().getId());
        paymentDTO.setUserId(payment.getUserId().getId());
        paymentDTO.setTransactionDate(payment.getTransactionDate());
        return paymentDTO;
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO){
        Payment payment = convertToEntity(paymentDTO);
        Payment savedPayment = paymentRepository.save(payment);

        //update order status when payment is created successfully
        Order order = payment.getOrderId();
        if ("Completed".equals(paymentDTO.getStatus())){
            order.setStatus("Payment successful");
        } else if ("Failed".equals(paymentDTO.getStatus())){
            order.setStatus("Payment failed");
        } else {
            order.setStatus("Payment pending");
        }
        orderRepository.save(order);
        return convertToDTO(savedPayment);
    }

    public PaymentDTO updatePayment(PaymentDTO paymentDTO){
        if (!paymentRepository.existsById(paymentDTO.getId())){
            throw new ResourceNotFoundException("Payment with this id not found:"+paymentDTO.getId());
        }
        Payment payment = convertToEntity(paymentDTO);
        //usually need to setId remember to check back with bruno, testing works so far.
        //payment.setId(paymentDTO.getId());
        Payment updatePayment = paymentRepository.save(payment);
        return convertToDTO(updatePayment);
    }

    /*
    public List<PaymentDTO> getAllPayments(){
        List<Payment> paymentList = paymentRepository.findAll();
        if (paymentList.isEmpty()){
            throw new ResourceNotFoundException("Empty list");
        }

        return paymentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PaymentDTO findPaymentById(Long id){
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Payment with id not found:"+id));
        return convertToDTO(payment);
    };

    public void deletePayment(Long id){
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Payment with id not found:"+id));
        paymentRepository.delete(payment);
    }

    //to check status for all payments to trouble shoot.
    public List<PaymentDTO> findByStatus(String status){
        List<Payment> findByStatus = paymentRepository.findByStatus(status);
        if (findByStatus.isEmpty()){
            throw new ResourceNotFoundException("Payments with status:"+ status +" is empty.");
        }
        return findByStatus.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //to find all the payments made by one user.
    public List<PaymentDTO> findPaymentsByUserId(Long userId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User with this id not found:"+ userId);
        }
        List<Payment> paymentList = paymentRepository.findByUserId(userId);
        if (paymentList.isEmpty()){
            throw new ResourceNotFoundException("User id payment list is empty"+userId);
        }
        return paymentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> findPaymentsByDateBetween(LocalDateTime startDate, LocalDateTime endDate){
        List<Payment> paymentList = paymentRepository.findByTransactionDateBetween(startDate, endDate);
        if (paymentList.isEmpty()){
            throw new ResourceNotFoundException("Payments from the start and end date not found"+ startDate + endDate);
        }
        return paymentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<PaymentDTO> findByPaymentMethod(String paymentMethod){
        List<Payment> paymentList = paymentRepository.findByPaymentMethod(paymentMethod);
        if (paymentList.isEmpty()){
            throw new ResourceNotFoundException("Payments with this type not found:" + paymentMethod);
        }
        return paymentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //for example my fish website, I am one of the merchant, could be useful in future is more than one merchant sell
    //from website.
    public List<PaymentDTO> findByMerchantId(String merchantId){
        List<Payment> paymentList = paymentRepository.findByMerchantId(merchantId);
        if (paymentList.isEmpty()){
            throw new ResourceNotFoundException("Merchant with this id not found:" + merchantId);
        }
        return paymentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //nonce string to provide unique id for each payment to prevent replay attacks.
    public PaymentDTO findByNonceStr(String nonceStr){
        Optional<Payment> paymentOptional = paymentRepository.findByNonceStr(nonceStr);
        Payment payment = paymentOptional.orElseThrow(()-> new ResourceNotFoundException("Payment with nonce string not found."+nonceStr));
        return convertToDTO(payment);
    }

    //provider like all payments made by WeChat, alipay, PayPal etc.
    public List<PaymentDTO> findByProviderId(String providerId){
        List<Payment> paymentList = paymentRepository.findByPaymentProviderId(providerId);
        if (paymentList.isEmpty()){
            throw new ResourceNotFoundException("Payments with this provider id not found:" + providerId);
        }
        return paymentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public void deleteAll(){
        if (paymentRepository.count() == 0){
            throw new ResourceNotFoundException("Payment list already empty");
        }
        paymentRepository.deleteAll();
    }
    //Transactional annotation ensures this method is used during transaction, since when request is made, spring starts
    //new 'transaction during runtime ensuring is that any part of the method that is transactional fails that database
    //goes back to the previous state.
    @Transactional
    public void resetAutoIdIncrement(){
        if (paymentRepository.count()==0){
            entityManager.createNativeQuery("ALTER TABLE payment AUTO_INCREMENT = 1").executeUpdate();
        }else {
            throw new InvalidInputException("Payment list not empty, cannot reset auto increment to 1");
        }
    }
     */

}
