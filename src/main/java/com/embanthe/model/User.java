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
    private Integer id;
    private String username;
    private String password; // BCrypt hashed
    private String role;     // ADMIN or USER
    private Timestamp createdAt;




    // Constructor without timestamps (for insert)
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}