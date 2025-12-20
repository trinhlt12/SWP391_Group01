<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="vi_VN"/>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Lịch sử giao dịch | EmBanThe</title>
    <link href="${pageContext.request.contextPath}/image/Logo.png" rel="icon">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">

    <style>
        body { background-color: #f8f9fa; display: flex; flex-direction: column; min-height: 100vh;}
        .history-wrapper { margin-top: 100px; padding-bottom: 60px; flex: 1; }

        /* Box thống kê */
        .stat-card {
            background: white;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            text-align: center;
            height: 100%;
            border-bottom: 4px solid transparent;
        }
        .stat-card.balance { border-color: #0d6efd; }
        .stat-card.deposit { border-color: #198754; }
        .stat-card.spent { border-color: #dc3545; }

        .stat-title { font-size: 0.9rem; color: #6c757d; text-transform: uppercase; font-weight: 600; margin-bottom: 10px; }
        .stat-value { font-size: 1.6rem; font-weight: bold; }

        /* Bảng */
        .table-container { background: white; border-radius: 12px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); overflow: hidden; }
        .status-badge { font-size: 0.85rem; padding: 6px 12px; border-radius: 20px; }
    </style>
</head>

<body>

<jsp:include page="header.jsp" />

<main class="history-wrapper">
    <div class="container">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="fw-bold mb-0">Quản lý giao dịch</h2>
            <a href="${pageContext.request.contextPath}/ewallet" class="btn btn-primary rounded-pill px-4">
                <i class="bi bi-wallet2 me-2"></i>Nạp tiền ngay
            </a>
        </div>

        <div class="row g-4 mb-5">
            <div class="col-md-4">
                <div class="stat-card balance">
                    <div class="stat-title">Số dư hiện tại</div>
                    <div class="stat-value text-primary">
                        <fmt:formatNumber value="${sessionScope.user.balance}" pattern="#,###"/> đ
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card deposit">
                    <div class="stat-title">Tổng tiền đã nạp</div>
                    <div class="stat-value text-success">
                        +<fmt:formatNumber value="${overview.totalDeposit}" pattern="#,###"/> đ
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="stat-card spent">
                    <div class="stat-title">Tổng tiền đã chi tiêu</div>
                    <div class="stat-value text-danger">
                        <fmt:formatNumber value="${overview.totalSpent}" pattern="#,###"/> đ
                    </div>
                </div>
            </div>
        </div>

        <div class="card border-0 shadow-sm mb-4">
            <div class="card-body p-4">
                <form action="${pageContext.request.contextPath}/user-history" method="get">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-4">
                            <label class="form-label text-muted small">Tìm kiếm</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="bi bi-search"></i></span>
                                <input type="text" name="keyword" class="form-control bg-light border-start-0"
                                       placeholder="Nhập mã đơn, nội dung..." value="${paramKeyword}">
                            </div>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label text-muted small">Loại GD</label>
                            <select name="type" class="form-select bg-light">
                                <option value="">-- Tất cả --</option>
                                <option value="DEPOSIT" ${paramType == 'DEPOSIT' ? 'selected' : ''}>Nạp tiền</option>
                                <option value="PURCHASE" ${paramType == 'PURCHASE' ? 'selected' : ''}>Mua thẻ</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label class="form-label text-muted small">Từ ngày</label>
                            <input type="date" name="fromDate" class="form-control bg-light" value="${paramFromDate}">
                        </div>
                        <div class="col-md-2">
                            <label class="form-label text-muted small">Đến ngày</label>
                            <input type="date" name="toDate" class="form-control bg-light" value="${paramToDate}">
                        </div>
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-dark w-100 fw-bold">Lọc dữ liệu</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div class="table-container">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="bg-light">
                    <tr>
                        <th class="py-3 ps-4">Mã GD</th>
                        <th class="py-3">Thời gian</th>
                        <th class="py-3">Loại GD</th>
                        <th class="py-3">Nội dung</th>
                        <th class="py-3 text-end">Số tiền</th>
                        <th class="py-3 text-center">Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="t" items="${historyList}">
                        <tr>
                            <td class="ps-4 text-muted small fw-bold">#${t.transactionId}</td>
                            <td class="text-muted small">
                                <i class="bi bi-clock me-1"></i>
                                <fmt:formatDate value="${t.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${t.type == 'DEPOSIT'}">
                                        <span class="badge bg-success bg-opacity-10 text-success px-3 py-2">Nạp tiền</span>
                                    </c:when>
                                    <c:when test="${t.type == 'PURCHASE'}">
                                        <span class="badge bg-primary bg-opacity-10 text-primary px-3 py-2">Mua thẻ</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary bg-opacity-10 text-secondary px-3 py-2">${t.type}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${t.message}</td>

                            <td class="text-end fw-bold">
                                <c:choose>
                                    <c:when test="${t.type == 'DEPOSIT' || t.amount > 0}">
                                        <span class="text-success">
                                            +<fmt:formatNumber value="${t.amount}" pattern="#,###"/> đ
                                        </span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger">
                                            <fmt:formatNumber value="${t.amount}" pattern="#,###"/> đ
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td class="text-center">
                                <c:if test="${t.status == 'SUCCESS'}">
                                    <span class="status-badge bg-success text-white"><i class="bi bi-check2"></i> Thành công</span>
                                </c:if>
                                <c:if test="${t.status == 'PENDING'}">
                                    <span class="status-badge bg-warning text-dark"><i class="bi bi-hourglass-split"></i> Đang chờ</span>
                                </c:if>
                                <c:if test="${t.status == 'FAILED'}">
                                    <span class="status-badge bg-danger text-white"><i class="bi bi-x-lg"></i> Thất bại</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty historyList}">
                        <tr>
                            <td colspan="6" class="text-center py-5">
                                <i class="bi bi-inbox fs-1 text-muted"></i>
                                <p class="text-muted mt-2 mb-0">Không tìm thấy giao dịch nào.</p>
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>

            <c:if test="${totalPages > 1}">
                <div class="p-3 border-top">
                    <nav>
                        <ul class="pagination justify-content-center mb-0">
                            <c:set var="query" value="&keyword=${paramKeyword}&type=${paramType}&fromDate=${paramFromDate}&toDate=${paramToDate}" />

                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="?page=${currentPage - 1}${query}">Trước</a>
                            </li>

                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}${query}">${i}</a>
                                </li>
                            </c:forEach>

                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="?page=${currentPage + 1}${query}">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:if>
        </div>

    </div>
</main>

<script src="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assetsHome/js/main.js"></script>

</body>
</html>