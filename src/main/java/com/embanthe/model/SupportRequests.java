package com.embanthe.model;


public class SupportRequests {

    private int requestId;
    private int userId;
    private String title;
    private String message;
    private String status;
    private java.sql.Timestamp createdAt;
    private String processNote; // ghi chú xử lý private
    private java.sql.Timestamp processedAt; // ngày xử lý


    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getProcessNote() {
        return processNote;
    }

    public void setProcessNote(String processNote) {
        this.processNote = processNote;
    }

    public java.sql.Timestamp getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(java.sql.Timestamp processedAt) {
        this.processedAt = processedAt;
    }
}
