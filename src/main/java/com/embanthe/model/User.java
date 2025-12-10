package com.embanthe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer userId;          // user_id
    private String username;         // username
    private String email;            // email
    private String passwordHash;     // password_hash (BCrypt hashed)
    private String fullName;         // full_name
    private String phone;            // phone
    private String role;             // role (CUSTOMER/ADMIN)
    private Double balance;          // balance
    private String status;           // status (ACTIVE/LOCKED)
    private Timestamp createdAt;     // created_at
    private Timestamp updatedAt;     // updated_at

    // Constructor dùng khi insert (không có auto-generated fields)
    public User(String username, String email, String passwordHash,
                String fullName, String phone, String role,
                Double balance, String status) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.phone = phone;
        this.role = role;
        this.balance = balance;
        this.status = status;
    }
}
