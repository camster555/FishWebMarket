package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Order;;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.OrderRepository;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service manages business logic for managing orders.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-26
 * @version 1.0.0
 */

@Service
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    private OrderDTO convertToOrderDto(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUser_id(order.getId());
        //so here using getProducts() from order class to stream the products then using map to get each Product and
        //also using getId() method from Product class and this is possible because in order class we have
        //'private Set<Product> products;' which is a set of products from Product class which means we can use its methods.
        //then '(Product::getId)' means each product is being iterated to get its id and collects all the ids from the
        //set and gathers them in a set<Long> in the end.
        orderDTO.setProductIDs(order.getProducts().stream().map(Product::getId).collect(Collectors.toSet()));
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setStatus(order.getStatus());
        return orderDTO;
    }

    public Order convertToOrderEntity(OrderDTO orderDTO){
        Order order = new Order();
        //logger.debug("Converting OrderDTO to Order entity. User ID: {}", orderDTO.getUser_id());
        //when request is made, the id of user is given then uses getUser_id method from orderDTO to get it and then find User.
        User user = userRepository.findById(orderDTO.getUser_id()).orElseThrow(() -> new ResourceNotFoundException("Id:"+orderDTO.getUser_id()+" not found."));
        order.setUser(user);
        //logger.debug("Order ID: {}", orderDTO.getId());
        //logger.debug("Product IDs: {}", orderDTO.getProductIDs());
        if (orderDTO.getProductIDs() == null || orderDTO.getProductIDs().isEmpty()) {
            throw new ResourceNotFoundException("Product IDs must not be null or empty");
        }
        //when request is made, product comes in ids so need to find all product by using the id given by the request
        //and make it into a set of Products.
        Set<Product> productsSet = new HashSet<>(productRepository.findAllById(orderDTO.getProductIDs()));
        order.setProducts(productsSet);
        order.setOrderDate(orderDTO.getOrderDate());
        order.setStatus(orderDTO.getStatus());
        return order;
    }

    public OrderDTO createOrder(OrderDTO orderDTO){
        //logger.info("Creating a new order with OrderDTO: {}", orderDTO);
        Order order = convertToOrderEntity(orderDTO);
        Order saveOrder = orderRepository.save(order);
        return convertToOrderDto(saveOrder);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO){
        if (!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Id order:" + id + " not found.");
        }

        Order order = convertToOrderEntity(orderDTO);
        //setting id to see which order should be updated needs to be in this method instead of the convertToOrder method
        //as in the convert method new instances are created so jpa does not know which id it belongs to, if you do setId
        //in the converterToOrder method and not in this method then it WILL NOT update and will create a new order.
        //so it needs to be null so creation method can create new order id and so jpa updates the existing id in this method.
        order.setId(id);
        Order updateOrder = orderRepository.save(order);
        return convertToOrderDto(updateOrder);
    }

    public List<OrderDTO> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()){
            throw new ResourceNotFoundException("Order list empty, no order found.");
        }

        return orders.stream().map(this::convertToOrderDto).collect(Collectors.toList());
    }

    public OrderDTO findOrderById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order with id " +id+ " not found."));
        return convertToOrderDto(order);
    }

    public void deleteOrder(Long id){
        if (!orderRepository.existsById(id)){
            throw new ResourceNotFoundException("Order by this id not found so can't be deleted");
        }
        orderRepository.deleteById(id);
    }
}
