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
    <link href="https://fonts.googleapis.com/css2?family=Roboto:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900;1,100;1,300;1,400;1,500;1,700;1,900&family=Raleway:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">

</head>

<body class="index-page">

<header id="header" class="header d-flex align-items-center sticky-top">
    <div class="container-fluid container-xl position-relative d-flex align-items-center">

        <a href="home" class="logo d-flex align-items-center me-auto">

            <img src="${pageContext.request.contextPath}/image/Logo.png" alt="Logo">
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
                        <img src="${pageContext.request.contextPath}/image/icons8-user-male-16.png"
                             alt="User Icon"
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
                                <img src="${pageContext.request.contextPath}/${not empty s.imageUrl ? s.imageUrl : 'image/default-card.png'}"
                                     alt="${s.productName}"
                                     class="img-fluid"
                                     style="max-height: 100px; object-fit: contain;">
                            </div>

                            <a href="product-details?id=${s.productId}" class="stretched-link">
                                <h3>${s.productName}</h3>
                            </a>

                            <p>
                                <strong>Nhà mạng:</strong> ${s.providerName}<br/>
                                <strong>Loại thẻ:</strong> ${s.categoryName}<br/>
                                <strong>Mệnh giá:</strong>
                                <fmt:formatNumber value="${s.price}" type="currency" currencySymbol="đ"/><br/>

                                <span class="${s.quantity > 0 ? 'text-success' : 'text-danger'}">
                                        ${s.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                                </span>
                            </p>

                            <div class="mt-3 text-center" style="position: relative; z-index: 2;">
                                <c:choose>
                                    <%-- TRƯỜNG HỢP 1: Đã đăng nhập (Có session user) --%>
                                    <c:when test="${not empty sessionScope.user}">
                                        <c:if test="${s.quantity > 0}">
                                            <a href="payment?productId=${s.productId}" class="btn btn-primary rounded-pill px-4">
                                                Mua ngay <i class="bi bi-cart-check"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${s.quantity <= 0}">
                                            <button class="btn btn-secondary rounded-pill px-4" disabled>Hết hàng</button>
                                        </c:if>
                                    </c:when>

                                    <c:otherwise>
                                        <a href="login"
                                           onclick="return confirm('Bạn cần đăng nhập để thực hiện mua hàng. Chuyển đến trang đăng nhập?');"
                                           class="btn btn-outline-primary rounded-pill px-4">
                                            Mua ngay <i class="bi bi-box-arrow-in-right"></i>
                                        </a>
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
                                <a href="category-details.jsp?id=${c.categoryId}" class="stretched-link">
                                        ${c.categoryName}
                                </a>
                            </h4>
                            <!-- Nếu có mô tả category -->
                            <p>${c.description}</p>
                        </div>
                    </div>
                </c:forEach>

            </div>

        </div>

    </section><!-- /Featured Services Section -->

    <!-- Testimonials Section -->
    <section id="testimonials" class="testimonials section light-background" style="padding: 30px 0;">
        <div class="container section-title" data-aos="fade-up" style="margin-bottom: 20px;"> <span>Nhà cung cấp</span>
        <h2>Đối tác</h2>
        </div>

        <div class="container" data-aos="fade-up" data-aos-delay="100">

            <div class="swiper init-swiper" data-speed="600" data-delay="5000"
                 data-breakpoints="{ &quot;320&quot;: { &quot;slidesPerView&quot;: 2, &quot;spaceBetween&quot;: 20 }, &quot;768&quot;: { &quot;slidesPerView&quot;: 4, &quot;spaceBetween&quot;: 20 }, &quot;1200&quot;: { &quot;slidesPerView&quot;: 5, &quot;spaceBetween&quot;: 20 } }">

                <script type="application/json" class="swiper-config">
                    {
                        "loop": true,
                        "speed": 600,
                        "autoplay": {
                            "delay": 3000
                        },
                        "slidesPerView": "auto",
                        "pagination": {
                            "el": ".swiper-pagination",
                            "type": "bullets",
                            "clickable": true
                        },
                        "breakpoints": {
                            "320": {
                                "slidesPerView": 2,
                                "spaceBetween": 20
                            },
                            "768": {
                                "slidesPerView": 4,
                                "spaceBetween": 20
                            },
                            "1200": {
                                "slidesPerView": 5,
                                "spaceBetween": 30
                            }
                        }
                    }
                </script>

                <div class="row text-center">
                    <c:forEach var="p" items="${providerList}">
                        <div class="col">
                            <img src="${pageContext.request.contextPath}/${p.logoUrl}"
                                 style="max-width:100px;">
                            <p>${p.providerName}</p>
                        </div>
                    </c:forEach>
                </div>

                <div class="swiper-pagination"></div>
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