package com.embanthe.dao;

import com.embanthe.model.Users;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 1. Lấy tất cả User
    public List<Users> getAll() {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        // Sử dụng try-with-resources để tự động đóng kết nối
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 2. Lấy User theo ID
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

    // 3. Nạp tiền (Deposit)
    public boolean updateBalance(int userId, double amountToAdd) {
        String sql = "UPDATE Users SET balance = balance + ? WHERE user_id = ?";
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

    // Hàm này nhận Connection từ bên ngoài để thực hiện Transaction
    public void updateBalance(Connection conn, int userId, double newBalance) throws SQLException {
        String sql = "UPDATE Users SET balance = ? WHERE user_id = ?";
        // Lưu ý: Không dùng try-with-resources cho 'conn' vì nó được quản lý ở bên ngoài (Service)
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    public double getBalanceForUpdate(Connection conn, int userId) throws SQLException {
        String sql ="SELECT balance FROM Users WHERE user_id = ? FOR UPDATE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        }
        return 0.0;
    }

    // 4. Lấy User theo Email
    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
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

    // 5. Kiểm tra email đã tồn tại chưa
    public boolean checkEmailExists(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Kiểm tra username đã tồn tại chưa
    public boolean checkUsernameExists(String username) {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. Lấy status (SỬA LỖI Connection ở đây)
    public String checkStatus(String username) {
        String sql = "SELECT status FROM Users WHERE username = ?";
        // Lỗi cũ: dùng biến 'connection' chưa khai báo
        // Sửa: Gọi DBContext.getInstance().getConnection()
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 8. Cập nhật thông tin User (Gộp updateUser và updateUserInfo làm một)
    public boolean updateUser(Users user) {
        String sql = "UPDATE Users SET full_name = ?, email = ?, phone = ?, role = ?, status = ? WHERE user_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getUserId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 9. Search & Filter
    public List<Users> searchUsers(String keyword, String role, String status, String fromDate, String toDate, String sort) {
        List<Users> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (username LIKE ? OR full_name LIKE ? OR email LIKE ?) ");
            String key = "%" + keyword.trim() + "%";
            params.add(key);
            params.add(key);
            params.add(key);
        }
        if (role != null && !role.isEmpty()) {
            sql.append("AND role = ? ");
            params.add(role);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND status = ? ");
            params.add(status);
        }
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append("AND created_at >= ? ");
            params.add(fromDate + " 00:00:00");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append("AND created_at <= ? ");
            params.add(toDate + " 23:59:59");
        }

        if ("name_asc".equals(sort)) {
            sql.append("ORDER BY full_name ASC ");
        } else if ("oldest".equals(sort)) {
            sql.append("ORDER BY created_at ASC ");
        } else {
            sql.append("ORDER BY created_at DESC ");
        }

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 10. Đổi mật khẩu
    public boolean changePassword(int userId, String hashedPassword) {
        String sql = "UPDATE Users SET password_hash = ? WHERE user_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 11. Insert Google User
    public void insertGoogleUser(Users user) {
        String sql = "INSERT INTO Users (username, email, full_name, password_hash, role, balance, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFullName());
            ps.setString(4, "");
            ps.setString(5, user.getRole() != null ? user.getRole() : "CUSTOMER");
            ps.setDouble(6, user.getBalance());
            ps.setString(7, user.getStatus() != null ? user.getStatus() : "ACTIVE");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 12. Insert User thường (Đăng ký)
    public boolean insertUser(Users user) {
        String sql = "INSERT INTO Users (username, password_hash, full_name, email, phone, role, status, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getFullName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 13. Lấy Username theo ID
    public String getUsernameById(int userId) {
        String sql = "SELECT username FROM Users WHERE user_id = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Users> searchUsersPaging(String keyword, String role, String status, int page, int pageSize) {
        List<Users> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        // Logic Filter
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (username LIKE ? OR full_name LIKE ? OR email LIKE ?) ");
            String key = "%" + keyword.trim() + "%";
            params.add(key);
            params.add(key);
            params.add(key);
        }
        if (role != null && !role.isEmpty()) {
            sql.append("AND role = ? ");
            params.add(role);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND status = ? ");
            params.add(status);
        }

        // Thêm sắp xếp và Phân trang
        sql.append(" ORDER BY created_at ASC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try (Connection conn = DBContext.getInstance().getConnection();
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
    public int countUsers(String keyword, String role, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Users WHERE 1=1 ");
        List<Object> params = new ArrayList<>();

        // Logic Filter (Giống hệt hàm search bên dưới)
        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append("AND (username LIKE ? OR full_name LIKE ? OR email LIKE ?) ");
            String key = "%" + keyword.trim() + "%";
            params.add(key); params.add(key); params.add(key);
        }
        if (role != null && !role.isEmpty()) {
            sql.append("AND role = ? ");
            params.add(role);
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND status = ? ");
            params.add(status);
        }

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int getTotalUserCount() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Helper: Map ResultSet to Users Object
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
}