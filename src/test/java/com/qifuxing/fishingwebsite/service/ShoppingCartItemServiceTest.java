package com.qifuxing.fishingwebsite.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.qifuxing.fishingwebsite.model.*;
import com.qifuxing.fishingwebsite.repository.*;
import com.qifuxing.fishingwebsite.service.*;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartItemDTO;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.exception.InvalidInputException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartItemServiceTest {

    @Mock
    private ShoppingCartItemRepository shoppingCartItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShoppingCartItemService shoppingCartItemService;

    private ShoppingCartItem shoppingCartItem;
    private ShoppingCartItemDTO shoppingCartItemDTO;
    private ShoppingCart shoppingCart;
    private Product product;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setTotalPrice(200.0);

        product = new Product();
        product.setId(1L);
        product.setPrice(100.0);

        shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setId(1L);
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(2);
        shoppingCartItem.setPriceOfIndividualProduct(100.0);

        shoppingCartItemDTO = new ShoppingCartItemDTO();
        shoppingCartItemDTO.setId(1L);
        shoppingCartItemDTO.setShoppingCartId(1L);
        shoppingCartItemDTO.setProductId(1L);
        shoppingCartItemDTO.setQuantity(2);
        shoppingCartItemDTO.setPriceOfIndividualProduct(100.0);
    }

    @Test
    void testSaveShoppingCartItem() {
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(shoppingCartItemRepository.save(any(ShoppingCartItem.class))).thenReturn(shoppingCartItem);

        ShoppingCartItemDTO savedItem = shoppingCartItemService.saveShoppingCartItem(shoppingCartItemDTO);

        assertNotNull(savedItem);
        assertEquals(shoppingCartItem.getId(), savedItem.getId());
        assertEquals(400.0, shoppingCart.getTotalPrice());
        verify(shoppingCartItemRepository, times(1)).save(any(ShoppingCartItem.class));
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    void testFindById() {
        when(shoppingCartItemRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCartItem));

        ShoppingCartItemDTO foundItem = shoppingCartItemService.findById(1L);

        assertNotNull(foundItem);
        assertEquals(shoppingCartItem.getId(), foundItem.getId());
        verify(shoppingCartItemRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testUpdateShoppingCartItem() {
        when(shoppingCartItemRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCartItem));
        when(shoppingCartRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCart));
        when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(product));
        when(shoppingCartItemRepository.save(any(ShoppingCartItem.class))).thenReturn(shoppingCartItem);

        shoppingCartItemDTO.setQuantity(3);
        ShoppingCartItemDTO updatedItem = shoppingCartItemService.updateShoppingCartItem(1L, shoppingCartItemDTO);

        assertNotNull(updatedItem);
        assertEquals(shoppingCartItem.getId(), updatedItem.getId());
        assertEquals(300.0, shoppingCart.getTotalPrice());
        verify(shoppingCartItemRepository, times(1)).save(any(ShoppingCartItem.class));
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    void testDeleteById() {
        when(shoppingCartItemRepository.findById(any(Long.class))).thenReturn(Optional.of(shoppingCartItem));
        doNothing().when(shoppingCartItemRepository).deleteById(any(Long.class));

        shoppingCartItemService.deleteById(1L);

        assertEquals(0.0, shoppingCart.getTotalPrice());
        verify(shoppingCartItemRepository, times(1)).deleteById(any(Long.class));
        verify(shoppingCartRepository, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    void testSaveShoppingCartItem_InvalidInput() {
        shoppingCartItemDTO.setQuantity(0);

        assertThrows(InvalidInputException.class, () -> {
            shoppingCartItemService.saveShoppingCartItem(shoppingCartItemDTO);
        });

        verify(shoppingCartItemRepository, times(0)).save(any(ShoppingCartItem.class));
    }

    @Test
    void testUpdateShoppingCartItem_NotFound() {
        when(shoppingCartItemRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartItemService.updateShoppingCartItem(1L, shoppingCartItemDTO);
        });

        verify(shoppingCartItemRepository, times(0)).save(any(ShoppingCartItem.class));
    }

    @Test
    void testFindById_NotFound() {
        when(shoppingCartItemRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartItemService.findById(1L);
        });

        verify(shoppingCartItemRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void testDeleteById_NotFound() {
        when(shoppingCartItemRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            shoppingCartItemService.deleteById(1L);
        });

        verify(shoppingCartItemRepository, times(0)).deleteById(any(Long.class));
    }
}