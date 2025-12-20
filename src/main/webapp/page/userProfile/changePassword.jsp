<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 11/12/2025
  Time: 9:30 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Em Ban The</title>

    <meta name="description" content="">
    <meta name="keywords" content="">

    <!-- Favicons -->
    <link href="image/Logo.png" rel="icon">


    <!-- Fonts -->
    <link href="https://fonts.googleapis.com" rel="preconnect">
    <link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&family=Raleway:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
          rel="stylesheet">

    <base href="${pageContext.request.contextPath}/">
    <link href="assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="assetsHome/css/main.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="assetsHome/css/userProfile.css">


</head>
<body>
<header id="header" class="header d-flex align-items-center sticky-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">

        <a href="home" class="logo d-flex align-items-center me-auto">

            <img src="image/Logo.png" alt="Logo">
            <h1 class="sitename">Em Bán Thẻ</h1>
        </a>

        <nav id="navmenu" class="navmenu">

            <ul>

                <li><a href="#hero" class="active">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/service">Dịch Vụ</a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/ewallet">Ewallet</a></li>
                    <li><a href="#portfolio">Thống Kê</a></li>
                    <li><a href="#team">Link Thanh Toán</a></li>
                    <li><a href="sendSupport">Hỗ Trợ</a></li>
                    <li class="dropdown">
                        <img src="image/icons8-user-male-16.png" alt="User Icon"
                             style="width:20px; height:20px; margin-right:5px;">

                        <span>${sessionScope.user.fullName} - ${sessionScope.user.balance} VND</span>
                        <i class="bi bi-chevron-down toggle-dropdown"></i>
                        <ul>
                            <li><a href="userprofile">Thông tin cá nhân</a></li>
                            <li><a href="changePassword">Đổi Mật Khẩu Đăng nhập</a></li>
                            <li><a href="#">Email: ${sessionScope.user.email}</a></li>
                            <li><a href="logout">Đăng xuất</a></li>
                        </ul>
                    </li>
                </c:if>
                <!-- Nếu chưa đăng nhập -->
                <c:if test="${empty sessionScope.user}">
                    <li><a href="login">Đăng nhập</a></li>
                </c:if>
            </ul>
            <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
        </nav>

    </div>
