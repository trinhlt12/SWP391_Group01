<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="vi_VN"/>

<header id="header" class="header d-flex align-items-center sticky-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">

        <a href="${pageContext.request.contextPath}/home" class="logo d-flex align-items-center me-auto">
            <img src="${pageContext.request.contextPath}/image/Logo.png" alt="Logo" style="max-height: 40px; width: auto;">
            <h1 class="sitename">Em Bán Thẻ</h1>
        </a>

        <nav id="navmenu" class="navmenu">
            <ul>
                <li><a href="${pageContext.request.contextPath}/home#hero" class="active">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/service">Dịch Vụ</a></li>

                <c:if test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/ewallet">Ewallet</a></li>
                    <li><a href="${pageContext.request.contextPath}/user-history">Thống Kê</a></li>

                    <li class="dropdown">
                        <a href="#">
                            <img src="${pageContext.request.contextPath}/image/icons8-user-male-16.png" alt="User Icon" style="width:20px; height:20px; margin-right:5px;">

                            <span>${sessionScope.user.fullName} -
                                <fmt:formatNumber value="${sessionScope.user.balance}" pattern="#,###"/> đ
                            </span>

                            <i class="bi bi-chevron-down toggle-dropdown"></i>
                        </a>
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/userprofile">Thông tin cá nhân</a></li>
                            <li><a href="${pageContext.request.contextPath}/user-history">Lịch sử giao dịch</a></li>
                            <li><a href="${pageContext.request.contextPath}/changePassword">Đổi Mật Khẩu</a></li>
                            <li><a href="#">Email: ${sessionScope.user.email}</a></li>
                            <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
                        </ul>
                    </li>
                </c:if>

                <c:if test="${empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/login">Đăng nhập</a></li>
                </c:if>
            </ul>
            <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
        </nav>

    </div>
</header>