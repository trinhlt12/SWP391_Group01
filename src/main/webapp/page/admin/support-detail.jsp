<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 19/12/2025
  Time: 6:17 CH
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
                        <li class="menu-item ">
                            <a href="${pageContext.request.contextPath}/admin" class="menu-link"><span class="menu-icon fas fa-home"></span> <span class="menu-text">Dashboard</span></a>
                        </li>
                        <li class="menu-header">Hệ thống</li>
                        <li class="menu-item ">
                            <a href="${pageContext.request.contextPath}/admin/user-list" class="menu-link"><span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý User</span></a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/products" class="menu-link"><span class="menu-icon fas fa-box"></span> <span class="menu-text">Quản lý Sản Phẩm</span></a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/providers" class="menu-link"><span class="menu-icon fas fa-building"></span> <span class="menu-text">Quản lý Nhà mạng</span></a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/category" class="menu-link"><span class="menu-icon fas fa-tags"></span> <span class="menu-text">Quản lý Loại thẻ</span></a>
                        </li>
                        <li class="menu-item has-active">
                            <a href="${pageContext.request.contextPath}/admin/support-list" class="menu-link ">
                                <span class="menu-icon oi oi-browser"></span> <span
                                    class="menu-text">Quản lý Support</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/carditems" class="menu-link">
                                <span class="menu-icon fas fa-warehouse"></span> <span class="menu-text">Kho hàng</span>

                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </aside>
    <main class="app-main">
        <div class="wrapper">

            <div class="sidebar-section sidebar-section-fill">

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <!-- Phần tiêu đề bên trái -->
                    <div>
                        <h1 class="page-title mb-0">
                            <i class="far fa-building text-muted mr-2"></i>${fn:escapeXml(supportRequest.title)}
                        </h1>
                        <p class="text-muted mb-0">Từ người dùng: ${username}</p>
                    </div>

                    <!-- Phần Back bên phải -->
                    <div>
                        <a href="admin/support-list" class="btn btn-outline-secondary">
                            <i class="fa fa-angle-left mr-2"></i>Back
                        </a>
                    </div>
                </div>
<%--                <div class="nav-scroller border-bottom">--%>

<%--                    <ul class="nav nav-tabs">--%>
<%--                        <li class="nav-item">--%>
<%--                            <a class="nav-link active show" data-toggle="tab" href="#client-billing-contact">Billing--%>
<%--                                &--%>
<%--                                Contact</a>--%>
<%--                        </li>--%>
<%--                        <li class="nav-item">--%>
<%--                            <a class="nav-link" data-toggle="tab" href="#client-tasks">To Do</a>--%>
<%--                        </li>--%>

<%--                    </ul><!-- /.nav-tabs -->--%>
<%--                </div><!-- /.nav-scroller -->--%>
                <div class="tab-content pt-4" id="clientDetailsTabs">
                    <div class="tab-pane fade show active" id="client-billing-contact" role="tabpanel"
                         aria-labelledby="client-billing-contact-tab">
                        <!-- .card -->
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <!-- .card-body -->
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h2 id="client-billing-contact-tab" class="card-title"> Nội Dung </h2>
                                        </div>
                                        <address>
                                            ${fn:escapeXml(supportRequest.message)}
                                        </address>
                                    </div><!-- /.card-body -->
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <!-- .card-body -->
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <h2 id="client-billing-contact-tab" class="card-title"> Ngày Gửi </h2>
                                        </div>
                                        <address>
                                            <fmt:formatDate value="${supportRequest.createdAt}"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </address>
                                    </div><!-- /.card-body -->
                                </div>

                            </div>
                        </div>
                        <!-- /.card -->
                        <!-- .card -->
                        <div class="card mt-4">
                            <!-- .card-body -->
                            <c:if test="${not empty mes}">
                                <div class="alert alert-danger"> ${mes} </div>
                            </c:if>
                            <div class="card-body">
                                <!-- .table-responsive -->
                                <form action="admin/support-detail" method="post"> <!-- ẩn ID của request -->
                                    <div class="d-flex justify-content-between align-items-center"><h2
                                            class="card-title">Xử lý Support</h2>
                                        <!-- Nút bên phải chỉ hiện khi status = Processing -->
                                        <c:if
                                                test="${supportRequest.status == 'Processing'}">
                                            <button type="submit" class="btn btn-primary">Cập nhật</button>
                                        </c:if></div>
                                    <input type="hidden" name="requestId" value="${supportRequest.requestId}"/>
                                    <div class="form-group"><label for="processNote">Ghi chú xử lý</label>
                                        <textarea class="form-control" id="processNote" name="processNote" rows="4"
                                                  placeholder="Nhập ghi chú xử lý...">${fn:escapeXml(supportRequest.processNote)}</textarea>

                                    </div>
                                    <div class="form-group mt-3"><label for="status">Trạng thái</label>
                                        <select class="form-control" id="status" name="status">

                                            <option value="Processing"
                                                    <c:if test="${supportRequest.status eq 'Processing'}">selected</c:if>>
                                                Processing
                                            </option>
                                            <option value="Approved"
                                                    <c:if test="${supportRequest.status eq 'Approved'}">selected</c:if>>
                                                Approved
                                            </option>
                                            <option value="Rejected"
                                                    <c:if test="${supportRequest.status eq 'Rejected'}">selected</c:if>>
                                                Rejected
                                            </option>

                                        </select>
                                    </div>

                                </form>
                            </div><!-- /.card-body -->
                            <!-- .card-footer -->
                            <!-- /.card-footer -->
                        </div><!-- /.card -->
                    </div>
                </div>
            </div>


        </div>
    </main>
</div>
<script>
    var skin = localStorage.getItem('skin') || 'default';
    var disabledSkinStylesheet = document.querySelector('link[data-skin]:not([data-skin="' + skin + '"])');
    disabledSkinStylesheet.setAttribute('rel', '');
    disabledSkinStylesheet.setAttribute('disabled', true);
</script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- END BASE JS -->
<!-- BEGIN PLUGINS JS -->
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/pace/pace.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/stacked-menu/stacked-menu.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/sortablejs/Sortable.min.js"></script>
<!-- END PLUGINS JS -->
<!-- BEGIN THEME JS -->
<script src="${pageContext.request.contextPath}/assetAdmin/assets/javascript/theme.min.js"></script>
<!-- END THEME JS -->
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-116692175-1"></script>
<script>
    window.dataLayer = window.dataLayer || [];

    function gtag() {
        dataLayer.push(arguments);
    }

    gtag('js', new Date());
    gtag('config', 'UA-116692175-1');
</script>
</body>
</html>
