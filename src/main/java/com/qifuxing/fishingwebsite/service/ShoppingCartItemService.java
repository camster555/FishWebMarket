package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.model.ShoppingCart;
import com.qifuxing.fishingwebsite.model.ShoppingCartItem;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.repository.ShoppingCartItemRepository;
import com.qifuxing.fishingwebsite.repository.ShoppingCartRepository;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service manages business logic for managing shopping cart items.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@Service
public class ShoppingCartItemService {
    @Autowired
    private ShoppingCartItemRepository shoppingCartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;

    //for shoppingCartItem id need to manually create in update method or else it would not update correctly.
    public ShoppingCartItem convertToEntity(ShoppingCartItemDTO shoppingCartItemDTO){
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        //In DTO class, getShoppingCartId returns long but in ShoppingCartItem class it returns a shoppingCart, so
        //we need to use id from DTO and ShoppingCart repository to find the right ShoppingCart
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartItemDTO.getShoppingCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart id:"+shoppingCartItemDTO.getShoppingCartId()+" not found."));
        shoppingCartItem.setShoppingCart(shoppingCart);
        Product product = productRepository.findById(shoppingCartItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product cart id:"+shoppingCartItemDTO.getProductId()+" not found."));
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setQuantity(shoppingCartItemDTO.getQuantity());
        shoppingCartItem.setPriceOfIndividualProduct(shoppingCartItemDTO.getPriceOfIndividualProduct());
        return shoppingCartItem;
    }

    public ShoppingCartItemDTO convertToDTO(ShoppingCartItem shoppingCartItem){
        ShoppingCartItemDTO shoppingCartItemDTO = new ShoppingCartItemDTO();
        shoppingCartItemDTO.setId(shoppingCartItem.getId());
        //when request made, it will show shopping cart id, so we get the getShoppingCart method and it's id.
        shoppingCartItemDTO.setShoppingCartId(shoppingCartItem.getShoppingCart().getId());
        shoppingCartItemDTO.setProductId(shoppingCartItem.getProduct().getId());
        shoppingCartItemDTO.setQuantity(shoppingCartItem.getQuantity());
        shoppingCartItemDTO.setPriceOfIndividualProduct(shoppingCartItem.getPriceOfIndividualProduct());
        return shoppingCartItemDTO;
    }

    public void validateShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO){
        if (shoppingCartItemDTO.getQuantity() <= 0 || shoppingCartItemDTO.getPriceOfIndividualProduct() <= 0){
            throw new InvalidInputException("Invalid input of quantity or price of product.");
        }
    }

    public ShoppingCartItemDTO saveShoppingCartItem(ShoppingCartItemDTO shoppingCartItemDTO){
        validateShoppingCartItem(shoppingCartItemDTO);
        ShoppingCartItem shoppingCartItem = convertToEntity(shoppingCartItemDTO);
        ShoppingCartItem shoppingCartItemSaved = shoppingCartItemRepository.save(shoppingCartItem);
        return convertToDTO(shoppingCartItemSaved);
    }

    public ShoppingCartItemDTO findById(Long id){
        ShoppingCartItem shoppingCartItem = shoppingCartItemRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shopping cart item not found with id:"+id));
        return convertToDTO(shoppingCartItem);
    }

    public ShoppingCartItemDTO updateShoppingCartItem(Long id, ShoppingCartItemDTO shoppingCartItemDTO){
        validateShoppingCartItem(shoppingCartItemDTO);

        if (!shoppingCartItemRepository.existsById(id)){
            throw new ResourceNotFoundException("Shopping cart item with id:"+id+" not found.");
        }

        ShoppingCartItem shoppingCartItem = convertToEntity(shoppingCartItemDTO);
        //set id here when creating new entity to ensure update correctly instead of creating new entity which is
        //what happens when set id in the entity converter instead.
        shoppingCartItem.setId(id);
        ShoppingCartItem savedShoppingCartItem = shoppingCartItemRepository.save(shoppingCartItem);
        return convertToDTO(savedShoppingCartItem);
    }

    public void deleteById(Long id){
        if (!shoppingCartItemRepository.existsById(id)){
            throw new ResourceNotFoundException("Shopping cart item with id:"+id+" not found.");
        }

        shoppingCartItemRepository.deleteById(id);
    }

}
