<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header id="header" class="header d-flex align-items-center sticky-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">

        <a href="home" class="logo d-flex align-items-center me-auto">
            <img src="image/Logo.png" alt="Logo">
            <h1 class="sitename">Em Bán Thẻ</h1>
        </a>

        <nav id="navmenu" class="navmenu">
            <ul>
                <li><a href="home#hero" class="active">Home</a></li>
                <li><a href="home#about">Dịch Vụ</a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li><a href="ewallet">Ewallet</a></li>
                    <li><a href="home#portfolio">Thống Kê</a></li>
                    <li><a href="home#team">Link Thanh Toán</a></li>
                    <li><a href="home#policy">Chính Sách</a></li>

                    <li class="dropdown">
                        <a href="#">
                            <img src="image/icons8-user-male-16.png" alt="User Icon" style="width:20px; height:20px; margin-right:5px;">
                            <span>${sessionScope.user.fullName} - ${sessionScope.user.balance} VND</span>
                            <i class="bi bi-chevron-down toggle-dropdown"></i>
                        </a>
                        <ul>
                            <li><a href="#">Thông tin cá nhân</a></li>
                            <li><a href="#">Đổi Mật Khẩu Đăng nhập</a></li>
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