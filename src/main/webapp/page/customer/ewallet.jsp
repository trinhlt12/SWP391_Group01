<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ví Điện Tử - Em Bán Thẻ</title>
    <base href="${pageContext.request.contextPath}/">

    <!-- Fonts -->
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&family=Raleway:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
          rel="stylesheet">

    <!-- CSS Files (Copy từ home.jsp) -->
    <link href="assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="assetsHome/css/main.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #f0fdf4 0%, #dcfce7 100%);
            min-height: 100vh;
            padding: 20px;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Header Section */
        .page-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .page-header h1 {
            color: #059669;
            font-size: 2.5rem;
            margin-bottom: 10px;
        }

        .page-header p {
            color: #6b7280;
            font-size: 1rem;
        }

        /* Balance Card */
        .balance-card {
            background: linear-gradient(135deg, #10B981 0%, #059669 100%);
            border-radius: 20px;
            padding: 40px;
            color: white;
            box-shadow: 0 10px 30px rgba(16, 185, 129, 0.3);
            margin-bottom: 30px;
            position: relative;
            overflow: hidden;
        }

        .balance-card::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -10%;
            width: 400px;
            height: 400px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 50%;
        }

        .balance-label {
            font-size: 1rem;
            opacity: 0.9;
            margin-bottom: 10px;
        }

        .balance-amount {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 20px;
            position: relative;
            z-index: 1;
        }

        .wallet-id {
            font-size: 0.9rem;
            opacity: 0.8;
        }

        /* Quick Actions */
        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .action-btn {
            background: white;
            border: 2px solid #10B981;
            border-radius: 15px;
            padding: 25px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            color: #059669;
            display: block;
        }

        .action-btn:hover {
            background: #10B981;
            color: white;
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(16, 185, 129, 0.3);
        }

        .action-btn .icon {
            font-size: 2rem;
            margin-bottom: 10px;
        }

        .action-btn .label {
            font-weight: 600;
            font-size: 1.1rem;
        }

        /* Transaction Section */
        .transaction-section {
            background: white;
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(0, 0, 0, 0.05);
        }

        .section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            flex-wrap: wrap;
            gap: 15px;
        }

        .section-header h2 {
            color: #059669;
            font-size: 1.8rem;
        }

        /* Filter */
        .filter-group {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .filter-btn {
            padding: 10px 20px;
            border: 2px solid #e5e7eb;
            background: white;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.9rem;
        }

        .filter-btn:hover,
        .filter-btn.active {
            background: #10B981;
            color: white;
            border-color: #10B981;
        }

        /* Transaction List */
        .transaction-list {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .transaction-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            border: 1px solid #e5e7eb;
            border-radius: 15px;
            transition: all 0.3s ease;
        }

        .transaction-item:hover {
            border-color: #10B981;
            box-shadow: 0 5px 15px rgba(16, 185, 129, 0.1);
        }

        .transaction-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .transaction-icon {
            width: 50px;
            height: 50px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 1.5rem;
        }

        .transaction-icon.deposit {
            background: #d1fae5;
            color: #059669;
        }

        .transaction-icon.withdraw {
            background: #fee2e2;
            color: #dc2626;
        }

        .transaction-icon.purchase {
            background: #dbeafe;
            color: #2563eb;
        }

        .transaction-details h4 {
            color: #1f2937;
            margin-bottom: 5px;
            font-size: 1rem;
        }

        .transaction-date {
            color: #6b7280;
            font-size: 0.85rem;
        }

        .transaction-amount {
            text-align: right;
        }

        .amount-value {
            font-size: 1.2rem;
            font-weight: 600;
            margin-bottom: 5px;
        }

        .amount-value.positive {
            color: #059669;
        }

        .amount-value.negative {
            color: #dc2626;
        }

        .transaction-status {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 600;
        }

        .status-success {
            background: #d1fae5;
            color: #059669;
        }

        .status-pending {
            background: #fef3c7;
            color: #d97706;
        }

        .status-failed {
            background: #fee2e2;
            color: #dc2626;
        }

        /* Empty State */
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #6b7280;
        }

        .empty-state .icon {
            font-size: 4rem;
            margin-bottom: 20px;
            opacity: 0.3;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .balance-amount {
                font-size: 2rem;
            }

            .page-header h1 {
                font-size: 1.8rem;
            }

            .transaction-item {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }

            .transaction-amount {
                text-align: left;
                width: 100%;
            }
        }
    </style>
</head>
<body>
<%
    // Format tiền VN
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    // Giả lập data tạm (sau này sẽ lấy từ database)
    double balance = 5250000; // 5.25 triệu
    String walletId = "EBT" + System.currentTimeMillis();
%>

