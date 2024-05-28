package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents shopping cart item in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    //join column annotation means the column name which is the foreign key.
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    //could be many shoppingCartItem with the same product, like 5 item that is both product A.
    private int quantity;
    @Column(name = "price_of_individual_product")
    private double priceOfIndividualProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceOfIndividualProduct() {
        return priceOfIndividualProduct;
    }

    public void setPriceOfIndividualProduct(double priceOfIndividualProduct) {
        this.priceOfIndividualProduct = priceOfIndividualProduct;
    }
}
