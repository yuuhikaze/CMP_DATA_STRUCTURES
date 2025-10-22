package com.yuuhikaze.ed202510.EI;

/**
 * Entity class representing a product in the Supermaxi supermarket
 */
public class EI05_Product_Entity {
    private String name;
    private String category;
    private double price;
    private String productId;

    public EI05_Product_Entity(String productId, String name, String category, double price) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Product{id='" + productId + "', name='" + name + "', category='" + category + "', price=$" + price + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EI05_Product_Entity product = (EI05_Product_Entity) obj;
        return productId.equals(product.productId);
    }

    @Override
    public int hashCode() {
        return productId.hashCode();
    }
}
