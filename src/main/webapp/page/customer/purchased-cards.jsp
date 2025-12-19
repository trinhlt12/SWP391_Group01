<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kho thẻ đã mua - Em Bán Thẻ</title>
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">
    <style>
        body {
            background: #f9fafb;
            font-family: 'Inter', sans-serif;
        }

        .page-container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .card-box {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        }

        .page-title {
            color: #059669;
            font-weight: 700;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            gap: 20px;
        }

        /* Table Styles */
        .table-custom {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0 10px;
        }

        .table-custom thead th {
            border: none;
            color: #6b7280;
            font-weight: 600;
            padding: 15px;
            text-align: left;
        }

        .table-custom tbody tr {
            background: #f8fafc;
            transition: 0.3s;
        }

        .table-custom tbody tr:hover {
            background: #f0fdf4;
            transform: translateY(-2px);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
        }

        .table-custom td {
            padding: 15px;
            border-top: 1px solid #e5e7eb;
            border-bottom: 1px solid #e5e7eb;
            vertical-align: middle;
            color: #374151;
        }

        .table-custom td:first-child {
            border-left: 1px solid #e5e7eb;
            border-top-left-radius: 10px;
            border-bottom-left-radius: 10px;
        }

        .table-custom td:last-child {
            border-right: 1px solid #e5e7eb;
            border-top-right-radius: 10px;
            border-bottom-right-radius: 10px;
        }

        /* Code Styles */
        .code-box {
            font-family: 'Courier New', monospace;
            font-weight: 700;
            letter-spacing: 1px;
            background: white;
            padding: 5px 10px;
            border: 1px dashed #d1d5db;
            border-radius: 5px;
            color: #111827;
        }

        .btn-copy {
            font-size: 0.8rem;
            margin-left: 8px;
            cursor: pointer;
            color: #059669;
            border: none;
            background: none;
        }

        .btn-copy:hover {
            text-decoration: underline;
        }

        /* Thêm đoạn này vào trong thẻ <style> */
        .code-cell {
            display: flex; /* Dùng Flexbox để xếp ngang */
            align-items: center; /* Căn giữa theo chiều dọc */
            white-space: nowrap; /* QUAN TRỌNG: Cấm tuyệt đối việc xuống dòng */
            gap: 10px; /* Khoảng cách giữa mã thẻ và nút Copy */
        }
    </style>
</head>
<body>

<jsp:include page="/header.jsp"/>


<div class="page-container">
    <div class="card-box">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="page-title">Kho thẻ đã mua</h2>
            <a href="${pageContext.request.contextPath}/ewallet" class="btn btn-outline-secondary">
                ← Quay lại Ví
            </a>
        </div>
        <!-- Tìm kiếm -->
        <div class="row mb-3">
            <div class="col-md-8 offset-md-2">  <!-- Hoặc col-md-6 offset-md-3 nếu muốn hẹp hơn -->
                <form action="purchased-cards" method="GET" class="d-flex gap-2">
                    <input type="text" name="search" class="form-control"
                           placeholder="Tìm theo tên thẻ, serial hoặc mã code..."
                           value="${searchKeyword}">
                    <button type="submit" class="btn btn-primary">Tìm</button>

                    <c:if test="${not empty searchKeyword}">
                        <a href="purchased-cards" class="btn btn-outline-secondary">Xóa</a>
                    </c:if>
                </form>
            </div>
        </div>


        <c:if test="${empty purchasedList}">
            <div class="text-center py-5 text-muted">
                <div style="font-size: 3rem;"></div>
                <p>Bạn chưa mua thẻ nào.</p>
                <a href="${pageContext.request.contextPath}/service" class="btn btn-primary mt-2">Mua ngay</a>
            </div>
        </c:if>

        <c:if test="${not empty purchasedList}">
            <div class="table-responsive">
                <table class="table-custom">
                    <thead>
                    <tr>
                        <th>Tên thẻ</th>
                        <th>Mệnh giá</th>
                        <th>Serial</th>
                        <th>Mã thẻ</th>
                        <th>Ngày mua</th>
                        <th>Hạn sử dụng</th>
                        <th>Đơn hàng</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${purchasedList}">
                        <tr>
                            <td style="font-weight: 600; color: #059669;">${item.productName}</td>
                            <td>
                                <fmt:formatNumber value="${item.price}" pattern="#,###"/>đ
                            </td>
                            <td>${item.serialNumber}</td>
                            <td>
                                <!-- Bọc vào div này -->
                                <div class="code-cell">
                                    <span class="code-box">${item.cardCode}</span>
                                    <!-- Xóa margin-left cũ đi nếu có, vì đã dùng gap trong css -->
                                    <button class="btn-copy" onclick="copyText('${item.cardCode}')" style="margin: 0;">Copy</button>
                                </div>
                            </td>
                            <td><fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                            <td style="color: #dc2626;">
                                <fmt:formatDate value="${item.expirationDate}" pattern="dd/MM/yyyy"/>
                            </td>
                            <td>#${item.orderId}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation" class="mt-4">
                        <ul class="pagination justify-content-center">

                            <!-- Nút Previous -->
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                   href="purchased-cards?page=${currentPage - 1}&search=${searchKeyword}">
                                    &laquo;
                                </a>
                            </li>

                            <!-- Các nút số trang -->
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link"
                                       href="purchased-cards?page=${i}&search=${searchKeyword}">
                                            ${i}
                                    </a>
                                </li>
                            </c:forEach>

                            <!-- Nút Next -->
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link"
                                   href="purchased-cards?page=${currentPage + 1}&search=${searchKeyword}">
                                    &raquo;
                                </a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </c:if>
    </div>
</div>

<script>
    function copyText(text) {
        navigator.clipboard.writeText(text).then(() => {
            alert("Đã sao chép mã thẻ: " + text);
        });
    }
</script>

</body>
</html>