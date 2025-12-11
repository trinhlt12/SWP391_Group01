package com.embanthe.model;

public class CardItems {

  private long cardItemId;
  private long productId;
  private long orderId;
  private String serialNumber;
  private String cardCode;
  private java.sql.Date expirationDate;
  private String status;
  private java.sql.Timestamp createdAt;


  public long getCardItemId() {
    return cardItemId;
  }

  public void setCardItemId(long cardItemId) {
    this.cardItemId = cardItemId;
  }


  public long getProductId() {
    return productId;
  }

  public void setProductId(long productId) {
    this.productId = productId;
  }


  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
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

}
