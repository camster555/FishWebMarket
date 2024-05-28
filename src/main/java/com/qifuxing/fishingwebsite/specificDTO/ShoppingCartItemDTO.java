package com.qifuxing.fishingwebsite.specificDTO;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This DTO is used for user shopping cart item requests.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-27
 * @version 1.0.0
 */

public class ShoppingCartItemDTO {
    private Long id;
    private Long shoppingCartId;
    private Long productId;
    private int quantity;
    private double priceOfIndividualProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
