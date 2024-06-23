package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Order;
import com.qifuxing.fishingwebsite.model.Payment;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.OrderRepository;
import com.qifuxing.fishingwebsite.repository.PaymentRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.PaymentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//command prompt "mvn test -Dtest=PaymentServiceTest"
/*
@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    PaymentService paymentService;

    private Payment payment;
    private Payment payment2;
    private PaymentDTO paymentDTO;
    private Order order;
    private User user;

    @BeforeEach
    void setUp(){
        order = new Order();
        order.setId(999L);

        user = new User();
        user.setId(555L);

        payment = new Payment();
        payment.setId(1000L);
        payment.setAmount(100.0);
        payment.setCurrency("ENTITY");
        payment.setStatus("Pending");
        payment.setPaymentMethod("Credit Card");
        payment.setPaymentProviderId("provider123");
        payment.setPaymentDetails("details");
        payment.setMerchantId("merchant123");
        payment.setNonceStr("nonce");
        payment.setSignature("signature");
        payment.setDescription("description");
        payment.setOrderId(order);
        payment.setUserId(user);
        payment.setTransactionDate(LocalDateTime.now());

        payment2 = new Payment();
        payment2.setId(21000L);
        payment2.setAmount(2100.0);
        payment2.setCurrency("entity2");
        payment2.setStatus("Pending");
        payment2.setPaymentMethod("Credit Card2");
        payment2.setPaymentProviderId("2provider123");
        payment2.setPaymentDetails("2details");
        payment2.setMerchantId("2merchant123");
        payment2.setNonceStr("nonce2");
        payment2.setSignature("signature2");
        payment2.setDescription("description2");
        payment2.setOrderId(order);
        payment2.setUserId(user);
        payment2.setTransactionDate(LocalDateTime.now());

        paymentDTO = new PaymentDTO();
        paymentDTO.setId(10001L);
        paymentDTO.setAmount(100.0);
        paymentDTO.setCurrency("DTO");
        paymentDTO.setStatus("success");
        paymentDTO.setPaymentMethod("Credit Card");
        paymentDTO.setPaymentProviderId("provider123");
        paymentDTO.setPaymentDetails("details");
        paymentDTO.setMerchantId("merchant123");
        paymentDTO.setNonceStr("nonce");
        paymentDTO.setSignature("signature");
        paymentDTO.setDescription("description");
        paymentDTO.setOrderId(999L);
        paymentDTO.setUserId(555L);
        paymentDTO.setTransactionDate(LocalDateTime.now());
    }
    @Test
    void testCreatePayment(){
        when(orderRepository.findById(999L)).thenReturn(Optional.of(order));
        when(userRepository.findById(555L)).thenReturn(Optional.of(user));
        //here the mocking behaviour should be when Payment object is called in save method it should return Payment object.
        //when(paymentRepository.save(any(Payment.class))).thenReturn(payment2);
        //above when method always returns payment2 which will cause error since the createPayment method will always return
        //content from payment 2, so we fix with thenAnswer
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            return payment;
        });
        PaymentDTO result = paymentService.createPayment(paymentDTO);
        assertNotNull(result);
        assertEquals(paymentDTO.getId(), result.getId());
        assertEquals(paymentDTO.getAmount(), result.getAmount());
        assertEquals(paymentDTO.getOrderId(), result.getOrderId());

        verify(orderRepository, times(1)).findById(999L);
        verify(userRepository, times(1)).findById(555L);
        //here the verify method is actually looking to see when save method is called from paymentRepository that it
        //is executed once during createPayment method and that the save method is interacting with the repository.
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
    @Test
    void testUpdatePayment(){

        when(paymentRepository.existsById(paymentDTO.getId())).thenReturn(true);
        when(orderRepository.findById(paymentDTO.getOrderId())).thenReturn(Optional.of(order));
        when(userRepository.findById(paymentDTO.getUserId())).thenReturn(Optional.of(user));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment payment = invocation.getArgument(0);
            return payment;
        });

        PaymentDTO updatedPayment = paymentService.updatePayment(paymentDTO);

        assertNotNull(updatedPayment);
        assertEquals(paymentDTO.getId(), updatedPayment.getId());
        assertEquals(paymentDTO.getAmount(), updatedPayment.getAmount());
        assertEquals(paymentDTO.getCurrency(), updatedPayment.getCurrency());
        assertEquals(paymentDTO.getStatus(), updatedPayment.getStatus());

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testUpdatePayment_NotFound() {
        when(paymentRepository.existsById(paymentDTO.getId())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> paymentService.updatePayment(paymentDTO));

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testGetAllPayments() {
        List<Payment> paymentList = Arrays.asList(payment, payment2);

        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<PaymentDTO> paymentDTOList = paymentService.getAllPayments();

        assertNotNull(paymentDTOList);
        assertEquals(2, paymentDTOList.size());
        assertEquals(payment.getId(), paymentDTOList.get(0).getId());
        assertEquals(payment2.getId(), paymentDTOList.get(1).getId());

        verify(paymentRepository, times(1)).findAll();
    }


    @Test
    void testGetAllPayments_EmptyList() {
        when(paymentRepository.findAll()).thenReturn(Arrays.asList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.getAllPayments());

        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void testFindPaymentById() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        PaymentDTO foundPayment = paymentService.findPaymentById(payment.getId());

        assertNotNull(foundPayment);
        assertEquals(payment.getId(), foundPayment.getId());
        assertEquals(payment.getAmount(), foundPayment.getAmount());
        assertEquals(payment.getCurrency(), foundPayment.getCurrency());
        assertEquals(payment.getStatus(), foundPayment.getStatus());

        verify(paymentRepository, times(1)).findById(payment.getId());
    }

    @Test
    void testFindPaymentById_NotFound() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findPaymentById(payment.getId()));

        verify(paymentRepository, times(1)).findById(payment.getId());
    }

    @Test
    void testDeletePayment() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.of(payment));

        paymentService.deletePayment(payment.getId());

        verify(paymentRepository, times(1)).findById(payment.getId());
        verify(paymentRepository, times(1)).delete(payment);
    }

    @Test
    void testDeletePayment_NotFound() {
        when(paymentRepository.findById(payment.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.deletePayment(payment.getId()));

        verify(paymentRepository, times(1)).findById(payment.getId());
        verify(paymentRepository, never()).delete(any(Payment.class));
    }
    @Test
    void testPaymentStatus_Success(){
        when(paymentRepository.findByStatus("Pending")).thenReturn(Arrays.asList(payment,payment2));

        List<PaymentDTO> result = paymentService.findByStatus("Pending");

        assertNotNull(result);
        assertEquals(2,result.size());
        assertEquals(payment.getId(),result.get(0).getId());
        assertEquals(payment2.getId(),result.get(1).getId());

        verify(paymentRepository,times(1)).findByStatus("Pending");
    }

    @Test
    void testFindByStatus_EmptyList() {
        when(paymentRepository.findByStatus("Pending")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findByStatus("Pending"));

        verify(paymentRepository, times(1)).findByStatus("Pending");
    }

    @Test
    void testFindPaymentsByUserId_UserExists() {
        when(userRepository.existsById(555L)).thenReturn(true);
        when(paymentRepository.findByUserId(555L)).thenReturn(Arrays.asList(payment, payment2));

        List<PaymentDTO> result = paymentService.findPaymentsByUserId(555L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
        assertEquals(payment2.getId(), result.get(1).getId());

        verify(paymentRepository, times(1)).findByUserId(555L);
    }

    @Test
    void testFindPaymentsByUserId_UserNotFound() {
        when(userRepository.existsById(555L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findPaymentsByUserId(555L));

        verify(userRepository, times(1)).existsById(555L);
        verify(paymentRepository, never()).findByUserId(anyLong());
    }

    @Test
    void testFindPaymentsByUserId_EmptyList() {
        when(userRepository.existsById(555L)).thenReturn(true);
        when(paymentRepository.findByUserId(555L)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findPaymentsByUserId(555L));

        verify(paymentRepository, times(1)).findByUserId(555L);
    }

    @Test
    void testFindPaymentsByDateBetween_Success() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        when(paymentRepository.findByTransactionDateBetween(startDate, endDate)).thenReturn(Arrays.asList(payment, payment2));

        List<PaymentDTO> result = paymentService.findPaymentsByDateBetween(startDate, endDate);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
        assertEquals(payment2.getId(), result.get(1).getId());

        verify(paymentRepository, times(1)).findByTransactionDateBetween(startDate, endDate);
    }

    @Test
    void testFindPaymentsByDateBetween_EmptyList() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        when(paymentRepository.findByTransactionDateBetween(startDate, endDate)).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findPaymentsByDateBetween(startDate, endDate));

        verify(paymentRepository, times(1)).findByTransactionDateBetween(startDate, endDate);
    }

    @Test
    void testFindByPaymentMethod_Success() {
        when(paymentRepository.findByPaymentMethod("Credit Card")).thenReturn(Arrays.asList(payment, payment2));

        List<PaymentDTO> result = paymentService.findByPaymentMethod("Credit Card");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
        assertEquals(payment2.getId(), result.get(1).getId());

        verify(paymentRepository, times(1)).findByPaymentMethod("Credit Card");
    }

    @Test
    void testFindByPaymentMethod_EmptyList() {
        when(paymentRepository.findByPaymentMethod("Credit Card")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findByPaymentMethod("Credit Card"));

        verify(paymentRepository, times(1)).findByPaymentMethod("Credit Card");
    }

    @Test
    void testFindByMerchantId_Success() {
        when(paymentRepository.findByMerchantId("merchant123")).thenReturn(Arrays.asList(payment, payment2));

        List<PaymentDTO> result = paymentService.findByMerchantId("merchant123");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
        assertEquals(payment2.getId(), result.get(1).getId());

        verify(paymentRepository, times(1)).findByMerchantId("merchant123");
    }

    @Test
    void testFindByMerchantId_EmptyList() {
        when(paymentRepository.findByMerchantId("merchant123")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findByMerchantId("merchant123"));

        verify(paymentRepository, times(1)).findByMerchantId("merchant123");
    }

    @Test
    void testFindByNonceStr_Success() {
        when(paymentRepository.findByNonceStr("nonce")).thenReturn(Optional.of(payment));

        PaymentDTO result = paymentService.findByNonceStr("nonce");

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getAmount(), result.getAmount());

        verify(paymentRepository, times(1)).findByNonceStr("nonce");
    }

    @Test
    void testFindByNonceStr_NotFound() {
        when(paymentRepository.findByNonceStr("nonce")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findByNonceStr("nonce"));

        verify(paymentRepository, times(1)).findByNonceStr("nonce");
    }

    @Test
    void testFindByProviderId_Success() {
        when(paymentRepository.findByPaymentProviderId("provider123")).thenReturn(Arrays.asList(payment, payment2));

        List<PaymentDTO> result = paymentService.findByProviderId("provider123");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
        assertEquals(payment2.getId(), result.get(1).getId());

        verify(paymentRepository, times(1)).findByPaymentProviderId("provider123");
    }

    @Test
    void testFindByProviderId_EmptyList() {
        when(paymentRepository.findByPaymentProviderId("provider123")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.findByProviderId("provider123"));

        verify(paymentRepository, times(1)).findByPaymentProviderId("provider123");
    }
}


 */