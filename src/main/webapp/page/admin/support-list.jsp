<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 19/12/2025
  Time: 11:34 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title> Em Ban The | Admin System </title>
    <link href="image/Logo.png" rel="icon">

    <link rel="apple-touch-icon" sizes="144x144"
          href="${pageContext.request.contextPath}/assetAdmin/apple-touch-icon.png">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assetAdmin/favicon.ico">
    <meta name="theme-color" content="#3063A0">
    <link href="https://fonts.googleapis.com/css?family=Fira+Sans:400,500,600" rel="stylesheet">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assetAdmin/assets/vendor/open-iconic/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/vendor/fontawesome/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/theme.min.css"
          data-skin="default">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/theme-dark.min.css"
          data-skin="dark">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/custom.css">
    <base href="${pageContext.request.contextPath}/">
    <style>

        .status-processing {
            color: #fff;
            background: #ffc107;
            padding: 4px 8px;
            border-radius: 4px;
            font-weight: bold;
        }

        .status-approved {
            color: #fff;
            background: #28a745;
            padding: 4px 8px;
            border-radius: 4px;
            font-weight: bold;
        }

        .status-rejected {
            color: #fff;
            background: #6c757d;
            padding: 4px 8px;
            border-radius: 4px;
            font-weight: bold;
        } </style>
