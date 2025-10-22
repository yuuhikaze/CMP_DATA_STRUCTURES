package com.yuuhikaze.ed202510.EI;

/**
 * Entity class representing an item in a shopping cart
 */
public class EI05_CartItem_Entity {
    private EI05_Product_Entity product;
    private int quantity;

    public EI05_CartItem_Entity(EI05_Product_Entity product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public EI05_Product_Entity getProduct() {
        return product;
    }

    public void setProduct(EI05_Product_Entity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{product=" + product.getName() + ", quantity=" + quantity + ", total=$" + getTotalPrice() + "}";
    }
}
