package com.qifuxing.fishingwebsite.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This entity represents orders in the system.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-25
 * @version 1.0.0
 */

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderID")
    private Long id;

    //Since @ManyToOne is on the 'private User user;' which means many order instance can be linked to one user instance,
    //then after '@JoinColumn(name = "user_id")' it means that the foreign key column in order class called user_id will
    //link to the primary key which is id field in User class because Spring auto matches it in runtime.
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //so from @manyToMany it means that many order instance can have many product instance and vice versa.
    @ManyToMany
    //@joinTable means spring will auto create a join table during runtime.
    @JoinTable(
            //the name of the new table created during runtime by spring.
            name = "order_product",
            //joinColumns specifies the foreign key column in the join table that references the primary key of the owning entity.
            // Since @JoinTable is placed in the Order class, the owning entity is Order.
            // The name attribute in @JoinColumn defines the column name in the join table.
            // In this case, the x column will store the primary key values of the Order entity.
            joinColumns = @JoinColumn(name = "order_id"),
            // inverseJoinColumns specifies the foreign key column in the join table that references the primary key of the other entity.
            // The y column will store the primary key values of the Product entity.
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products;

    @Column(name = "order_date")
    private Date orderDate;
    private String status;

    //not getUser method can use methods from User class.
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //compare objects base on their fields which would be id in this class.
    @Override
    public boolean equals(Object o){
        //first check if objects are of the same instance
        if (this == o) return true;
        //check if other object is not from the same class or is null
        if (o == null || getClass() != o.getClass()) return false;

        //make the other object to object Order which is current class
        Order order = (Order) o;

        /*
        //so it is first seeing if id is null, if not then checks if id equals order id which holds id from the o object.
        return id != null ? id.equals(order.id) : order.id == null;
         */

        //shorter version of using Objects.equal ti handle null checks and equality
        return Objects.equals(id,order.id);
    }

    //so when object are equal from the equals method then they must have same hashcode
    @Override
    public int hashCode(){
        //return hashcode of the id if not null, otherwise return 0.
        return id != null ? id.hashCode() : 0;
    }
}
