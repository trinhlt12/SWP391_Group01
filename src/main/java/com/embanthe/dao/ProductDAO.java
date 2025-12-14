package com.embanthe.dao;

import com.embanthe.model.Products;
import com.embanthe.util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {

    /**
     * Thêm sản phẩm mới
     */
    public void insert(Products p) {
        String sql = """
            INSERT INTO products
            (provider_id, category_id, product_name, price, quantity, image_url)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection con = DBContext.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getProviderId());
            ps.setInt(2, p.getCategoryId());
            ps.setString(3, p.getProductName());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getQuantity());
            ps.setString(6, p.getImageUrl());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
