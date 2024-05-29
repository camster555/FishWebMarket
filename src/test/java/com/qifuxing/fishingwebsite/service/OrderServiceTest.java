package com.qifuxing.fishingwebsite.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.qifuxing.fishingwebsite.model.*;
import com.qifuxing.fishingwebsite.repository.*;
import com.qifuxing.fishingwebsite.service.*;
import com.qifuxing.fishingwebsite.specificDTO.OrderDTO;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setProducts(new HashSet<>(Collections.singletonList(product)));
        order.setOrderDate(new Date());
        order.setStatus("Pending");

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setUser_id(1L);
        orderDTO.setProductIDs(new HashSet<>(Collections.singletonList(1L)));
        orderDTO.setOrderDate(new Date());
        orderDTO.setStatus("Pending");
    }

    @Test
    void testCreateOrder() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(productRepository.findAllById(anySet())).thenReturn(Collections.singletonList(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO createdOrder = orderService.createOrder(orderDTO);

        assertNotNull(createdOrder);
        assertEquals(order.getId(), createdOrder.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrder() {
        when(orderRepository.existsById(any(Long.class))).thenReturn(true);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(productRepository.findAllById(anySet())).thenReturn(Collections.singletonList(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO updatedOrder = orderService.updateOrder(1L, orderDTO);

        assertNotNull(updatedOrder);
        assertEquals(order.getId(), updatedOrder.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        List<OrderDTO> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testFindOrderById() {
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.of(order));

        OrderDTO foundOrder = orderService.findOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
        verify(orderRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testDeleteOrder() {
        when(orderRepository.existsById(any(Long.class))).thenReturn(true);
        doNothing().when(orderRepository).deleteById(any(Long.class));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    void testCreateOrder_UserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.createOrder(orderDTO);
        });

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testUpdateOrder_NotFound() {
        when(orderRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.updateOrder(1L, orderDTO);
        });

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testFindOrderById_NotFound() {
        when(orderRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findOrderById(1L);
        });

        verify(orderRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.existsById(any(Long.class))).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrder(1L);
        });

        verify(orderRepository, times(0)).deleteById(any(Long.class));
    }
}
