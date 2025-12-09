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

    /**
     * Login: kiểm tra email và password
     * @param email email người dùng nhập
     * @param password mật khẩu plaintext người dùng nhập
     * @return User nếu đúng, null nếu sai
     */
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
        try {
            AuthDAO authDAO = new AuthDAO();
            Scanner sc = new Scanner(System.in);

            System.out.print("Nhập email: ");
            String email = sc.nextLine();

            System.out.print("Nhập mật khẩu: ");
            String password = sc.nextLine();

            User user = authDAO.login(email, password);
            if (user != null) {
                System.out.println("✅ Login thành công: " + user.getFullName());
            } else {
                System.out.println("❌ Login thất bại!");
            }

            sc.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
