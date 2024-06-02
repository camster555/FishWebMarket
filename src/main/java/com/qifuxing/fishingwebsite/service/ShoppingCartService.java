package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.InvalidInputException;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import com.qifuxing.fishingwebsite.model.Product;
import com.qifuxing.fishingwebsite.model.ShoppingCart;
import com.qifuxing.fishingwebsite.model.ShoppingCartItem;
import com.qifuxing.fishingwebsite.model.User;
import com.qifuxing.fishingwebsite.repository.ProductRepository;
import com.qifuxing.fishingwebsite.repository.ShoppingCartItemRepository;
import com.qifuxing.fishingwebsite.repository.ShoppingCartRepository;
import com.qifuxing.fishingwebsite.repository.UserRepository;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartDTO;
import com.qifuxing.fishingwebsite.specificDTO.ShoppingCartItemDTO;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service manages business logic for managing shopping carts.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    public ShoppingCart convertToEntity(ShoppingCartDTO shoppingCartDTO){
        ShoppingCart shoppingCart = new ShoppingCart();
        User user = userRepository.findById(shoppingCartDTO.getUserId()).orElseThrow(
                ()-> new ResourceNotFoundException("User with this id not found:"+shoppingCartDTO.getUserId()));
        shoppingCart.setUser(user);
        shoppingCart.setTotalPrice(shoppingCartDTO.getTotalPrice());
        List<ShoppingCartItem> itemsEntity = shoppingCartDTO.getItems().stream()
                .map(this::convertToItemEntity).collect(Collectors.toList());
        shoppingCart.setItems(itemsEntity);
        return shoppingCart;
    }

    public ShoppingCartDTO convertToDTO(ShoppingCart shoppingCart){
        ShoppingCartDTO shoppingCartDTO = new ShoppingCartDTO();
        shoppingCartDTO.setId(shoppingCart.getId());
        shoppingCartDTO.setTotalPrice(shoppingCart.getTotalPrice());
        shoppingCartDTO.setUserId(shoppingCart.getUser().getId());
        //'this' refers to the current instance of the class where this code resides,
        //and the method after '::' (convertToItemDTO) is a method of this class, so this refers to the instance of
        //this class as we use a method in this class inside map method.
        List<ShoppingCartItemDTO> itemDTOS = shoppingCart.getItems().stream()
                .map(this::convertToItemDTO).collect(Collectors.toList());
        shoppingCartDTO.setItems(itemDTOS);
        return shoppingCartDTO;
    }

    public ShoppingCartItemDTO convertToItemDTO(ShoppingCartItem shoppingCartItem) {
        ShoppingCartItemDTO shoppingCartItemDTO = new ShoppingCartItemDTO();
        shoppingCartItemDTO.setShoppingCartId(shoppingCartItem.getShoppingCart().getId());
        shoppingCartItemDTO.setId(shoppingCartItem.getId());
        shoppingCartItemDTO.setProductId(shoppingCartItem.getProduct().getId());
        shoppingCartItemDTO.setPriceOfIndividualProduct(shoppingCartItem.getPriceOfIndividualProduct());
        shoppingCartItemDTO.setQuantity(shoppingCartItem.getQuantity());
        return shoppingCartItemDTO;
    }

    public ShoppingCartItem convertToItemEntity(ShoppingCartItemDTO shoppingCartItemDTO){
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartItemDTO.getShoppingCartId()).orElseThrow(
                () -> new ResourceNotFoundException("Shopping cart with this id not found:"+ shoppingCartItemDTO.getShoppingCartId()));
        shoppingCartItem.setShoppingCart(shoppingCart);
        shoppingCartItem.setId(shoppingCartItemDTO.getId());
        Product product = productRepository.findById(shoppingCartItemDTO.getProductId()).orElseThrow(
                () -> new ResourceNotFoundException("Product with this id not found:"+shoppingCartItemDTO.getProductId()));
        shoppingCartItem.setProduct(product);
        shoppingCartItem.setPriceOfIndividualProduct(shoppingCartItemDTO.getPriceOfIndividualProduct());
        shoppingCartItem.setQuantity(shoppingCartItemDTO.getQuantity());
        return shoppingCartItem;
    }

    public ShoppingCartDTO updateTotalPrice(Long cartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId).orElseThrow(
                ()-> new ResourceNotFoundException("No cart with this id found."+cartId));
        if (shoppingCart.getItems().isEmpty()){
            shoppingCart.setTotalPrice(00.00);
        } else {
            //item here is not a set name it can be anything like x -> x.getQuantity() for example, usually
            // 'this::someMethod' is used for method references but if calculation then use lambda in this case.
            double totalPrice = shoppingCart.getItems().stream()
                    .mapToDouble(item -> item.getQuantity() * item.getPriceOfIndividualProduct())
                    .sum();
            shoppingCart.setTotalPrice(totalPrice);
        }
        shoppingCartRepository.save(shoppingCart);
        return convertToDTO(shoppingCart);
    }

    public ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO){
        ShoppingCart shoppingCart = convertToEntity(shoppingCartDTO);
        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        return convertToDTO(savedShoppingCart);
    }

    public ShoppingCartDTO findShoppingCartById(Long id){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shopping cart with id not found:"+id));
        return convertToDTO(shoppingCart);
    }

    public void deleteShoppingCart(Long id){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shopping cart with id not found:"+id));
        shoppingCartRepository.delete(shoppingCart);
    }

    public List<ShoppingCartItemDTO> findAllItemsInShoppingCart(Long shoppingCartId){
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(
                () -> new ResourceNotFoundException("Shopping cart with id not found:"+shoppingCartId));
        return shoppingCart.getItems().stream().map(this::convertToItemDTO).collect(Collectors.toList());
    }

    public List<ShoppingCartDTO> findAllShoppingCart(){
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAll();
        if (shoppingCarts.isEmpty()){
            throw new ResourceNotFoundException("No shopping cart found in database");
        }
        return shoppingCarts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    //can update anything except for the individual items in the cart since that is performed in the shoppingCartItem
    //service layer, so no need to explicit update them, and when converting method is used, it will auto save
    //the original items as well.
    public ShoppingCartDTO updateShoppingCart(Long id, ShoppingCartDTO shoppingCartDTO){
        //NO NEED TO SET ID IN THIS METHOD COMPARE TO ORDER UPDATE METHOD IS CAUSE THIS METHOD IS TAKEN DIRECTLY
        //FROM DATABASE AND WE CHECKED AND FOUND THE CART DIRECTLY FROM DATABASE AND PUT INTO existingShoppingCart
        //BUT IN ORDER UPDATE METHOD, WE USE THE CONVERSION METHOD FIRST AND THEN SET ID AFTER CAUSE IN THE CONVERSION METHOD
        //A NEW INSTANCE OF ORDER IS CREATED WITHOUT SETTING THE ID FOR THAT ORDER SO I SET IT AFTER SO APPLICATION KNOWS
        //WHICH ORDER TO UPDATE AND REASON WE DON'T CONVERSION FIRST IN CART UPDATE METHOD CAUSE WE DON'T UPDATE EVERYTHING
        //LIKE THE INDIVIDUAL ITEMS IN THE LIST.
        ShoppingCart existingShoppingCart = shoppingCartRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Shopping cart with id not found:"+id));
        User user = userRepository.findById(shoppingCartDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User with this id not found:"+shoppingCartDTO.getUserId()));
        existingShoppingCart.setUser(user);
        existingShoppingCart.setTotalPrice(shoppingCartDTO.getTotalPrice());
        ShoppingCart updateShoppingCart = shoppingCartRepository.save(existingShoppingCart);
        return convertToDTO(updateShoppingCart);
    }
    public void deleteAll(){
        if (shoppingCartRepository.count() == 0){
            throw new ResourceNotFoundException("Shopping cart already empty");
        }
        shoppingCartRepository.deleteAll();
    }
    //Transactional annotation ensures this method is used during transaction, since when request is made, spring starts
    //new 'transaction during runtime ensuring is that any part of the method that is transactional fails that database
    //goes back to the previous state.
    @Transactional
    public void resetAutoIdIncrement(){
        if (shoppingCartRepository.count()==0){
            entityManager.createNativeQuery("ALTER TABLE shopping_cart AUTO_INCREMENT = 1").executeUpdate();
        }else {
            throw new InvalidInputException("Shopping cart list not empty, cannot reset auto increment to 1");
        }
    }

}
