package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.service.ProductAdminService;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages product admin-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/admin/product")
public class ProductAdminController {

    @Autowired
    private ProductAdminService productAdminService;

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        //logger.info("Received ProductDTO: {}", productDTO);
        ProductDTO newProduct = productAdminService.addProduct(productDTO);
        //logger.info("Returning new ProductDTO: {}", newProduct);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        ProductDTO updateProduct = productAdminService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productAdminService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> deleteAllProducts(){
        productAdminService.deleteAllProduct();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/products/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrementProducts(){
        productAdminService.resetAutoIdIncrementProduct();
        return ResponseEntity.noContent().build();
    }
}