<div class="container">
    <jsp:include page="/header.jsp" />
    <!-- Page Header -->
    <div class="page-header">
        <h1>💳 Ví Điện Tử</h1>
        <p>Quản lý tài chính của bạn một cách dễ dàng</p>
    </div>

    <!-- Balance Card -->
    <div class="balance-card">
        <div class="balance-label">Số dư hiện tại</div>
        <div class="balance-amount">
            <%= currencyFormat.format(balance) %>
        </div>
        <div class="wallet-id">ID Ví: <strong><%= walletId %>
        </strong></div>
    </div>

    <!-- Quick Actions -->
    <div class="quick-actions">
        <a href="<%= request.getContextPath() %>/deposit" class="action-btn">
            <div class="icon">💰</div>
            <div class="label">Nạp Tiền</div>
        </a>
        <a href="<%= request.getContextPath() %>/withdraw" class="action-btn">
            <div class="icon">💸</div>
            <div class="label">Rút Tiền</div>
        </a>
        <a href="<%= request.getContextPath() %>/transfer" class="action-btn">
            <div class="icon">🔄</div>
            <div class="label">Chuyển Tiền</div>
        </a>
        <a href="<%= request.getContextPath() %>/history" class="action-btn">
            <div class="icon">📊</div>
            <div class="label">Lịch Sử</div>
        </a>
    </div>

    <!-- Transaction History -->
    <div class="transaction-section">
        <div class="section-header">
            <h2>Giao Dịch Gần Đây</h2>
            <div class="filter-group">
                <button class="filter-btn active" onclick="filterTransactions('all')">Tất cả</button>
                <button class="filter-btn" onclick="filterTransactions('deposit')">Nạp tiền</button>
                <button class="filter-btn" onclick="filterTransactions('withdraw')">Rút tiền</button>
                <button class="filter-btn" onclick="filterTransactions('purchase')">Mua hàng</button>
            </div>
        </div>

        <div class="transaction-list">
            <!-- Sample Transaction 1: Nạp tiền -->
            <div class="transaction-item" data-type="deposit">
                <div class="transaction-info">
                    <div class="transaction-icon deposit">⬇️</div>
                    <div class="transaction-details">
                        <h4>Nạp tiền vào ví qua VNPay</h4>
                        <div class="transaction-date">10/12/2024 14:35</div>
                    </div>
                </div>
                <div class="transaction-amount">
                    <div class="amount-value positive">+500.000₫</div>
                    <span class="transaction-status status-success">Thành công</span>
                </div>
            </div>

            <!-- Sample Transaction 2: Mua thẻ -->
            <div class="transaction-item" data-type="purchase">
                <div class="transaction-info">
                    <div class="transaction-icon purchase">🛒</div>
                    <div class="transaction-details">
                        <h4>Mua thẻ Viettel 100.000đ</h4>
                        <div class="transaction-date">09/12/2024 18:22</div>
                    </div>
                </div>
                <div class="transaction-amount">
                    <div class="amount-value negative">-100.000₫</div>
                    <span class="transaction-status status-success">Thành công</span>
                </div>
            </div>

            <!-- Sample Transaction 3: Rút tiền -->
            <div class="transaction-item" data-type="withdraw">
                <div class="transaction-info">
                    <div class="transaction-icon withdraw">⬆️</div>
                    <div class="transaction-details">
                        <h4>Rút tiền về tài khoản ngân hàng</h4>
                        <div class="transaction-date">08/12/2024 10:15</div>
                    </div>
                </div>
                <div class="transaction-amount">
                    <div class="amount-value negative">-200.000₫</div>
                    <span class="transaction-status status-pending">Đang xử lý</span>
                </div>
            </div>

            <!-- Sample Transaction 4: Mua thẻ -->
            <div class="transaction-item" data-type="purchase">
                <div class="transaction-info">
                    <div class="transaction-icon purchase">🛒</div>
                    <div class="transaction-details">
                        <h4>Mua thẻ Mobifone 50.000đ</h4>
                        <div class="transaction-date">07/12/2024 21:45</div>
                    </div>
                </div>
                <div class="transaction-amount">
                    <div class="amount-value negative">-50.000₫</div>
                    <span class="transaction-status status-success">Thành công</span>
                </div>
            </div>

            <!-- Sample Transaction 5: Nạp tiền -->
            <div class="transaction-item" data-type="deposit">
                <div class="transaction-info">
                    <div class="transaction-icon deposit">⬇️</div>
                    <div class="transaction-details">
                        <h4>Nạp tiền vào ví qua MoMo</h4>
                        <div class="transaction-date">06/12/2024 16:00</div>
                    </div>
                </div>
                <div class="transaction-amount">
                    <div class="amount-value positive">+1.000.000₫</div>
                    <span class="transaction-status status-success">Thành công</span>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Filter transactions
    function filterTransactions(type) {
        const items = document.querySelectorAll('.transaction-item');
        const buttons = document.querySelectorAll('.filter-btn');

        // Update active button
        buttons.forEach(btn => btn.classList.remove('active'));
        event.target.classList.add('active');

        // Show/hide transactions
        items.forEach(item => {
            if (type === 'all' || item.dataset.type === type) {
                item.style.display = 'flex';
            } else {
                item.style.display = 'none';
            }
        });
    }

    // Animation on load
    document.addEventListener('DOMContentLoaded', function () {
        const items = document.querySelectorAll('.transaction-item');
        items.forEach((item, index) => {
            item.style.opacity = '0';
            item.style.transform = 'translateY(20px)';
            setTimeout(() => {
                item.style.transition = 'all 0.5s ease';
                item.style.opacity = '1';
                item.style.transform = 'translateY(0)';
            }, index * 100);
        });
    });
</script>
</body>
</html>