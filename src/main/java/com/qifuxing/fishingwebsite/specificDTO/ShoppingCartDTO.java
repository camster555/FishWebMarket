package com.qifuxing.fishingwebsite.specificDTO;

import java.util.List;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This DTO is used for user shopping cart requests.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */


public class ShoppingCartDTO {

    private Long id;
    private Long userId;
    private List<ShoppingCartItemDTO> items;
    private double totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ShoppingCartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItemDTO> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
