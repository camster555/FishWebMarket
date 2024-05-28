package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents a product in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-25
 * @version 1.0.0
 */

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;
    @Column(name = "stock_quantity")
    private int stockQuantity;
    //here it means that this relationship is managed by the other entity which is order class entity.
    @ManyToMany(mappedBy = "products")
    private Set<Order> orderSet;

    public Set<Order> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<Order> orderSet) {
        this.orderSet = orderSet;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        //first check if objects are of the same instance
        if (this == o) return true;
        //check if other object is not from the same class or is null
        if (o == null || getClass() != o.getClass()) return false;

        //make the other object to current object class.
        Product product = (Product) o;

        /*
        //so it is first seeing if id is null, if not then checks if id equals order id which holds id from the o object.
        return id != null ? id.equals(order.id) : order.id == null;
         */

        //shorter version of using Objects.equal ti handle null checks and equality
        return Objects.equals(id,product.id);
    }

    //so when object are equal from the equals method then they must have same hashcode
    @Override
    public int hashCode(){
        //return hashcode of the id if not null, otherwise return 0.
        return id != null ? id.hashCode() : 0;
    }
}
