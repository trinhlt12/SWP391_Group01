    package com.embanthe.dao;

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

        PreparedStatement ps = null;
        ResultSet rs = null;

    //    public UserDAO() throws SQLException {
    //        this.connection = DBContext.getInstance().getConnection();
    //    }

        // Lấy tất cả User
        public List<Users> getAll() throws SQLException {
            List<Users> users = new ArrayList<>();
            String sql = "SELECT * FROM Users";
            try (Connection connection = DBContext.getInstance().getConnection();
                 Statement st = connection.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    users.add(mapRow(rs));
                }
            }
            return users;
        }
        public Users getUserById(int id) {
            String sql = "SELECT * FROM Users WHERE user_id = ?";
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
        public Users getUserByEmail(String email) throws SQLException {
            String sql = "SELECT * FROM Users WHERE email = ?";
            try (Connection connection = DBContext.getInstance().getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql)) {
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
            try (Connection connection = DBContext.getInstance().getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)) {
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
    //                .updatedAt(rs.getTimestamp("updated_at"))
                    .build();
        }
        // search by name or email
            public Users getUserByNameOrEmail(String username, String email) throws SQLException {
                String sql = "SELECT * FROM Users WHERE username = ? and email = ?";
                try ( Connection connection = DBContext.getInstance().getConnection();
                        PreparedStatement ps = connection.prepareStatement(sql)) {
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
            // update User
        public boolean updateUser(Users user) {

            String sql = "UPDATE Users SET full_name=?, email=?, role=?, phone=?, status=? WHERE user_id=?";

            // ---------------------------------------------
            try (Connection conn = DBContext.getInstance().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, user.getFullName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getRole());
                ps.setString(4, user.getPhone());
                ps.setString(5, user.getStatus());
                ps.setInt(6, user.getUserId());

                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        // get password
        public boolean updatePassword(int userId, String newPasswordHash) {
            String sql = "UPDATE Users SET password_hash = ? WHERE user_id = ?";
            try (Connection conn =  DBContext.getInstance().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newPasswordHash);
                ps.setInt(2, userId);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
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

        // Test trực tiếp trong DAO
        public static void main(String[] args) {

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
    }
