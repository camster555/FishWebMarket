package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;
import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents shopping cart in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //mappedBy means ShoppingCart is the class that manages this relationship for CRUD methods, then cascade means
    //all CRUD operation that affect ShoppingCart class will also affect ShoppingCartItem class,
    //then orphanRemoval means if a ShoppingCartItem is removed or deleted from the items list then it will be deleted from database.
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items;

    private double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
