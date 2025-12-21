<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>Em Ban The</title>
    <meta name="description" content="">
    <meta name="keywords" content="">

    <link href="image/Logo.png" rel="icon">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&family=Raleway:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
          rel="stylesheet">

    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">
    <style>
        .partner-item {
            padding: 15px;
            background: #fff;
            border-radius: 10px;
            transition: all 0.3s ease;
        }

        .partner-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }

        .partner-logo {
            max-height: 60px;
            object-fit: contain;
        }

        .partner-name {
            font-size: 14px;
            font-weight: 500;
            color: #333;
        }
        /* Override background cho product icons */
        .service-item .icon {
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%) !important;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            border-radius: 15px;
            padding: 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 140px;
            transition: all 0.3s ease;
        }

        .service-item:hover .icon {
            transform: scale(1.05);
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }

        .service-item .icon img {
            max-height: 90px;
            max-width: 90px;
            object-fit: contain;
            filter: drop-shadow(0 2px 4px rgba(0,0,0,0.1)); /* Thêm shadow cho logo */
        }

    </style>
</head>

<body class="index-page">

<jsp:include page="header.jsp"/>

<main class="main">

    <!-- Hero Section -->
    <section id="hero" class="hero section">

        <div class="container">
            <div class="row gy-4">
                <div class="col-lg-6 order-2 order-lg-1 d-flex flex-column justify-content-center" data-aos="fade-up">
                    <h1>Dịch Vụ Bán Thẻ</h1>
                    <p>Trang thẻ uy tín</p>
                    <div class="d-flex">
                        <a href="${pageContext.request.contextPath}/service" class="btn-get-started">Dịch Vụ</a>
                    </div>
                </div>
                <div class="col-lg-6 order-1 order-lg-2 hero-img" data-aos="zoom-out" data-aos-delay="100">
                    <img src="image/banner.png" class="img-fluid animated" alt="">
                </div>
            </div>
        </div>

    </section>
    <section id="services" class="services section light-background">

        <div class="container section-title" data-aos="fade-up">
            <span>Sản phẩm</span>
            <h2>Danh sách sản phẩm mới</h2>
            <p>Các loại thẻ đang được bán chạy nhất tại hệ thống</p>
        </div>

        <div class="container">
            <div class="row gy-4">

                <c:if test="${empty productList}">
                    <div class="col-12 text-center">
                        <p>Hiện chưa có sản phẩm nào được bày bán.</p>
                    </div>
                </c:if>

                <c:forEach var="s" items="${productList}">
                    <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="100">
                        <div class="service-item position-relative">

                            <div class="icon">
                                <c:choose>
                                   <%-- &lt;%&ndash; Nếu có imageUrl trong database &ndash;%&gt;
                                    <c:when test="${not empty s.imageUrl}">
                                        <img src="${pageContext.request.contextPath}/${s.imageUrl}"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>--%>

                                    <%-- Fallback: Dùng logo theo providerName --%>
                                    <c:when test="${fn:containsIgnoreCase(s.productName, 'Viettel')}">
                                        <img src="${pageContext.request.contextPath}/image/viettel1.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>
                                    <c:when test="${fn:containsIgnoreCase(s.productName, 'Mobifone')}">
                                        <img src="${pageContext.request.contextPath}/image/mobi.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>
                                    <c:when test="${fn:containsIgnoreCase(s.productName, 'Vinaphone')}">
                                        <img src="${pageContext.request.contextPath}/image/vina.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>
                                    <c:when test="${fn:containsIgnoreCase(s.productName, 'Vietnamobile') or fn:containsIgnoreCase(s.productName, 'Vietnam Mobile')}">
                                        <img src="${pageContext.request.contextPath}/image/vietnammobile.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>
                                    <c:when test="${fn:containsIgnoreCase(s.productName, 'Garena')}">
                                        <img src="${pageContext.request.contextPath}/image/garena.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>
                                    <c:when test="${fn:containsIgnoreCase(s.productName, 'Steam')}">
                                        <img src="${pageContext.request.contextPath}/image/Steam_logo.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:when>

                                    <%-- Default fallback --%>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/image/default-card.png"
                                             alt="${s.productName}"
                                             class="img-fluid"
                                             style="max-height: 100px; object-fit: contain;">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <h3 style="cursor: default;">${s.productName}</h3>

                            <div class="mt-2 mb-3">
                                <p class="mb-2">
                                    <strong style="font-size: 1.3rem; color: #dc3545;">
                                        <fmt:formatNumber value="${s.price}" type="number" groupingUsed="true"/> đ
                                    </strong>
                                </p>
                                <span class="${s.quantity > 0 ? 'badge bg-success' : 'badge bg-secondary'}">
                                        ${s.quantity > 0 ? 'Sẵn hàng' : 'Tạm hết'}
                                </span>
                            </div>

                            <div class="text-center" style="position: relative; z-index: 2;">

                                <c:choose>
                                    <%-- ƯU TIÊN 1: Kiểm tra Hết hàng trước --%>
                                    <c:when test="${s.quantity <= 0}">
                                        <button class="btn btn-secondary rounded-pill px-4" disabled style="cursor: not-allowed;">
                                            Hết hàng <i class="bi bi-x-circle"></i>
                                        </button>
                                    </c:when>

                                    <%-- ƯU TIÊN 2: Còn hàng -> Kiểm tra xem là Khách hay User --%>
                                    <c:otherwise>
                                        <c:choose>

                                            <%-- USER ĐÃ LOGIN: Hiện nút MUA --%>
                                            <c:when test="${not empty sessionScope.user}">
                                                <form action="${pageContext.request.contextPath}/review-order" method="post">
                                                    <input type="hidden" name="productId" value="${s.productId}">
                                                    <input type="hidden" name="quantity" value="1">
                                                    <button type="submit" class="btn btn-primary rounded-pill px-4 shadow-sm">
                                                        Mua ngay <i class="bi bi-cart-check"></i>
                                                    </button>
                                                </form>
                                            </c:when>

                                            <%-- KHÁCH VÃNG LAI: Hiện nút ĐĂNG NHẬP --%>
                                            <c:otherwise>
                                                <a href="login" class="btn btn-outline-primary rounded-pill px-4">
                                                    Đăng nhập để mua <i class="bi bi-box-arrow-in-right"></i>
                                                </a>
                                            </c:otherwise>

                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>

                            </div>

                        </div>
                    </div>
                </c:forEach>


            </div>
        </div>

    </section>
    <!-- Featured Services Section -->
    <section id="featured-services" class="featured-services section">

        <div class="container">

            <div class="row gy-4">

                <c:forEach var="c" items="${categoriesList}">
                    <div class="col-lg-4 d-flex" data-aos="fade-up" data-aos-delay="100">
                        <div class="service-item position-relative">

                            <h4>
                                <a href="${pageContext.request.contextPath}/service" class="stretched-link">
                                        ${c.categoryName}
                                </a>
                            </h4>
                            <!-- Nếu có mô tả category ?id=${c.categoryId} -->
                            <p>${c.description}</p>
                        </div>
                    </div>
                </c:forEach>

            </div>

        </div>

    </section><!-- /Featured Services Section -->

    <section id="partners" class="partners section light-background">
        <div class="container section-title" data-aos="fade-up">
            <span>Nhà cung cấp</span>
            <h2>Đối tác</h2>
            <p>Các nhà cung cấp uy tín đang hợp tác cùng hệ thống</p>
        </div>

        <div class="container">
            <div class="row justify-content-center align-items-center g-4">

                <c:forEach var="p" items="${providerList}">
                    <div class="col-6 col-sm-4 col-md-3 col-lg-2 text-center">
                        <div class="partner-item">
                            <img src="${pageContext.request.contextPath}/${p.logoUrl}"
                                 alt="${p.providerName}"
                                 class="img-fluid partner-logo">
                            <p class="mt-2 mb-0 partner-name">
                                    ${p.providerName}
                            </p>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
    </section>

    <!-- Team Section -->
    <section id="team" class="team section">

        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
            <span>SWP391_Group01</span>
            <h2>Team Member</h2>
            <p></p>
        </div><!-- End Section Title -->

        <div class="container">

            <div class="row gy-5">

                <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="100">
                    <div class="member">
                        <div class="pic"><img src="assets/img/team/team-1.jpg" class="img-fluid" alt=""></div>
                        <div class="member-info">
                            <h4>Do Sy Hung</h4>
                            <span>Chief</span>

                        </div>
                    </div>
                </div><!-- End Team Member -->

                <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="200">
                    <div class="member">
                        <div class="pic"><img src="assets/img/team/team-2.jpg" class="img-fluid" alt=""></div>
                        <div class="member-info">
                            <h4>Lam Trinh</h4>
                            <span>Product Manager</span>

                        </div>
                    </div>
                </div><!-- End Team Member -->
                <div class="col-lg-4 col-md-6" data-aos="fade-up" data-aos-delay="100">
                    <div class="member">
                        <div class="pic"><img src="assets/img/team/team-1.jpg" class="img-fluid" alt=""></div>
                        <div class="member-info">
                            <h4>Tran Hoang</h4>
                            <span>Chief</span>

                        </div>
                    </div>
                </div>
            </div>

        </div>

    </section><!-- /Team Section -->


</main>
<footer id="footer" class="footer">

    <!-- Newsletter -->

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
                        <strong>Hotline:</strong> <span>0915201641</span>
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
                    <li><i class="bi bi-chevron-right"></i> <a href="${pageContext.request.contextPath}/service">Thẻ
                        điện thoại</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="${pageContext.request.contextPath}/service">Thẻ
                        game</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="${pageContext.request.contextPath}/service">Nạp
                        điện thoại</a></li>
                    <li><i class="bi bi-chevron-right"></i> <a href="${pageContext.request.contextPath}/ewallet">Ví điện
                        tử</a></li>
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

<!-- Scroll Top -->
<a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>

<!-- Preloader -->
<div id="preloader"></div>

<!-- Vendor JS Files -->
<script src="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assetsHome/vendor/aos/aos.js"></script>
<script src="${pageContext.request.contextPath}/assetsHome/vendor/glightbox/js/glightbox.min.js"></script>
<script src="${pageContext.request.contextPath}/assetsHome/vendor/swiper/swiper-bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assetsHome/js/main.js"></script>


<!-- Main JS File -->
<script src="assetsHome/js/main.js"></script>

</body>

</html>