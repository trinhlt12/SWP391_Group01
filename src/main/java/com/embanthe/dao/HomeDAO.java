package com.embanthe.dao;

import com.embanthe.util.DBContext;
import com.embanthe.model.Categories;
import com.embanthe.model.Providers;
import com.embanthe.model.Products; // Giả sử bạn đã có class này

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HomeDAO {

    // 1. Lấy danh sách Danh mục (Categories)
    public List<Categories> getAllCategories() {
        List<Categories> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categories c = new Categories();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                c.setDescription(rs.getString("description"));
                list.add(c);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public List<Providers> getAllProviders() {
        List<Providers> list = new ArrayList<>();
        String sql = "SELECT * FROM providers";
        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Providers p = new Providers();
                p.setProviderId(rs.getInt("provider_id"));
                p.setProviderName(rs.getString("provider_name"));
                p.setLogoUrl(rs.getString("logo_url"));
                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Trong file HomeDAO.java

    public List<Products> getNewestProducts() {
        List<Products> list = new ArrayList<>();

        // SỬA: Đổi 'ORDER BY p.created_at' thành 'ORDER BY p.product_id'
        String sql = "SELECT p.product_id, p.product_name, p.price, p.quantity, p.image_url,\n" +
                "       c.category_name, pr.provider_name\n" +
                "FROM products p\n" +
                "JOIN categories c ON p.category_id = c.category_id\n" +
                "JOIN providers pr ON p.provider_id = pr.provider_id\n" +
                "ORDER BY p.product_id DESC\n" +
                "LIMIT 6\n";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Products p = new Products();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setImageUrl(rs.getString("image_url"));

                // Set các trường mở rộng
                p.setCategoryName(rs.getString("category_name"));
                p.setProviderName(rs.getString("provider_name"));

                list.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}