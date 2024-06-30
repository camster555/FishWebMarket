package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.specificDTO.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing product admin.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-10
 * @version 1.0.0
 */

@Service
public class ProductAdminService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @PersistenceContext
    private EntityManager entityManager;

    public ProductDTO addProduct(ProductDTO productDTO){
        /*
        //the exception here need to change from null to something else in the future as if body request is null or
        //empty it won't even reach controller class which means it won't reach service class and would not reach this
        //method as the 'HttpMessageNotReadableException' happens before it reaches the controller class if the
        //request body is empty and nothing is being sent from http.
        if (productDTO == null){
            System.out.println("Invalid input: " + productDTO);
            throw new InvalidInputException("Invalid data request");
        }
         */

        //null if it hasn't been set or assigned any value and empty if it has been assigned an empty string ("").
        //trim for accidental white spaces
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            System.out.println("Invalid input: name is empty or null");
            throw new InvalidInputException("Product name cannot be empty or null");
        }

        if (productDTO.getDescription() == null || productDTO.getDescription().trim().isEmpty()) {
            System.out.println("Invalid input: description is empty or null");
            throw new InvalidInputException("Product description cannot be empty or null");
        }

        //Don't need to catch exception here since it needs to be handled on the frontend, it won't even reach this try and catch.
        /*
        try {
            Double.parseDouble(String.valueOf(productDTO.getPrice()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: price is not a number");
            throw new InvalidInputException("Product price must be a number");
        }

        try {
            Integer.parseInt(String.valueOf(productDTO.getStockQuantity()));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: stock quantity is not a number");
            throw new InvalidInputException("Product stock quantity must be a number");
        }
         */

        if (productDTO.getPrice() <= 0) {
            System.out.println("Invalid input: price is zero or negative");
            throw new InvalidInputException("Product price must be greater than zero");
        }

        if (productDTO.getStockQuantity() <= 0) {
            System.out.println("Invalid input: stock quantity is zero or negative");
            throw new InvalidInputException("Product stock quantity must be greater than zero");
        }

        Product product = productService.convertToEntity(productDTO);
        //logger.info("Converted ProductDTO to Product: {}", product);
        Product newProduct = productRepository.save(product);
        //logger.info("Saved new product: {}", newProduct);
        ProductDTO productDTO1 = productService.convertToDTO(newProduct);
        //logger.info("Converted saved product to ProductDTO: {}", productDTO1);
        return productDTO1;
    }

    public ProductDTO updateProduct(Long id,ProductDTO productDTO){
        //findById() WILL return 'Optional<T>' so need to handle exception accordingly.
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setId(productDTO.getId());
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStockQuantity(productDTO.getStockQuantity());

        Product updatedProduct = productRepository.save(existingProduct);
        return productService.convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id){
        if (id == null) {
            throw new InvalidInputException("Invalid input: ID is required.");
        }

        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Product not found" + id);
        }
        productRepository.deleteById(id);
    }

    public void deleteAllProduct(){
        if (productRepository.count() == 0){
            throw new ResourceNotFoundException("Product list already empty");
        }
        productRepository.deleteAll();
    }
    //Transactional annotation ensures this method is used during transaction, since when request is made, spring starts
    //new 'transaction during runtime ensuring is that any part of the method that is transactional fails that database
    //goes back to the previous state.
    @Transactional
    public void resetAutoIdIncrementProduct(){
        if (productRepository.count()==0){
            entityManager.createNativeQuery("ALTER TABLE product AUTO_INCREMENT = 1").executeUpdate();
        }else {
            throw new InvalidInputException("Product list not empty, cannot reset auto increment to 1");
        }
    }
}
