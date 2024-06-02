package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.ShoppingCartService;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartDTO;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages shopping cart related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        ShoppingCartDTO createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCartDTO);
        return ResponseEntity.ok(createdShoppingCart);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartDTO> findShoppingCartById(@PathVariable Long id){
        ShoppingCartDTO shoppingCartById = shoppingCartService.findShoppingCartById(id);
        return ResponseEntity.ok(shoppingCartById);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id){
        shoppingCartService.deleteShoppingCart(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/items")
    public ResponseEntity<List<ShoppingCartItemDTO>> findAllItemsInCart(@PathVariable Long id){
        List<ShoppingCartItemDTO> itemsList = shoppingCartService.findAllItemsInShoppingCart(id);
        return ResponseEntity.ok(itemsList);
    }
    @GetMapping
    public ResponseEntity<List<ShoppingCartDTO>> allShoppingCarts(){
        List<ShoppingCartDTO> shoppingCarts = shoppingCartService.findAllShoppingCart();
        return ResponseEntity.ok(shoppingCarts);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(@PathVariable Long id, @RequestBody ShoppingCartDTO shoppingCartDTO){
        ShoppingCartDTO updatedShoppingCart = shoppingCartService.updateShoppingCart(id,shoppingCartDTO);
        return ResponseEntity.ok(updatedShoppingCart);
    }
    @PostMapping("/{id}/update-total-price")
    public ResponseEntity<ShoppingCartDTO> updateTotalPrice(@PathVariable Long id){
        ShoppingCartDTO shoppingCartDTO = shoppingCartService.updateTotalPrice(id);
        return ResponseEntity.ok(shoppingCartDTO);
    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(){
        shoppingCartService.deleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrement(){
        shoppingCartService.resetAutoIdIncrement();
        return ResponseEntity.noContent().build();
    }
}
