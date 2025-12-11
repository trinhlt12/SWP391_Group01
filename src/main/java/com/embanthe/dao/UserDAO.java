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
    PreparedStatement ps = null;
    ResultSet rs = null;

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
    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE userId = ?";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs); // Dùng lại hàm mapRow cũ
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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

    // Map dữ liệu từ ResultSet sang User model mới
    private User mapRow(ResultSet rs) throws SQLException {
        return User.builder()
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
//                .updatedAt(rs.getTimestamp("updated_at"))
                .build();
    }
    // search by name or email
        public User getUserByNameOrEmail(String username, String email) throws SQLException {
            String sql = "SELECT * FROM Users WHERE username = ? and email = ?";
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

        // search by role
        public List<User> searchUsers(String keyword, String role, String status) {
            List<User> list = new ArrayList<>();

            StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (keyword != null && !keyword.trim().isEmpty()) {
                sql.append(" AND (username LIKE ? OR full_name LIKE ? OR email LIKE ?)");
                String pattern = "%" + keyword + "%";
                params.add(pattern);
                params.add(pattern);
                params.add(pattern);
            }

            if (role != null && !role.trim().isEmpty()) {
                sql.append(" AND role = ?");
                params.add(role);
            }

            if (status != null && !status.trim().isEmpty()) {
                sql.append(" AND status = ?");
                params.add(status);
            }

            try (Connection conn = DBContext.getInstance().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql.toString())) {

                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapRow(rs)); // Dùng lại hàm mapRow cũ
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }
        // update User
    public boolean updateUser(User user) {

        String sql = "UPDATE Users SET full_name=?, email=?, role=?, phone=?, status=? WHERE user_id=?";

        // ---------------------------------------------
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getRole());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getStatus());
            ps.setInt(6, user.getUserId()); // WHERE user_id = ?

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Test trực tiếp trong DAO
    public static void main(String[] args) {
        try {
            UserDAO dao = new UserDAO();

            // BƯỚC 1: Lấy user đầu tiên trong danh sách để làm chuột bạch
            List<User> users = dao.getAll();
            if (users.isEmpty()) {
                System.out.println("❌ Database đang trống, vui lòng thêm ít nhất 1 user vào SQL trước khi test.");
                return;
            }

            User targetUser = users.get(2);
            System.out.println("User được chọn để test: ID " + targetUser.getUserId() + " - " + targetUser.getFullName());

            // BƯỚC 2: Giả lập dữ liệu mới (Giống hệt lúc bạn nhập form)
            // Hãy thử thay đổi chữ "Admin" thành cái gì đó dài hơn xem có lỗi ko
            String testRole = "Admin";
            String testStatus = "Active";

            targetUser.setFullName("Test Update Name");
            targetUser.setRole(testRole);
            targetUser.setStatus(testStatus);
            targetUser.setPhone("0987654321");

            // --- DEBUG: In ra độ dài để kiểm tra lỗi Truncated ---
            System.out.println("\n--- THÔNG TIN CHUẨN BỊ UPDATE ---");
            System.out.println("Role gửi đi: [" + targetUser.getRole() + "]");
            System.out.println("Độ dài Role: " + targetUser.getRole().length()); // Nếu > độ rộng cột trong SQL là lỗi
            System.out.println("Status gửi đi: [" + targetUser.getStatus() + "]");

            // BƯỚC 3: Gọi hàm update
            System.out.println("\n>>> Đang gọi dao.updateUser()...");
            boolean result = dao.updateUser(targetUser);

            if (result) {
                System.out.println("✅ UPDATE THÀNH CÔNG! (Code Java & SQL đều ngon)");
            } else {
                System.out.println("❌ UPDATE THẤT BẠI (Nhưng không báo lỗi Exception)");
            }

        } catch (Exception e) {
            System.err.println("❌ LỖI UPDATE (Exception):");
            e.printStackTrace();
            // Nếu thấy dòng "Data truncated" ở đây -> Chắc chắn do cột trong SQL quá ngắn
        }
    }
}
