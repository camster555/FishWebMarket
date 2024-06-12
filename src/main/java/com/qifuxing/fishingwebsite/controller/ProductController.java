package com.qifuxing.fishingwebsite.controller;

import com.qifuxing.fishingwebsite.service.ProductService;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This controller manages product-related operations.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-25
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> productDTOList = productService.getAllProducts();
        return ResponseEntity.ok(productDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id){
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO);
    }

    /*
    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        //logger.info("Received ProductDTO: {}", productDTO);
        ProductDTO newProduct = productService.addProduct(productDTO);
        //logger.info("Returning new ProductDTO: {}", newProduct);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        ProductDTO updateProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteID(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll(){
        productService.deleteAll();
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/reset-auto-increment")
    public ResponseEntity<Void> resetAutoIncrement(){
        productService.resetAutoIdIncrement();
        return ResponseEntity.noContent().build();
    }

     */
}
