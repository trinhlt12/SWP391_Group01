package com.embanthe.dao;

import com.embanthe.model.Users;
import com.embanthe.model.Users;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final Connection connection;

    public UserDAO() throws SQLException {
        this.connection = DBContext.getInstance().getConnection();
    }

    // Lấy tất cả User
    public List<Users> getAll() throws SQLException {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        }
        return users;
    }

    // Lấy User theo Email
    public Users getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean checkEmailExist(String email) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Map dữ liệu từ ResultSet sang User model mới
    private Users mapRow(ResultSet rs) throws SQLException {
        return Users.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .fullName(rs.getString("full_name"))
                .email(rs.getString("email"))
                .passwordHash(rs.getString("password_hash")) // map từ cột snake_case sang field camelCase
                .phone(rs.getString("phone"))
                .role(rs.getString("role"))
                .balance(rs.getDouble("balance"))
                .status(rs.getString("status"))
                .createdAt(rs.getTimestamp("created_at"))
                .build();
    }

    // Test trực tiếp trong DAO
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();
            List<Users> users = dao.getAll();
            System.out.println("✅ Found " + users.size() + " users in DB:");
            for (Users u : users) {
                System.out.println(u);
            }

            Users user = dao.getUserByEmail("customer01@example.com");
            System.out.println("User by email: " + user);

            boolean exists = dao.checkEmailExist("customer02@example.com");
            System.out.println("📧 Email exists? " + exists);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
