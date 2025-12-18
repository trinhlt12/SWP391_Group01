<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Giao Dịch #${trans.transactionId}</title>
    <base href="${pageContext.request.contextPath}/">
    <!-- Import CSS giống trang Ewallet -->
    <link href="assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f3f4f6; padding: 40px 0; font-family: 'Inter', sans-serif; }
        .invoice-card {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.05);
            overflow: hidden;
        }
        .invoice-header {
            background: #10B981;
            color: white;
            padding: 30px;
            text-align: center;
        }
        .invoice-body { padding: 30px; }
        .info-row {
            display: flex;
            justify-content: space-between;
            padding: 12px 0;
            border-bottom: 1px dashed #e5e7eb;
        }
        .info-row:last-child { border-bottom: none; }
        .label { color: #6b7280; font-weight: 500; }
        .value { color: #1f2937; font-weight: 600; }
        .amount-large {
            font-size: 2rem;
            font-weight: 700;
            margin: 10px 0;
        }
        .status-badge {
            display: inline-block;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.9rem;
            background: rgba(255,255,255,0.2);
        }
        .btn-back {
            display: block;
            width: 100%;
            padding: 15px;
            text-align: center;
            background: #f9fafb;
            color: #4b5563;
            text-decoration: none;
            font-weight: 600;
            border-top: 1px solid #e5e7eb;
            transition: all 0.3s;
        }
        .btn-back:hover { background: #e5e7eb; color: #1f2937; }
    </style>
</head>
<body>

<div class="container">
    <div class="invoice-card">
        <!-- Header -->
        <div class="invoice-header" style="background: ${trans.status == 'SUCCESS' ? '#10B981' : (trans.status == 'PENDING' ? '#F59E0B' : '#EF4444')}">
            <div class="status-badge">
                ${trans.status == 'SUCCESS' ? 'Giao dịch Thành công' : (trans.status == 'PENDING' ? 'Đang chờ xử lý' : 'Giao dịch Thất bại')}
            </div>
            <div class="amount-large">
                <fmt:formatNumber value="${trans.amount}" type="currency" currencySymbol="₫"/>
            </div>
            <div>${trans.createdAt}</div>
        </div>

        <!-- Body -->
        <div class="invoice-body">
            <div class="info-row">
                <span class="label">Mã giao dịch</span>
                <span class="value">#${trans.transactionId}</span>
            </div>
            <div class="info-row">
                <span class="label">Loại giao dịch</span>
                <span class="value">${trans.type == 'DEPOSIT' ? 'Nạp tiền vào ví' : 'Mua thẻ điện thoại'}</span>
            </div>

            <!-- Phần này nếu bạn lưu các trường vnp_ trong bảng Logs, cần join bảng để lấy -->
            <!-- Ở đây mình hiển thị message làm mô tả -->
            <div class="info-row">
                <span class="label">Nội dung</span>
                <span class="value">${trans.message}</span>
            </div>

            <!-- Nếu là nạp tiền, có thể hiển thị thêm thông tin (giả lập từ logic) -->
            <c:if test="${trans.type == 'DEPOSIT'}">
                <div class="info-row">
                    <span class="label">Cổng thanh toán</span>
                    <span class="value">VNPay</span>
                </div>
            </c:if>

            <!-- Nếu là mua hàng, hiển thị mã đơn hàng -->
            <c:if test="${trans.orderId > 0}">
                <div class="info-row">
                    <span class="label">Mã đơn hàng</span>
                    <span class="value">#${trans.orderId}</span>
                </div>
            </c:if>
        </div>

        <!-- Footer Button -->
        <a href="ewallet" class="btn-back">
            ← Quay lại Ví của tôi
        </a>
    </div>
</div>

</body>
</html>