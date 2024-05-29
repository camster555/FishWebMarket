package com.qifuxing.fishingwebsite.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.qifuxing.fishingwebsite.model.*;
import com.qifuxing.fishingwebsite.repository.*;
import com.qifuxing.fishingwebsite.service.*;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartDTO;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartItemDTO;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private ShoppingCart shoppingCart;
    private ShoppingCartDTO shoppingCartDTO;
    private User user;
    private Product product;
    private ShoppingCartItem shoppingCartItem;
    private ShoppingCartItemDTO shoppingCartItemDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(200.0);

        shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(2);
        shoppingCartItem.setPriceOfIndividualProduct(100.0);

        shoppingCart.setItems(Collections.singletonList(shoppingCartItem));

        shoppingCartItemDTO = new ShoppingCartItemDTO();
        shoppingCartItemDTO.setId(1L);
        shoppingCartItemDTO.setShoppingCartId(1L);
        shoppingCartItemDTO.setProductId(1L);
        shoppingCartItemDTO.setQuantity(2);
        shoppingCartItemDTO.setPriceOfIndividualProduct(100.0);

        shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(1L);
        shoppingCartDTO.setUserId(1L);
        shoppingCartDTO.setTotalPrice(200.0);
        shoppingCartDTO.setItems(Collections.singletonList(shoppingCartItemDTO));
    }

    @Test
    void testCreateShoppingCart() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));

        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        ShoppingCartDTO createdCart = shoppingCartService.createShoppingCart(shoppingCartDTO);

        assertNotNull(createdCart);
        assertEquals(shoppingCart.getId(), createdCart.getId());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    void testFindShoppingCartById() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));

        ShoppingCartDTO foundCart = shoppingCartService.findShoppingCartById(1L);

        assertNotNull(foundCart);
        assertEquals(shoppingCart.getId(), foundCart.getId());
        verify(shoppingCartRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testDeleteShoppingCart() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));
        doNothing().when(shoppingCartRepository).delete(any(ShoppingCart.class));

        shoppingCartService.deleteShoppingCart(1L);

        verify(shoppingCartRepository, times(1)).delete(any(ShoppingCart.class));
    }

    @Test
    void testFindAllItemsInShoppingCart() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));

        List<ShoppingCartItemDTO> items = shoppingCartService.findAllItemsInShoppingCart(1L);

        assertNotNull(items);
        assertEquals(1, items.size());
        verify(shoppingCartRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testFindAllShoppingCarts() {
        when(shoppingCartRepository.findAll()).thenReturn(Collections.singletonList(shoppingCart));

        List<ShoppingCartDTO> carts = shoppingCartService.findAllShoppingCart();

        assertNotNull(carts);
        assertEquals(1, carts.size());
        verify(shoppingCartRepository, times(1)).findAll();
    }

    @Test
    void testUpdateShoppingCart() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        ShoppingCartDTO updatedCart = shoppingCartService.updateShoppingCart(1L, shoppingCartDTO);

        assertNotNull(updatedCart);
        assertEquals(shoppingCart.getId(), updatedCart.getId());
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    void testCreateShoppingCart_UserNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartService.createShoppingCart(shoppingCartDTO);
        });

        verify(shoppingCartRepository, times(0)).save(any(ShoppingCart.class));
    }

    @Test
    void testFindShoppingCartById_NotFound() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartService.findShoppingCartById(1L);
        });

        verify(shoppingCartRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testDeleteShoppingCart_NotFound() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartService.deleteShoppingCart(1L);
        });

        verify(shoppingCartRepository, times(0)).delete(any(ShoppingCart.class));
    }

    @Test
    void testUpdateShoppingCart_UserNotFound() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartService.updateShoppingCart(1L, shoppingCartDTO);
        });

        verify(shoppingCartRepository, times(0)).save(any(ShoppingCart.class));
    }
}