package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o){
        //first check if objects are of the same instance
        if (this == o) return true;
        //check if other object is not from the same class or is null
        if (o == null || getClass() != o.getClass()) return false;

        //make the other object to current object class.
        ShoppingCart shoppingCart = (ShoppingCart) o;

        /*
        //so it is first seeing if id is null, if not then checks if id equals order id which holds id from the o object.
        return id != null ? id.equals(order.id) : order.id == null;
         */

        //shorter version of using Objects.equal ti handle null checks and equality
        return Objects.equals(id,shoppingCart.id);
    }

    //so when object are equal from the equals method then they must have same hashcode
    @Override
    public int hashCode(){
        //return hashcode of the id if not null, otherwise return 0.
        return id != null ? id.hashCode() : 0;
    }
}
