package com.embanthe.dao;

import com.embanthe.model.Users;
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
    public Users getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    //DEPOSIT:
    public boolean updateBalance(int userId, double amountToAdd) {
        String sql = "UPDATE users SET balance = balance + ? WHERE user_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amountToAdd);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
    public String checkStatus(String username) {
        String sql = "SELECT status FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status"); // trả về ACTIVE, INACTIVE...
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // không tìm thấy user
    }
    // Cập nhật thông tin cơ bản của User theo user_id
    public boolean updateUser(Users user) throws SQLException {
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

    public boolean updateUserInfo(int userId, String fullName, String email, String phone, String role, String status) {
        String sql = "UPDATE Users SET full_name=?, email=?, phone=?, role=?, status=? WHERE user_id=?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, role);
            ps.setString(5, status);
            ps.setInt(6, userId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // search by role
    public List<Users> searchUsers(String keyword, String role, String status, String fromDate, String toDate, String sort) {
        List<Users> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        // -- Filter Keyword --
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (username LIKE ? OR full_name LIKE ? OR email LIKE ?) ");
            String key = "%" + keyword.trim() + "%";
            params.add(key); params.add(key); params.add(key);
        }
        // -- Filter Role --
        if (role != null && !role.isEmpty()) {
            sql.append("AND role = ? ");
            params.add(role);
        }
        // -- Filter Status --
        if (status != null && !status.isEmpty()) {
            sql.append("AND status = ? ");
            params.add(status);
        }
        // -- Filter Date Range (Mới thêm) --
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND created_at >= ? ");
            params.add(fromDate + " 00:00:00");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND created_at <= ? ");
            params.add(toDate + " 23:59:59");
        }

        // -- Sorting (Mới thêm) --
        if ("name_asc".equals(sort)) {
            sql.append("ORDER BY full_name ASC ");
        } else if ("oldest".equals(sort)) {
            sql.append("ORDER BY created_at ASC ");
        } else {
            sql.append("ORDER BY created_at DESC "); // Mặc định mới nhất
        }

        try (Connection conn =  DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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
    public void insertGoogleUser(Users user) throws SQLException {
        String sql = "INSERT INTO users (username, email, full_name, password_hash, role, balance, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullName());
            ps.setString(4, ""); // đánh dấu đây là user Google (không có password thật)
            ps.setString(5, user.getRole() != null ? user.getRole() : "CUSTOMER");
            ps.setDouble(6, user.getBalance());
            ps.setString(7, user.getStatus() != null ? user.getStatus() : "ACTIVE");

            ps.executeUpdate();
        }
    }
    // Map dữ liệu từ ResultSet sang User model mới
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
