package com.embanthe.dao;

import com.embanthe.model.Users;
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

    public Users login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashed = rs.getString("password_hash");
                    if (BCrypt.checkpw(password, hashed)) {
                        return mapRow(rs);
                    }
                }
            }
        }
        return null;
    }

    public boolean register(String username, String fullName, String email, String rawPassword, String phone) throws SQLException {
        if (isEmailExists(email)) {
            return false;
        }

        String passwordHash= BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));

        String sql = "INSERT INTO Users (username, full_name, email, password_hash, phone, role, balance, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, 'CUSTOMER', 0.0, 'ACTIVE', NOW())";


        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, passwordHash);
            ps.setString(5, phone);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public boolean isPhoneExists(String phone) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE username  = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    public boolean isFullNameExists(String fullname) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE full_name  = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, fullname);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
    private Users mapRow(ResultSet rs) throws SQLException {
        return Users.builder()
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
            AuthDAO authDAO = new AuthDAO();
            boolean isFullNameExists = authDAO.isFullNameExists("Hoang");
            System.out.println(isFullNameExists);
//            Scanner sc = new Scanner(System.in);
//
//            System.out.println("=== TEST ĐĂNG NHẬP TÀI KHOẢN ===");
//            System.out.print("Nhập email: ");
//            String email = sc.nextLine();
//
//            System.out.print("Nhập mật khẩu: ");
//            String password = sc.nextLine();
//
//            Users user = authDAO.login(email, password);
//            if (user != null) {
//                System.out.println("✅ Đăng nhập thành công!");
//                System.out.println("Xin chào, " + user.getFullName());
//                System.out.println("Role: " + user.getRole());
//                System.out.println("Balance: " + user.getBalance());
//            } else {
//                System.out.println("❌ Đăng nhập thất bại! Sai email hoặc mật khẩu.");
//            }
//
//            sc.close();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối CSDL:");
            e.printStackTrace();
        }

//        try {
//            AuthDAO authDAO = new AuthDAO();
//            Scanner sc = new Scanner(System.in);
//
//            System.out.println("=== TEST ĐĂNG KÝ TÀI KHOẢN ===");
//            System.out.print("Nhập username: ");
//            String username = sc.nextLine();
//
//            System.out.print("Nhập họ tên: ");
//            String fullName = sc.nextLine();
//
//            System.out.print("Nhập email: ");
//            String email = sc.nextLine();
//
//            System.out.print("Nhập số điện thoại: ");
//            String phone = sc.nextLine();
//
//            System.out.print("Nhập mật khẩu: ");
//            String password = sc.nextLine();
//
//            System.out.print("Nhập lại mật khẩu: ");
//            String confirm = sc.nextLine();
//
//            if (!password.equals(confirm)) {
//                System.out.println("Mật khẩu không khớp!");
//            } else {
//                boolean success = authDAO.register(username, fullName, email, password, phone);
//                if (success) {
//                    System.out.println("ĐĂNG KÝ THÀNH CÔNG!");
//                    System.out.println("→ Tài khoản Customer đã được tạo.");
//                } else {
//                    System.out.println("ĐĂNG KÝ THẤT BẠI! Email này đã tồn tại.");
//                }
//            }
//
//            sc.close();
//
//        } catch (SQLException e) {
//            System.out.println("Lỗi kết nối CSDL:");
//            e.printStackTrace();
//        }
    }
}
