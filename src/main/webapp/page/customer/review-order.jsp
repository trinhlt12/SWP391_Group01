<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận đơn hàng</title>
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">

    <style>
        body { background-color: #f3f4f6; font-family: 'Inter', sans-serif; }
        .review-container { max-width: 900px; margin: 40px auto; display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
        .card-box { background: white; border-radius: 12px; padding: 25px; box-shadow: 0 2px 10px rgba(0,0,0,0.05); }
        .section-title { font-weight: 700; margin-bottom: 20px; font-size: 1.1rem; }

        /* Info Row */
        .info-row { display: flex; justify-content: space-between; margin-bottom: 15px; border-bottom: 1px solid #eee; padding-bottom: 10px; }
        .info-label { color: #6b7280; font-size: 0.95rem; }
        .info-value { font-weight: 600; color: #111827; }
        .total-price { color: #10B981; font-size: 1.2rem; font-weight: 700; }

        /* Payment Form */
        .payment-input { width: 100%; padding: 12px; border: 1px solid #d1d5db; border-radius: 8px; margin-bottom: 20px; }
        .btn-confirm { width: 100%; padding: 12px; background: #10B981; color: white; border: none; border-radius: 8px; font-weight: 600; font-size: 1.1rem; transition: 0.3s; }
        .btn-confirm:hover { background: #10B981; }
    </style>
</head>
<body>


<div class="container">
    <jsp:include page="/header.jsp"/>

    <div class="review-container">

        <!-- CỘT TRÁI: THÔNG TIN ĐƠN HÀNG -->
        <div class="card-box">
            <div class="section-title">Thông tin đơn hàng</div>

            <div class="info-row">
                <span class="info-label">Sản phẩm</span>
                <span class="info-value">${product.productName}</span>
            </div>

            <div class="info-row">
                <span class="info-label">Đơn giá</span>
                <span class="info-value">
                    <fmt:formatNumber value="${product.price}" pattern="#,###"/>đ
                </span>
            </div>

            <div class="info-row">
                <span class="info-label">Số lượng</span>
                <span class="info-value">${quantity}</span>
            </div>

            <!-- Tính năng discount để sau -->
            <div class="info-row">
                <span class="info-label">Chiết khấu</span>
                <span class="info-value">0%</span>
            </div>

            <div class="info-row" style="border: none;">
                <span class="info-label">Thanh toán</span>
                <span class="info-value total-price">
                    <fmt:formatNumber value="${totalAmount}" pattern="#,###"/>đ
                </span>
            </div>

            <div class="info-row" style="border: none; margin-top: 10px;">
                <span class="info-label">Hình thức</span>
                <span class="info-value">Hiển thị mã thẻ</span>
            </div>
        </div>

        <!-- CỘT PHẢI: XÁC THỰC THANH TOÁN -->
        <div class="card-box">
            <div class="section-title">Xác thực giao dịch</div>

            <!-- FORM GỬI SANG PURCHASE SERVLET -->
            <form action="${pageContext.request.contextPath}/purchase" method="POST">

                <!-- QUAN TRỌNG: Truyền lại ID và Quantity cho bước xử lý tiếp theo -->
                <input type="hidden" name="productId" value="${product.productId}">
                <input type="hidden" name="quantity" value="${quantity}">

                <button type="submit" class="btn-confirm">
                    Thanh toán
                </button>

                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/service" style="color: #6b7280; text-decoration: none;">Hủy bỏ</a>
                </div>
            </form>
        </div>

    </div>
</div>

</body>
</html>