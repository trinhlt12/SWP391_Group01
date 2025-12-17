package com.embanthe.dao;

import com.embanthe.util.DBContext;
import com.embanthe.model.Products;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // Lấy danh sách sản phẩm
    public List<Products> getAll() {
        List<Products> list = new ArrayList<>();
        String sql = """
                SELECT p.product_id,
                       p.product_name,
                       p.category_id,
                       p.provider_id,
                       p.price,
                       p.quantity,
                       p.image_url
                FROM products p
                ORDER BY p.product_id DESC
                """;

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setProviderId(rs.getInt("provider_id"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setImageUrl(rs.getString("image_url"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Lấy chi tiết sản phẩm theo ID
     * Sử dụng model Products hiện tại (không có description/status/createdAt/updatedAt/categoryName/providerName)
     */
    public Products getProductById(int productId) {
        String sql = """
                SELECT p.product_id,
                       p.product_name,
                       p.category_id,
                       p.provider_id,
                       p.price,
                       p.quantity,
                       p.image_url
                FROM products p
                WHERE p.product_id = ?
                """;

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Products p = new Products();
                    p.setProductId(rs.getInt("product_id"));
                    p.setProductName(rs.getString("product_name"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setProviderId(rs.getInt("provider_id"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setImageUrl(rs.getString("image_url"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm sản phẩm
    public void insert(Products p) {
        String sql = "INSERT INTO products (product_name, category_id, provider_id, price, quantity, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setInt(3, p.getProviderId());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getImageUrl());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Products> getPagedList(String search, String sort, Integer categoryId, Integer providerId, int offset, int pageSize) {
        List<Products> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT p.product_id, p.product_name, p.category_id,
                   p.provider_id, p.price, p.quantity, p.image_url
            FROM products p
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND p.product_name LIKE ?");
            String s = "%" + search.trim() + "%";
            params.add(s);
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND p.category_id = ?");
            params.add(categoryId);
        }
        if (providerId != null && providerId > 0) {
            sql.append(" AND p.provider_id = ?");
            params.add(providerId);
        }
        if (sort != null && sort.equalsIgnoreCase("price_asc")) {
            sql.append(" ORDER BY p.price ASC ");
        } else if (sort != null && sort.equalsIgnoreCase("price_desc")) {
            sql.append(" ORDER BY p.price DESC ");
        } else {
            sql.append(" ORDER BY p.product_id DESC ");
        }
        sql.append(" LIMIT ? OFFSET ? ");

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int idx = 1;
            for (Object obj : params) {
                ps.setObject(idx++, obj);
            }
            ps.setInt(idx++, pageSize);
            ps.setInt(idx, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Products p = new Products();
                    p.setProductId(rs.getInt("product_id"));
                    p.setProductName(rs.getString("product_name"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setProviderId(rs.getInt("provider_id"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));
                    p.setImageUrl(rs.getString("image_url"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int count(String search, Integer categoryId, Integer providerId) {
        StringBuilder sql = new StringBuilder("""
            SELECT COUNT(*) FROM products p
            WHERE 1=1
            """);
        List<Object> params = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND p.product_name LIKE ?");
            String s = "%" + search.trim() + "%";
            params.add(s);
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND p.category_id = ?");
            params.add(categoryId);
        }
        if (providerId != null && providerId > 0) {
            sql.append(" AND p.provider_id = ?");
            params.add(providerId);
        }
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {
            int idx = 1;
            for (Object obj : params) ps.setObject(idx++, obj);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public Products getById(int id) {
        // Alias of getProductById kept for compatibility
        return getProductById(id);
    }

    public void update(Products p) {
        String sql = "UPDATE products SET product_name=?, category_id=?, provider_id=?, price=?, quantity=?, image_url=? WHERE product_id=?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setInt(3, p.getProviderId());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getImageUrl());
            ps.setInt(7, p.getProductId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delete(int productId) {
        String checkSql = "SELECT COUNT(*) FROM card_items WHERE product_id = ?";
        String deleteSql = "DELETE FROM products WHERE product_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement psCheck = con.prepareStatement(checkSql)) {
            // Kiểm tra xem còn card_items tham chiếu không
            psCheck.setInt(1, productId);
            try (ResultSet rs = psCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Có card items tham chiếu -> không xóa
                    return false;
                }
            }

            // Không có bản ghi con -> tiến hành xóa product
            try (PreparedStatement psDel = con.prepareStatement(deleteSql)) {
                psDel.setInt(1, productId);
                return psDel.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Điều chỉnh quantity của product trong cùng Connection/transaction.
     * Trả về true nếu cập nhật thành công, false nếu product không tồn tại hoặc quantity sau khi tăng < 0.
     */
    public boolean adjustQuantityWithCheck(Connection con, int productId, int delta) throws SQLException {
        // 1) Lock hàng product để tránh race (SELECT ... FOR UPDATE)
        String selectSql = "SELECT quantity FROM products WHERE product_id = ? FOR UPDATE";
        try (PreparedStatement ps = con.prepareStatement(selectSql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return false; // product not found
                }
                int curr = rs.getInt("quantity");
                int updated = curr + delta;
                if (updated < 0) {
                    return false; // không cho quantity âm
                }
                String updateSql = "UPDATE products SET quantity = ? WHERE product_id = ?";
                try (PreparedStatement ups = con.prepareStatement(updateSql)) {
                    ups.setInt(1, updated);
                    ups.setInt(2, productId);
                    return ups.executeUpdate() > 0;
                }
            }
        }
    }

    /**
     * Convenience wrapper that opens its own connection.
     */
    public boolean adjustQuantity(int productId, int delta) {
        try (Connection con = DBContext.getInstance().getConnection()) {
            con.setAutoCommit(false);
            boolean ok = adjustQuantityWithCheck(con, productId, delta);
            if (ok) con.commit(); else con.rollback();
            return ok;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Đếm số thẻ (card_items) đang tham chiếu tới product này.
     */
    public int countCardItems(int productId) {
        String sql = "SELECT COUNT(*) FROM card_items WHERE product_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Đếm tổng số orders tham chiếu tới product.
     */
    public int countOrders(int productId) {
        String sql = "SELECT COUNT(*) FROM orders WHERE product_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /**
     * Đếm orders theo status (ví dụ 'COMPLETED', 'PENDING', 'CANCELLED').
     */
    public int countOrdersByStatus(int productId, String status) {
        String sql = "SELECT COUNT(*) FROM orders WHERE product_id = ? AND status = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int countCardItemsByStatus(int productId, String status) {
        String sql = "SELECT COUNT(*) FROM card_items WHERE product_id = ? AND status = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    /**
     * Trả về tên category theo id, hoặc "-" nếu không tìm thấy / lỗi.
     */
    public String getCategoryNameById(int categoryId) {
        String sql = "SELECT category_name FROM categories WHERE category_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("category_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "-";
    }

    /**
     * Trả về tên provider theo id, hoặc "-" nếu không tìm thấy / lỗi.
     */
    public String getProviderNameById(int providerId) {
        String sql = "SELECT provider_name FROM providers WHERE provider_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, providerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("provider_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "-";
    }

    /**
     * (Option B) Kiểm tra có sản phẩm nào đang dùng categoryId không.
     * Trả về true nếu tồn tại ít nhất 1 product tham chiếu category này.
     */
    public boolean existsProductByCategoryId(int categoryId) {
        String sql = "SELECT 1 FROM products WHERE category_id = ? LIMIT 1";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * (Option B) Kiểm tra có sản phẩm nào đang dùng providerId không.
     * Trả về true nếu tồn tại ít nhất 1 product tham chiếu provider này.
     */
    public boolean existsProductByProviderId(int providerId) {
        String sql = "SELECT 1 FROM products WHERE provider_id = ? LIMIT 1";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, providerId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}