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
    private Integer userID;
    private Integer roleID;
    private String fullName;
    private String email;
    private String passwordHash; // BCrypt hashed
    private Double balance;
    private Timestamp createdAt;

    // Constructor without auto-generated fields (for insert)
    public User(Integer roleID, String fullName, String email, String passwordHash, Double balance) {
        this.roleID = roleID;
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
    }
}