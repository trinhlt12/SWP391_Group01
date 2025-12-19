<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-length, initial-scale=1.0">
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
            margin-top: 60px;
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
            container-type: inline-size;
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
            font-size: clamp(2rem, 13cqw, 3rem);
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

        /* Deposit Form Section*/
        .deposit-form-container {
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.5s ease, margin 0.5s ease, opacity 0.5s ease;
            opacity: 0;
            margin-bottom: 0;
        }

        .deposit-form-container.active {
            max-height: 1000px;
            opacity: 1;
            margin-bottom: 30px;
        }

        .deposit-form {
            background: white;
            border: 2px solid #10B981;
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 5px 20px rgba(16, 185, 129, 0.15);
        }

        .form-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
        }

        .form-header h3 {
            color: #059669;
            font-size: 1.5rem;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .close-btn {
            background: #fee2e2;
            color: #dc2626;
            border: none;
            width: 35px;
            height: 35px;
            border-radius: 50%;
            cursor: pointer;
            font-size: 1.2rem;
            transition: all 0.3s ease;
        }

        .close-btn:hover {
            background: #dc2626;
            color: white;
            transform: rotate(90deg);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            color: #374151;
            font-weight: 600;
            margin-bottom: 10px;
            font-size: 0.95rem;
        }

        .amount-input {
            width: 100%;
            padding: 15px 20px;
            border: 2px solid #e5e7eb;
            border-radius: 12px;
            font-size: 1.1rem;
            transition: all 0.3s ease;
            font-family: 'Inter', sans-serif;
        }

        .amount-input:focus {
            outline: none;
            border-color: #10B981;
            box-shadow: 0 0 0 3px rgba(16, 185, 129, 0.1);
        }

        /* Quick Amount Buttons */
        .quick-amounts {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 10px;
            margin-bottom: 20px;
        }

        .quick-amount-btn {
            padding: 12px;
            border: 2px solid #e5e7eb;
            background: white;
            border-radius: 10px;
            cursor: pointer;
            font-weight: 600;
            color: #374151;
            transition: all 0.3s ease;
        }

        .quick-amount-btn:hover {
            border-color: #10B981;
            color: #10B981;
            background: #f0fdf4;
        }

        .quick-amount-btn.selected {
            background: #10B981;
            color: white;
            border-color: #10B981;
        }

        .submit-btn {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #10B981 0%, #059669 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(16, 185, 129, 0.3);
        }

        .submit-btn:active {
            transform: translateY(0);
        }

        /* Responsive cho form */
        @media (max-width: 768px) {
            .quick-amounts {
                grid-template-columns: repeat(2, 1fr);
            }
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
            display: flex; /* Quan trọng: Để Tiền và Badge nằm ngang */
            flex-direction: row; /* Xếp theo hàng ngang */
            align-items: end;
            justify-content: flex-end; /* Đẩy hết sang bên phải */
            gap: 15px; /* Khoảng cách giữa Số tiền và Badge */
            min-width: 200px; /* Đảm bảo đủ rộng để không bị xuống dòng */
        }

        .amount-value {
            font-size: 1.2rem;
            font-weight: 700;
            margin-bottom: 0 !important;
            white-space: nowrap;
        }

        .amount-value.positive {
            color: #059669;
        }

        .amount-value.negative {
            color: #dc2626;
        }

        .transaction-status {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            white-space: nowrap; /* Không cho badge bị xuống dòng */
            height: fit-content;
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
    <jsp:include page="/header.jsp"/>
    <!-- Page Header -->
    <div class="page-header">
        <h1>💳 Em Bán Thẻ E-Wallet</h1>
    </div>

    <div class="row">
        <div class="col-lg-4 col-md-5 mb-4">
            <!-- Balance Card -->
            <div class="balance-card">
                <div class="balance-label">Số dư hiện tại</div>
                <div class="balance-amount">
                    <fmt:formatNumber value="${sessionScope.user.balance}" type="currency" currencySymbol="₫"/></div>
                <div class="wallet-id">ID Ví: <strong>EBT-${sessionScope.user.userId}</strong></div>

            </div>

            <!-- Quick Actions -->
            <div class="quick-actions">
                <a href="javascript:void(0)" class="action-btn" onclick="toggleDepositForm()">
                    <div class="icon">💰</div>
                    <div class="label">Nạp Tiền</div>
                </a>
            </div>
            <!-- Deposit Form - Hidden by default -->
            <div class="deposit-form-container" id="depositFormContainer">
                <div class="deposit-form">
                    <div class="form-header">
                        <h3>💰 Nạp Tiền Vào Ví</h3>
                        <button class="close-btn" onclick="toggleDepositForm()">×</button>
                    </div>

                    <c:if test="${not empty errorMessage}">
                        <div id="error-alert" class="alert alert-danger alert-dismissible fade show" role="alert">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i> <!-- Icon cảnh báo -->
                            <strong>Lỗi:</strong> ${errorMessage}

                        </div>
                    </c:if>

                    <form action="<%= request.getContextPath() %>/ewallet" method="POST" onsubmit="return validateAmount()">
                        <div class="form-group">
                            <label for="amount">Số tiền muốn nạp (VNĐ)</label>
                            <input
                                    type="text"
                                    id="amount"
                                    name="amount"
                                    class="amount-input"
                                    placeholder="Nhập số tiền..."
                                    oninput="formatCurrency(this); clearError()"
                                    required
                            />
                        </div>

                        <button type="submit" class="submit-btn">
                            Tiếp Tục Thanh Toán →
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-lg-8 col-md-7">
            <!-- Transaction History -->
            <div class="transaction-section">
                <div class="section-header">
                    <h2>Giao Dịch Gần Đây</h2>
                    <div class="filter-group">
                        <button class="filter-btn active" onclick="filterTransactions('all')">Tất cả</button>
                        <button class="filter-btn" onclick="filterTransactions('deposit')">Nạp tiền</button>
                        <button class="filter-btn" onclick="filterTransactions('purchase')">Mua hàng</button>
                    </div>
                </div>

                <div class="transaction-list">

                    <!-- Kiểm tra nếu list rỗng -->
                    <c:if test="${empty transactionList}">
                        <div class="empty-state">
                            <div class="icon">📭</div>
                            <p>Chưa có giao dịch nào.</p>
                        </div>
                    </c:if>

                    <!-- Vòng lặp duyệt danh sách -->
                    <c:forEach var="trans" items="${transactionList}">

                        <!-- Logic xác định loại giao dịch để hiển thị màu sắc/icon -->
                        <c:set var="isDeposit" value="${trans.type == 'DEPOSIT'}"/>
                        <c:set var="iconClass" value="${isDeposit ? 'deposit' : 'purchase'}"/>
                        <c:set var="iconSymbol" value="${isDeposit ? '⬇️' : '🛒'}"/>
                        <c:set var="amountSign" value="${isDeposit ? '+' : '-'}"/>
                        <c:set var="amountClass" value="${isDeposit ? 'positive' : 'negative'}"/>

                        <!-- data-type dùng cho bộ lọc JS (deposit/purchase) -->
                        <div class="transaction-item" data-type="${isDeposit ? 'deposit' : 'purchase'}">
                            <div class="transaction-info">
                                <div class="transaction-icon ${iconClass}">
                                        ${iconSymbol}
                                </div>
                                <div class="transaction-details">
                                    <h4>${trans.message}</h4>
                                    <div class="transaction-date">
                                        <!-- Format ngày tháng -->
                                        <fmt:formatDate value="${trans.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                        <a href="transaction-detail?id=${trans.transactionId}"
                                           style="color: #059669; text-decoration: underline; font-weight: bold;">
                                            Xem chi tiết
                                        </a>
                                    </div>
                                </div>
                            </div>
                            <div class="transaction-amount">
                                <div class="amount-value ${amountClass}">
                                        ${amountSign}
                                    <fmt:formatNumber value="${trans.amount}" type="currency" currencySymbol="₫"/>
                                </div>

                                <!-- Hiển thị trạng thái (Badge) -->
                                <c:choose>
                                    <c:when test="${trans.status == 'SUCCESS'}">
                                        <span class="transaction-status status-success">Thành công</span>
                                    </c:when>
                                    <c:when test="${trans.status == 'PENDING'}">
                                        <span class="transaction-status status-pending">Đang xử lý</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="transaction-status status-failed">Thất bại</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                                <%--
                                                    <div class="transaction-details">
                                &lt;%&ndash;
                                                        <h4>${trans.message}</h4>
                                &ndash;%&gt;
                                                        <div class="transaction-date">

                                                        </div>
                                                    </div>--%>
                        </div>
                    </c:forEach>

                </div>
                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">

                            <!-- Nút Previous -->
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="ewallet?page=${currentPage - 1}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>

                            <!-- Các nút số trang -->
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="ewallet?page=${i}">${i}</a>
                                </li>
                            </c:forEach>

                            <!-- Nút Next -->
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="ewallet?page=${currentPage + 1}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>


</div>

<!-- Transaction Detail Modal -->
<div class="modal fade" id="transactionDetailModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chi Tiết Giao Dịch</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <%--<div class="modal-body" id="modal-content-placeholder">
                <!-- Nội dung sẽ được AJAX load vào đây -->
                <div class="text-center">
                    <div class="spinner-border text-success" role="status"></div>
                    <p>Đang tải dữ liệu...</p>
                </div>
            </div>--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>

<script>

    function viewTransactionDetail(transactionId) {
        const modalElement = document.getElementById('transactionDetailModal');
        const modal = new bootstrap.Modal(modalElement);
        modal.show();

        document.getElementById('modal-content-placeholder').innerHTML =
            '<div class="text-center py-3"><div class="spinner-border text-success"></div></div>';

        fetch('ewallet?mode=detail&id=' + transactionId)
            .then(response => {
                if (!response.ok) throw new Error('Failed to load');
                return response.text();
            })
            .then(html => {
                document.getElementById('modal-content-placeholder').innerHTML = html;
            })
            .catch(error => {
                document.getElementById('modal-content-placeholder').innerHTML =
                    '<p class="text-danger text-center">Không thể tải thông tin giao dịch.</p>';
            });
    }

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

    // Toggle Deposit Form
    function toggleDepositForm() {
        const container = document.getElementById('depositFormContainer');
        container.classList.toggle('active');

        // Scroll to form nếu đang mở
        if (container.classList.contains('active')) {
            setTimeout(() => {
                container.scrollIntoView({behavior: 'smooth', block: 'nearest'});
            }, 100);
        }
    }

    // Select quick amount
    function selectAmount(amount) {
        const input = document.getElementById('amount');
        input.value = amount.toLocaleString('vi-VN');

        // Highlight selected button
        document.querySelectorAll('.quick-amount-btn').forEach(btn => {
            btn.classList.remove('selected');
        });
        event.target.classList.add('selected');
    }

    // Format currency as user types
    function formatCurrency(input) {
        // Remove non-numeric characters
        let value = input.value.replace(/\D/g, '');

        // Format with thousand separators
        if (value) {
            value = parseInt(value).toLocaleString('vi-VN');
        }

        input.value = value;
    }

    function validateAmount() {
        const input = document.getElementById('amount');
        const value = parseInt(input.value.replace(/\D/g, ''));

        if (!value || value < 10000) {
            alert('Số tiền nạp tối thiểu là 10.000đ');
            return false;
        }

        if (value > 50000000) {
            alert('Số tiền nạp tối đa là 50.000.000đ');
            return false;
        }

        // Store raw number for backend
        input.value = value;
        return true;
    }

    function clearError() {
        const errorAlert = document.getElementById('error-alert');
        if(errorAlert){
            errorAlert.remove();
        }
    }

    <c:if test="${not empty errorMessage}">
    document.addEventListener('DOMContentLoaded', function () {
        const container = document.getElementById('depositFormContainer');
        container.classList.add('active');

        container.scrollIntoView({behavior: 'smooth', block: 'center'});
    });
    </c:if>
</script>
<script src="assetsHome/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>