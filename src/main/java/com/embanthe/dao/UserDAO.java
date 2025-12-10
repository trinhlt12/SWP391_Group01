package com.embanthe.dao;

import com.embanthe.model.User;
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
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        }
        return users;
    }
    // Lấy User theo Email
    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE Email = ?";
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

    public boolean checkEmailExist(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có kết quả => email tồn tại
            }
        }
    }
    private User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
                .userID(rs.getInt("UserID"))
                .roleID(rs.getInt("RoleID"))
                .fullName(rs.getString("FullName"))
                .email(rs.getString("Email"))
                .passwordHash(rs.getString("PasswordHash"))
                .balance(rs.getDouble("Balance"))
                .createdAt(rs.getTimestamp("CreatedAt"))
                .build();
    }


    // Test trực tiếp trong DAO
    public static void main(String[] args) throws SQLException {
//        try {
//            UserDAO dao = new UserDAO();
//            List<User> users = dao.getAll();
//            System.out.println("✅ Found " + users.size() + " users in DB:");
//            for (User u : users) {
//                System.out.println(u);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        UserDAO dao = new UserDAO();
//        User user = dao.getUserByEmail("nguyenvana@example.com");
//        System.out.println(user);

//        boolean exists = dao.checkEmailExist("tranthib@example.com");
//        System.out.println("📧 Email exists? " + exists);
    }

}
