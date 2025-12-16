package com.embanthe.model;

import java.sql.Date;
import java.sql.Timestamp;

public class CardItems {

    private int cardItemId;
    private int productId;
    private Integer orderId;
    private String serialNumber;
    private String cardCode;
    private Date expirationDate;
    private String status;
    private Timestamp createdAt;

    public CardItems() {
    }

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
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
}
