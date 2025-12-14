<%--
  Created by IntelliJ IDEA.
  User: hungd
  Date: 13/12/2025
  Time: 11:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết người dùng | Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .card { box-shadow: 0 0.15rem 1.75rem 0 rgba(58, 59, 69, 0.15); border: none; border-radius: 0.35rem; }
        .nav-tabs .nav-link { color: #495057; }
        .nav-tabs .nav-link.active { font-weight: bold; color: #0d6efd; }
    </style>
</head>
<body class="bg-light">

<%-- <jsp:include page="../../includes/sidebar.jsp" /> --%>

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="h3 text-gray-800">Hồ sơ người dùng</h2>
        <a href="${pageContext.request.contextPath}/admin/user-list" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Quay lại danh sách
        </a>
    </div>

    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show">
                ${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% session.removeAttribute("message"); %>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible fade show">
                ${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        <% session.removeAttribute("error"); %>
    </c:if>

    <div class="row">
        <div class="col-xl-4">
            <div class="card mb-4 mb-xl-0">
                <div class="card-header"></div>
                <div class="card-body text-center">
                    <img class="img-account-profile rounded-circle mb-2"
                         src="https://ui-avatars.com/api/?name=${user.fullName}&background=random&size=150"
                         alt="Avatar">
                    <div class="small font-italic text-muted mb-4">ID: #${user.userId}</div>
                    <h5 class="fw-bold">${user.username}</h5>
                    <p class="badge ${user.status == 'Active' ? 'bg-success' : 'bg-danger'}">
                        ${user.status}
                    </p>
                    <hr>
                    <div class="text-start px-4">
                        <p><strong><i class="fas fa-wallet"></i> Số dư:</strong>
                            <span class="text-success fw-bold">
                              <fmt:formatNumber value="${user.balance}" pattern="#,##0"/> đ
                           </span>
                        </p>
                        <p><strong><i class="fas fa-calendar"></i> Ngày tạo:</strong> ${user.createdAt}</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xl-8">
            <div class="card">
                <div class="card-header">
                    <ul class="nav nav-tabs card-header-tabs" id="myTab" role="tablist">
                        <li class="nav-item">
                            <button class="nav-link active" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile" type="button">Thông tin chung</button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" id="security-tab" data-bs-toggle="tab" data-bs-target="#security" type="button">Bảo mật</button>
                        </li>
                        <li class="nav-item">
                            <button class="nav-link" id="history-tab" data-bs-toggle="tab" data-bs-target="#history" type="button">Lịch sử giao dịch</button>
                        </li>
                    </ul>
                </div>

                <div class="card-body">
                    <div class="tab-content" id="myTabContent">

                        <div class="tab-pane fade show active" id="profile">
                            <form action="${pageContext.request.contextPath}/admin/user-update" method="post">
                                <input type="hidden" name="id" value="${user.userId}">
                                <div class="mb-3">
                                    <label class="small mb-1">Họ và tên</label>
                                    <input class="form-control" name="fullName" type="text" value="${user.fullName}">
                                </div>
                                <div class="row gx-3 mb-3">
                                    <div class="col-md-6">
                                        <label class="small mb-1">Email</label>
                                        <input class="form-control" name="email" type="email" value="${user.email}">
                                    </div>
                                    <div class="col-md-6">
                                        <label class="small mb-1">Số điện thoại</label>
                                        <input class="form-control" name="phone" type="text" value="${user.phone}">
                                    </div>
                                </div>
                                <div class="row gx-3 mb-3">
                                    <div class="col-md-6">
                                        <label class="small mb-1">Vai trò</label>
                                        <select class="form-select" name="role">
                                            <option value="CUSTOMER" ${user.role == 'Customer' ? 'selected' : ''}>Customer</option>
                                            <option value="Admin" ${user.role == 'Admin' ? 'selected' : ''}>Admin</option>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="small mb-1">Trạng thái</label>
                                        <select class="form-select" name="status">
                                            <option value="Active" ${user.status == 'Active' ? 'selected' : ''}>Active</option>
                                            <option value="Banned" ${user.status == 'Banned' ? 'selected' : ''}>Banned</option>
                                            <option value="Inactive" ${user.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                                        </select>
                                    </div>
                                </div>
                                <button class="btn btn-primary" type="submit">Lưu thay đổi</button>
                            </form>
                        </div>

                        <div class="tab-pane fade" id="security">
                            <form action="${pageContext.request.contextPath}/admin/user-security" method="post">
                                <input type="hidden" name="id" value="${user.userId}">
                                <div class="mb-3">
                                    <label class="small mb-1">Mật khẩu mới</label>
                                    <input class="form-control" name="newPass" type="password" required>
                                </div>
                                <div class="mb-3">
                                    <label class="small mb-1">Xác nhận mật khẩu</label>
                                    <input class="form-control" name="confirmPass" type="password" required>
                                </div>
                                <button class="btn btn-danger" type="submit">Đổi mật khẩu</button>
                            </form>
                        </div>

                        <div class="tab-pane fade" id="history">
                            <c:if test="${empty history}">
                                <p class="text-center text-muted my-4">Chưa có giao dịch nào gần đây.</p>
                            </c:if>
                            <c:if test="${not empty history}">
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover">
                                        <thead class="table-light">
                                        <tr>
                                            <th>Mã GD</th>
                                            <th>Loại</th>
                                            <th>Số tiền</th>
                                            <th>Trạng thái</th>
                                            <th>Ngày tạo</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="t" items="${history}">
                                            <tr>
                                                <td>#${t.transactionId}</td>
                                                <td>
                                                        <span class="badge ${t.type == 'DEPOSIT' ? 'bg-primary' : 'bg-info'}">
                                                                ${t.type}
                                                        </span>
                                                </td>
                                                <td class="${t.type == 'DEPOSIT' ? 'text-success' : 'text-danger'} fw-bold">
                                                        ${t.type == 'DEPOSIT' ? '+' : '-'}<fmt:formatNumber value="${t.amount}" /> đ
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${t.status == 'Success'}">
                                                            <span class="badge bg-success">Thành công</span>
                                                        </c:when>
                                                        <c:when test="${t.status == 'Pending'}">
                                                            <span class="badge bg-warning text-dark">Chờ xử lý</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-danger">Thất bại</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${t.createdAt}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:if>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
