package com.embanthe.dao;

import com.embanthe.model.User;
import com.embanthe.util.DBContext;
import org.mindrot.jbcrypt.BCrypt;

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
    public User getUserByEmail(String email) throws SQLException {
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
    // Cập nhật thông tin cơ bản của User theo user_id
    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE Users SET " +
                "username = ?, " +
                "full_name = ?, " +
                "email = ?, " +
                "phone = ? " +
                "WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getUserId());
            return ps.executeUpdate() > 0;
        }
    }
    // Đổi mật khẩu theo user_id
    public boolean changePassword(int userId, String newPasswordHash) throws SQLException {
        String sql = "UPDATE Users SET password_hash = ? WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPasswordHash);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }
    public void insertGoogleUser(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, full_name, password_hash, role, balance, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullName());
            ps.setString(4, ""); // đánh dấu đây là user Google (không có password thật)
            ps.setString(5, user.getRole() != null ? user.getRole() : "CUSTOMER");
            ps.setDouble(6, user.getBalance() != null ? user.getBalance() : 0.0);
            ps.setString(7, user.getStatus() != null ? user.getStatus() : "ACTIVE");

            ps.executeUpdate();
        }
    }
    // Map dữ liệu từ ResultSet sang User model mới
    private User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .fullName(rs.getString("full_name"))
                .email(rs.getString("email"))
                .passwordHash(rs.getString("password_hash"))
                .phone(rs.getString("phone"))
                .role(rs.getString("role"))
                .balance(rs.getDouble("balance"))
                .status(rs.getString("status"))
                .createdAt(rs.getTimestamp("created_at"))
                .build();
    }


    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();
            //List<User> users = dao.getAll();
           // User user = dao.getUserByEmail("hoangluffy0981@gmail.com");

//            if (user != null) {
//                user.setUsername("luffy");
//                user.setFullName("Monkey D. Luffy");
//                user.setEmail("hoangluffy098@gmail.com");
//                user.setPhone("0123456789");
//
//                boolean updated = dao.updateUser(user);
//                System.out.println("Update thành công? " + updated);
//                System.out.println(user);
//            }
//            else {
//                System.out.println("error");
//            }

//            System.out.println("✅ Found " + users.size() + " users in DB:");
//            for (User u : users) {
//                System.out.println(u);
//            }

//            User user = dao.getUserByEmail("hoangluffy098@gmail.com");
//            System.out.println("User by email: " + user);

//            boolean exists = dao.checkEmailExist("customer02@example.com");
//            System.out.println("📧 Email exists? " + exists);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
