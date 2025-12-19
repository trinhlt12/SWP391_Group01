package com.embanthe.model;

import java.sql.Timestamp;

public class Products {

    private int productId;
    private int providerId;
    private int categoryId;
    private String productName;
    private double price;
    private int quantity;
    private String imageUrl;


    private String status;
    private Timestamp createdAt;

    private String providerName;
    private String categoryName;


    public Products() {
    }

    public Products(int productId, String productName, double price, int quantity, String imageUrl, String providerName, String categoryName) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.providerName = providerName;
        this.categoryName = categoryName;
    }

    // --- Getters và Setters ---

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Getter/Setter cho DTO (ProviderName, CategoryName)
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + productId +
                ", name='" + productName + '\'' +
                ", price=" + price +
                ", provider='" + providerName + '\'' +
                ", category='" + categoryName + '\'' +
                '}';
    }
}