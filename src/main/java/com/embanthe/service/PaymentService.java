package com.embanthe.service;

import com.embanthe.util.VNPayConfig;
import com.embanthe.util.VNPayUtils;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentService {

    public String createDepositUrl(int userId, long amount, String ipAddr, String transactionRef) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        long vnp_Amount = amount * 100;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_TxnRef", transactionRef);
        vnp_Params.put("vnp_OrderInfo", "Nap tien vao vi user:" + userId);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", ipAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        String queryUrl = VNPayUtils.buildQueryUrl(vnp_Params, VNPayConfig.vnp_HashSecret);

        return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
    }

    public int processPaymentReturn(Map<String, String[]> requestParams) {
        try {
            Map<String, String> fields = new HashMap<>();
            for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
                String fieldName = URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(entry.getValue()[0], StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = fields.get("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            fields.remove("vnp_SecureHash");

            String signValue = VNPayUtils.hashAllFields(fields, VNPayConfig.vnp_HashSecret);

            if (signValue.equals(vnp_SecureHash)) {
                String vnp_ResponseCode = fields.get("vnp_ResponseCode");
                String transactionId = fields.get("vnp_TxnRef");

                if ("00".equals(vnp_ResponseCode)) {
                    return 1;
                }else{
                    return 0;
                }
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}