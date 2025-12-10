package com.embanthe.DAO;

import com.embanthe.model.User;
import com.embanthe.util.DBContext;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AuthDAO {
    private final Connection connection;

    public AuthDAO() throws SQLException {
        this.connection = DBContext.getInstance().getConnection();
    }


    public User login(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashed = rs.getString("PasswordHash");
                    // So sánh password plaintext với hash trong DB
                    if (BCrypt.checkpw(password, hashed)) {
                        return mapRow(rs);
                    }
                }
            }
        }
        return null;
    }

    public boolean register(String fullName, String email, String rawPassword) throws SQLException {
        // 1. Kiểm tra email đã tồn tại chưa
        if (isEmailExists(email)) {
            return false; // Email đã được dùng
        }

        // 2. Hash mật khẩu bằng BCrypt
        String passwordHash = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));

        String sql = "INSERT INTO Users (RoleID, FullName, Email, PasswordHash, Balance, CreatedAt) " +
                "VALUES (1, ?, ?, ?, 0.0, NOW())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, passwordHash);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Kiểm tra email đã tồn tại trong DB chưa
     */
    private boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // có dòng → email đã tồn tại
            }
        }
    }

    // Map dữ liệu từ ResultSet sang User
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


    // Test nhanh
    public static void main(String[] args) {
//        try {
//            AuthDAO authDAO = new AuthDAO();
//            Scanner sc = new Scanner(System.in);
//
//            System.out.print("Nhập email: ");
//            String email = sc.nextLine();
//
//            System.out.print("Nhập mật khẩu: ");
//            String password = sc.nextLine();
//
//            User user = authDAO.login(email, password);
//            if (user != null) {
//                System.out.println("✅ Login thành công: " + user.getFullName());
//            } else {
//                System.out.println("❌ Login thất bại!");
//            }
//
//            sc.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            AuthDAO authDAO = new AuthDAO();
            Scanner sc = new Scanner(System.in);

            System.out.println("=== TEST ĐĂNG KÝ TÀI KHOẢN ===");
            System.out.print("Nhập họ tên: ");
            String fullName = sc.nextLine();

            System.out.print("Nhập email: ");
            String email = sc.nextLine();

            System.out.print("Nhập mật khẩu: ");
            String password = sc.nextLine();

            System.out.print("Nhập lại mật khẩu: ");
            String confirm = sc.nextLine();

            if (!password.equals(confirm)) {
                System.out.println("Mật khẩu không khớp!");
            } else {
                boolean success = authDAO.register(fullName, email, password);
                if (success) {
                    System.out.println("ĐĂNG KÝ THÀNH CÔNG!");
                    System.out.println("→ Tài khoản Customer (RoleID = 1) đã được tạo.");
                    System.out.println("→ Bạn có thể đăng nhập bằng email và mật khẩu vừa nhập.");
                } else {
                    System.out.println("ĐĂNG KÝ THẤT BẠI! Email này đã tồn tại.");
                }
            }

            sc.close();

        } catch (SQLException e) {
            System.out.println("Lỗi kết nối CSDL:");
            e.printStackTrace();
        }


    }
}
