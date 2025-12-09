package com.embanthe.DAO;

import com.embanthe.model.User;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();
            List<User> users = dao.getAll();
            System.out.println("✅ Found " + users.size() + " users in DB:");
            for (User u : users) {
                System.out.println(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
