package com.qifuxing.fishingwebsite.specificDTO;

import com.qifuxing.fishingwebsite.model.Product;

import java.util.Date;
import java.util.Set;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This DTO is used for order requests.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-26
 * @version 1.0.0
 */

public class OrderDTO {
    private Long id;
    //here user can be Long instead of User because we only need the user_id to link to the user table.
    private Long user_id;
    private Set<Long> productIDs;
    private Date orderDate;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getProductIDs() {
        return productIDs;
    }

    public void setProductIDs(Set<Long> productIDs) {
        this.productIDs = productIDs;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
}
