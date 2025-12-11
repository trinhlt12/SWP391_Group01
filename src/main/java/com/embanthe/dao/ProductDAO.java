package com.embanthe.dao;

import com.embanthe.model.Products;
import com.embanthe.util.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Products.
 * Giả sử table tên là Products với các cột:
 * product_id, provider_id, category_id, product_name, price, denomination, image_url, created_at
 */
public class ProductDAO {
    private final Connection connection;

    public ProductDAO() throws SQLException {
        this.connection = DBContext.getInstance().getConnection();
    }

    /**
     * Đếm tổng số sản phẩm theo điều kiện tìm kiếm / category.
     */
    public int countProducts(String q, Long categoryId) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (q != null && !q.trim().isEmpty()) {
            sql.append(" AND product_name LIKE ?");
            params.add("%" + q.trim() + "%");
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    /**
     * Lấy danh sách sản phẩm có pagination, tìm kiếm và sort cơ bản.
     *
     * @param page  1-based page index
     * @param size  số item / page
     * @param q     từ khoá tìm kiếm tên
     * @param categoryId category id (nullable)
     * @param sort  kiểu sort: newest, name_asc, name_desc, price_asc, price_desc
     */
    public List<Products> listProducts(int page, int size, String q, Long categoryId, String sort) throws SQLException {
        int offset = (page - 1) * size;
        StringBuilder sql = new StringBuilder("SELECT * FROM Products WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (q != null && !q.trim().isEmpty()) {
            sql.append(" AND product_name LIKE ?");
            params.add("%" + q.trim() + "%");
        }
        if (categoryId != null && categoryId > 0) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);
        }

        // sort
        if ("price_asc".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY price ASC");
        } else if ("price_desc".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY price DESC");
        } else if ("name_asc".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY product_name ASC");
        } else if ("name_desc".equalsIgnoreCase(sort)) {
            sql.append(" ORDER BY product_name DESC");
        } else { // default newest
            sql.append(" ORDER BY created_at DESC");
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        List<Products> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        }
        return list;
    }

    private Products mapRow(ResultSet rs) throws SQLException {
        Products p = new Products();
        p.setProductId(rs.getLong("product_id"));
        p.setProviderId(rs.getLong("provider_id"));
        p.setCategoryId(rs.getLong("category_id"));
        p.setProductName(rs.getString("product_name"));
        p.setPrice(rs.getDouble("price"));
        p.setDenomination(rs.getDouble("denomination"));
        p.setImageUrl(rs.getString("image_url"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}