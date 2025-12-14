package com.embanthe.dao;

import com.embanthe.model.Products;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
                       c.category_name,
                       p.provider_id,
                       pr.provider_name,
                       p.price,
                       p.quantity,
                       p.image_url
                FROM products p
                JOIN categories c ON p.category_id = c.category_id
                JOIN providers pr ON p.provider_id = pr.provider_id
                ORDER BY p.product_id DESC
                """;

        try {
            Connection con = DBContext.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("category_name"));
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setImageUrl(rs.getString("image_url"));
                list.add(p);
            }
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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
        SELECT p.product_id, p.product_name, p.category_id, c.category_name,
               p.provider_id, pr.provider_name, p.price, p.quantity, p.image_url
        FROM products p
        JOIN categories c ON p.category_id = c.category_id
        JOIN providers pr ON p.provider_id = pr.provider_id
        WHERE 1=1
    """);
        List<Object> params = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (p.product_name LIKE ? OR pr.provider_name LIKE ? OR c.category_name LIKE ?)");
            String s = "%" + search.trim() + "%";
            params.add(s); params.add(s); params.add(s);
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

        try {
            Connection con = DBContext.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql.toString());
            int idx = 1;
            for (Object obj : params) ps.setObject(idx++, obj);
            ps.setInt(idx++, pageSize);
            ps.setInt(idx, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("category_name"));
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setImageUrl(rs.getString("image_url"));
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public int count(String search, Integer categoryId, Integer providerId) {
        StringBuilder sql = new StringBuilder("""
        SELECT COUNT(*) FROM products p
        JOIN categories c ON p.category_id = c.category_id
        JOIN providers pr ON p.provider_id = pr.provider_id
        WHERE 1=1
    """);
        List<Object> params = new ArrayList<>();
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (p.product_name LIKE ? OR pr.provider_name LIKE ? OR c.category_name LIKE ?)");
            String s = "%" + search.trim() + "%";
            params.add(s); params.add(s); params.add(s);
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND p.category_id = ?");
            params.add(categoryId);
        }
        if (providerId != null && providerId > 0) {
            sql.append(" AND p.provider_id = ?");
            params.add(providerId);
        }
        try {
            Connection con = DBContext.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql.toString());
            int idx = 1;
            for (Object obj : params) ps.setObject(idx++, obj);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // ... (các hàm trước đó)

    public Products getById(int id) {
        String sql = """
        SELECT p.product_id, p.product_name, p.category_id, c.category_name,
               p.provider_id, pr.provider_name, p.price, p.quantity, p.image_url
        FROM products p
        JOIN categories c ON p.category_id = c.category_id
        JOIN providers pr ON p.provider_id = pr.provider_id
        WHERE p.product_id = ?
        """;
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("category_name"));
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setImageUrl(rs.getString("image_url"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Products p) {
        String sql = "UPDATE products SET product_name=?, category_id=?, provider_id=?, price=?, image_url=? WHERE product_id=?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getProductName());
            ps.setInt(2, p.getCategoryId());
            ps.setInt(3, p.getProviderId());
            ps.setDouble(4, p.getPrice());
            ps.setString(5, p.getImageUrl());
            ps.setInt(6, p.getProductId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE product_id=?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Tăng quantity sản phẩm lên delta (thường là +1 khi thêm thẻ)
    public void incrementQuantity(int productId, int delta) {
        String sql = "UPDATE products SET quantity = quantity + ? WHERE product_id = ?";
        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, delta);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}