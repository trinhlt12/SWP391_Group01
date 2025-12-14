package com.embanthe.model;

public class CardItems {

    private int cardItemId;
    private int productId;
    private int orderId; // 0 nếu chưa bán
    private String serialNumber;
    private String cardCode;
    private java.sql.Date expirationDate;
    private String status;
    private java.sql.Timestamp createdAt;

    // Thuộc tính phụ để hiển thị tên sản phẩm và giá (nếu join với products)
    private String productName;
    private int price;

    // Getter/Setter
    public int getCardItemId() {
        return cardItemId;
    }
    public void setCardItemId(int cardItemId) {
        this.cardItemId = cardItemId;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCardCode() {
        return cardCode;
    }
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public java.sql.Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(java.sql.Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(java.sql.Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}