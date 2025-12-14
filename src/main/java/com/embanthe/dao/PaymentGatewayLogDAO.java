package com.embanthe.dao;

import com.embanthe.model.PaymentGatewayLogs;
import com.embanthe.util.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentGatewayLogDAO {

    public void save(PaymentGatewayLogs log) {
        String sql = "INSERT INTO payment_gateway_logs (transaction_id, gateway_name, request_code, response_code, bank_code, raw_data, created_at) VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DBContext.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, log.getTransactionId());
            ps.setString(2, log.getGatewayName());  // "VNPAY"
            ps.setString(3, log.getRequestCode());  // URL
            ps.setString(4, log.getResponseCode()); // vnp_ResponseCode
            ps.setString(5, log.getBankCode());     // vnp_BankCode
            ps.setString(6, log.getRawData());      // Full query string

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Không thể lưu log gateway: " + e.getMessage());
        }
    }
}