</head>
<body class="app">
<div class="app">
    <header class="app-header app-header-dark">
        <div class="top-bar">
            <div class="top-bar-brand">
                <button class="hamburger hamburger-squeeze mr-2" type="button" data-toggle="aside-menu">
                    <span class="hamburger-box"><span class="hamburger-inner"></span></span>
                </button>
                <a href="home">
                    <a href="home"
                       class="text-decoration-none d-flex align-items-center">
    <span style="
        font-size: 22px;
        font-weight: 700;
        color: #ffc107;
        letter-spacing: 1px;">
        EMBANTHE
    </span>
                        <span style="
        font-size: 14px;
        margin-left: 6px;
        color: #ffffff;
        opacity: 0.8;">
    </span>
                    </a>
                </a>
            </div>
            <div class="top-bar-list">
                <div class="top-bar-item top-bar-item-right px-0 d-none d-sm-flex">
                    <div class="dropdown d-flex">
                        <button class="btn-account d-none d-md-flex" type="button" data-toggle="dropdown">
                                <span class="user-avatar user-avatar-md">
                                    <img src="assetAdmin/assets/images/avatars/profile.jpg"
                                         alt="">
                                </span>
                            <span class="account-summary pr-lg-4 d-none d-lg-block">
                                    <span class="account-name">Admin</span>
                                    <span class="account-description">Quản lý</span>
                                </span>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#"><span class="dropdown-icon oi oi-person"></span>
                                Profile</a>
                            <a class="dropdown-item" href="logout"><span
                                    class="dropdown-icon oi oi-account-logout"></span> Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
    <aside class="app-aside app-aside-expand-md app-aside-light">
        <div class="aside-content">
            <div class="aside-menu overflow-hidden">
                <nav id="stacked-menu" class="stacked-menu">
                    <ul class="menu">
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin" class="menu-link">
                                <span class="menu-icon fas fa-home"></span> <span class="menu-text">Trang chủ</span>
                            </a>
                        </li>
                        <li class="menu-item ">
                            <a href="${pageContext.request.contextPath}/admin/user-list" class="menu-link">
                                <span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý User</span>
                            </a>
                        </li>
                        <li class="menu-item has-active">
                            <a href="#" class="menu-link">
                                <span class="menu-icon oi oi-browser"></span> <span
                                    class="menu-text">Quản lý Support</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="#" class="menu-link">
                                <span class="menu-icon fas fa-users"></span> <span
                                    class="menu-text">Quản lý System</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </aside>
    <main class="app-main">
        <div class="wrapper">
            <!-- .page -->
            <div class="page">
                <!-- .page-inner -->
                <div class="page-inner">
                    <!-- .page-title-bar -->
                    <header class="page-title-bar">
                        <!-- .breadcrumb -->
                        <!-- /.breadcrumb -->
                        <!-- floating action -->
                        <button type="button" class="btn btn-success btn-floated"><span class="fa fa-plus"></span>
                        </button>
                        <!-- /floating action -->
                        <!-- title and toolbar -->
                        <div class="d-md-flex align-items-md-start">
                            <h1 class="page-title mr-sm-auto"> Support Request List </h1><!-- .btn-toolbar -->
                            <!-- /.btn-toolbar -->
                        </div><!-- /title and toolbar -->
                    </header><!-- /.page-title-bar -->
                    <!-- .page-section -->
                    <div class="page-section">
                        <!-- .card -->
                        <div class="card card-fluid">
                            <!-- .card-header -->
                            <!-- /.card-header -->
                            <!-- .card-body -->
                            <div class="card-body">
                                <!-- .form-group -->
                                <div class="form-group">
                                    <!-- .input-group -->
                                    <div class="input-group input-group-alt">
                                        <!-- .input-group-prepend -->
                                        <div class="input-group-prepend">
                                            <form action="${pageContext.request.contextPath}/admin/support-list"
                                                  method="get" class="form-inline mb-3">
                                                <select class="custom-select mr-2" name="subject">
                                                    <option value="">-- Chọn chủ đề --</option>
                                                    <option value="Bán thẻ hỗ trợ" ${selectedSubject eq 'ban-the-ho-tro' ? 'selected' : ''}>
                                                        Bán thẻ hỗ trợ
                                                    </option>
                                                    <option value="Đơn hỗ trợ khác" ${selectedSubject eq 'don-ho-tro-khac' ? 'selected' : ''}>
                                                        Đơn hỗ trợ khác
                                                    </option>
                                                    <option value="Vấn đề tài khoản" ${selectedSubject eq 'tai-khoan' ? 'selected' : ''}>
                                                        Vấn đề tài khoản
                                                    </option>
                                                    <option value="Thanh toán" ${selectedSubject eq 'thanh-toan' ? 'selected' : ''}>
                                                        Thanh toán
                                                    </option>
                                                    <option value="Hỗ trợ kỹ thuật" ${selectedSubject eq 'ky-thuat' ? 'selected' : ''}>
                                                        Hỗ trợ kỹ thuật
                                                    </option>
                                                </select>
                                                <select class="custom-select mr-2" name="status">
                                                    <option value="">-- Chọn trạng thái --</option>
                                                    <option value="Processing" ${selectedStatus eq 'Processing' ? 'selected' : ''}>
                                                        Processing
                                                    </option>
                                                    <option value="Approved" ${selectedStatus eq 'Approved' ? 'selected' : ''}>
                                                        Approved
                                                    </option>
                                                    <option value="Rejected" ${selectedStatus eq 'Rejected' ? 'selected' : ''}>
                                                        Rejected
                                                    </option>
                                                </select>
                                                <button type="submit" class="btn btn-primary">Lọc</button>
                                            </form>

                                        </div><!-- /.input-group-prepend -->
                                        <!-- .input-group -->
                                        <!-- /.input-group -->
                                    </div><!-- /.input-group -->
                                </div><!-- /.form-group -->
                                <!-- .table-responsive -->
                                <div class="text-muted">  ${totalRequests} entries</div>
                                <div class="table-responsive">
                                    <!-- .table -->
                                    <table class="table"> <!-- thead -->
                                        <thead>
                                        <tr>
                                            <th> ID</th>
                                            <th>User ID</th>
                                            <th>Title</th>
                                            <th>Message</th>
                                            <th>Status</th>
                                            <th>Created At</th>
                                            <th>Updated At</th>
                                            <th>Action</th>
                                        </tr>
                                        </thead> <!-- tbody -->
                                        <tbody><c:forEach var="req" items="${supportRequests}">
                                            <tr>

                                                <td class="align-middle">${req.requestId}</td>

                                                <td class="align-middle"><c:forEach var="u" items="${users}"> <c:if
                                                        test="${u.userId == req.userId}"> ${u.username} </c:if>
                                                </c:forEach></td>
                                                <td class="align-middle">${req.title}</td>
                                                <td class="align-middle">${req.message}</td>
                                                <td class="align-middle">
                                                    <span class="status-${fn:toLowerCase(req.status)}">${req.status}</span>
                                                </td>

                                                <td class="align-middle">
                                                    <fmt:formatDate value="${req.createdAt}"
                                                                    pattern="yyyy-MM-dd HH:mm:ss"/>
                                                </td>

                                                <td class="align-middle">
                                                    <c:choose>
                                                        <c:when test="${req.processedAt != null}">
                                                            <fmt:formatDate value="${req.processedAt}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ---
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="align-middle">
                                                    <a href="admin/support-detail?id=${req.requestId}"
                                                       class="btn btn-sm btn-icon "
                                                       data-toggle="modal"
                                                       data-target="#editUserModal">
                                                        <i class="fa fa-eye"></i>
                                                    </a>

                                                </td>
                                            </tr>
                                        </c:forEach></tbody>
                                    </table><!-- /.table -->
                                </div><!-- /.table-responsive -->
                                <div class="modal-footer">
                                    <nav aria-label="Page navigation">
                                        <ul class="pagination mb-0">
                                            <!-- Các số trang -->
                                            <c:set var="totalPages"
                                                   value="${(totalRequests / pageSize) + (totalRequests % pageSize > 0 ? 1 : 0)}"/>
                                            <c:forEach var="i" begin="1" end="${totalPages}">
                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                    <a class="page-link"
                                                       href="admin/support-list?page=${i}&subject=${selectedSubject}&status=${selectedStatus}">
                                                            ${i} <c:if test="${i == currentPage}"><span class="sr-only">(current)</span></c:if>
                                                    </a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </nav>
                                </div>


                            </div><!-- /.card-body -->
                        </div><!-- /.card -->


                    </div><!-- /grid row -->
                </div>
            </div>
        </div>
    </main>
</div>
</div>
<script>
    var skin = localStorage.getItem('skin') || 'default';
    var disabledSkinStylesheet = document.querySelector('link[data-skin]:not([data-skin="' + skin + '"])');
    disabledSkinStylesheet.setAttribute('rel', '');
    disabledSkinStylesheet.setAttribute('disabled', true);
</script>
</body>
</html>