</header>
<div class="bg-light">
    <div class="container py-5">
        <div class="row">
            <!-- Profile Header -->
            <div class="col-12 mb-4 mt-4">

                <div class="text-center">
                    <div class="position-relative d-inline-block mb-4 mt-4">
                        <img src="image/icons8-profile-96.png" class="rounded-circle profile-pic" alt="Profile Picture">

                    </div>

                    <h3 class="mt-3 mb-1"> ${sessionScope.user.fullName}
                    </h3>
                    <c:if test="${sessionScope.user.status == 'ACTIVE'}">
                        <p class="text-success mb-0">Hoạt Động</p>
                    </c:if>
                    <label class="form-label">Ngày mở tài khoản: <fmt:formatDate value="${sessionScope.user.createdAt}"
                                                                                 pattern="dd/MM/yyyy"/></label>

                </div>
            </div>

            <!-- Main Content -->
            <div class="col-12">
                <nav class="mb-[17px] navmenu">
                    <ul class="flex items-center space-x-2 text-base ">
                        <li><a href="home" class="active">Home</a></li>
                        <li class="flex items-center"><span class=" text-[#000000]">&gt;</span><span
                                class="text-[#000000] font-medium">Đổi Mật Khẩu</span></li>
                    </ul>
                </nav>
                <div class="card border-0 shadow-sm">
                    <div class="card-body p-0">
                        <div class="row g-0">
                            <!-- Sidebar -->
                            <div class="col-lg-3 border-end">
                                <div class="p-4">
                                    <div class="nav flex-column nav-pills">
                                        <a class="nav-link " href="userprofile"><i class="fas fa-user me-2"></i>Thông
                                            Tin Cá Nhân</a>
                                        <a class="nav-link active" href="changePassword"><i
                                                class="fas fa-lock me-2"></i>Đổi Mật Khẩu</a>
                                        <a class="nav-link" href="listSupport"><i class="fas fa-credit-card me-2"></i>Support</a>
                                        <a class="nav-link" href="#"><i class="fas fa-chart-line me-2"></i>Activity</a>
                                    </div>
                                </div>
                            </div>

                            <!-- Content Area -->
                            <div class="col-lg-9">
                                <div class="p-4">
                                    <!-- Personal Information -->
                                    <div class="mb-4">
                                        <h5 class="mb-4">Thay Đổi Mật Khẩu</h5>
                                        <c:if test="${not empty mess }">
                                            <div style="color:red; font-weight:bold;">
                                                    ${mess}
                                            </div>
                                        </c:if>
                                        <c:if test="${not empty success }">
                                            <div style="color:yellowgreen; font-weight:bold;">
                                                    ${success}
                                            </div>
                                        </c:if>
                                        <form action="changePassword" method="post">

                                            <div class="row g-3 flex-column">
                                                <div class="col-md-6">
                                                    <label class="form-label">Mật Khẩu Cũ</label>
                                                    <input type="password" class="form-control"
                                                           name="oldPassword" required>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Mật Khẩu Mới</label>
                                                    <input type="password" class="form-control"
                                                           name="newPassword" required>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Xác Nhận Mật Khẩu</label>
                                                    <input type="password" class="form-control"
                                                           name="confirmPassword" required>
                                                </div>
                                                <div class="mt-3">
                                                    <button type="submit" class="btn btn-success">Lưu thay đổi</button>
                                                    <button type="reset" class="btn btn-secondary">Hủy</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer id="footer" class="footer">


    <!-- Footer Top -->
    <div class="container footer-top">
        <div class="row gy-4">

            <!-- About -->
            <div class="col-lg-4 col-md-6 footer-about">
                <a href="index.html" class="d-flex align-items-center">
                    <span class="sitename">EmBanThe</span>
                </a>
                <div class="footer-contact pt-3">
                    <p>Nền tảng mua thẻ điện thoại & thẻ game online</p>
                    <p>Uy tín – Nhanh chóng – An toàn</p>
                    <p class="mt-3">
                        <strong>Hotline:</strong> <span>0900 123 456</span>
                    </p>
                    <p>
                        <strong>Email:</strong> <span>support@embanthe.vn</span>
                    </p>
                </div>
            </div>

            <!-- Useful Links -->
            <div class="col-lg-2 col-md-3 footer-links">
                <h4>Liên kết</h4>
                <ul>
                    <li><i class="bi bi-chevron-right"></i> <a href="/">Trang chủ</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="/about">Giới thiệu</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="/cards">Mua thẻ</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="/contact">Liên hệ</a></li>
                </ul>
            </div>

            <!-- Services -->
            <div class="col-lg-2 col-md-3 footer-links">
                <h4>Dịch vụ</h4>
                <ul>
                    <li><i class="bi bi-chevron-right"></i> <a href="#">Thẻ điện thoại</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="#">Thẻ game</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="#">Nạp điện thoại</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="#">Ví điện tử</a></li>
                </ul>
            </div>

            <!-- Social -->
            <div class="col-lg-4 col-md-12">
                <h4>Kết nối với chúng tôi</h4>
                <p>
                    Theo dõi EmBanThe để cập nhật khuyến mãi và tin tức mới nhất.
                </p>
                <div class="social-links d-flex">
                    <a href="#"><i class="bi bi-facebook"></i></a>
                    <a href="#"><i class="bi bi-instagram"></i></a>
                    <a href="#"><i class="bi bi-twitter-x"></i></a>
                    <a href="#"><i class="bi bi-linkedin"></i></a>
                </div>
            </div>

        </div>
    </div>

    <!-- Copyright -->
    <div class="container copyright text-center mt-4">
        <p>
            © <span>2025</span>
            <strong class="px-1 sitename">EmBanThe</strong>
            <span>– All Rights Reserved</span>
        </p>
        <div class="credits">
            Thiết kế & phát triển bởi <strong>EmBanThe Team</strong>
        </div>
    </div>

</footer>
</body>
</html>
