package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.ShoppingCartItemService;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages shopping cart item-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/public/shopping-cart-item")
public class ShoppingCartItemController {
    @Autowired
    private ShoppingCartItemService shoppingCartItemService;
    @PostMapping
    public ResponseEntity<ShoppingCartItemDTO> createShoppingCartItem(@RequestBody ShoppingCartItemDTO shoppingCartItemDTO){
        ShoppingCartItemDTO createdShoppingCartItem = shoppingCartItemService.saveShoppingCartItem(shoppingCartItemDTO);
        return ResponseEntity.ok(createdShoppingCartItem);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartItemDTO> findById(@PathVariable Long id){
        ShoppingCartItemDTO findShoppingCartItem = shoppingCartItemService.findById(id);
        return ResponseEntity.ok(findShoppingCartItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartItemDTO> updateItem(@PathVariable Long id, @RequestBody ShoppingCartItemDTO shoppingCartItemDTO){
        ShoppingCartItemDTO updatedShoppingCartItem = shoppingCartItemService.updateShoppingCartItem(id, shoppingCartItemDTO);
        return ResponseEntity.ok(updatedShoppingCartItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id){
        shoppingCartItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(){
        shoppingCartItemService.deleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrement(){
        shoppingCartItemService.resetAutoIdIncrement();
        return ResponseEntity.noContent().build();
    }

}
