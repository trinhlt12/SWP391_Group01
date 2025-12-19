<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 18/12/2025
  Time: 8:50 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


    <link href="assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="assetsHome/css/main.css" rel="stylesheet">
    <base href="${pageContext.request.contextPath}/">
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
                    <li><a href="#policy">Chính Sách</a></li>
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
<main class="main">
    <section id="contact" class="contact section">

        <!-- Section Title -->
        <div class="container section-title" data-aos="fade-up">
            <span>Support</span>
            <h2>Support</h2>
            <p>Hỗ Trợ Khánh Hàng</p>
            <c:if test="${not empty msg}"> <div class="alert alert-info">${msg}</div> </c:if>
        </div><!-- End Section Title -->

        <div class="container" data-aos="fade-up" data-aos-delay="100">

            <div class="row gy-4">

                <div class="col-lg-5">

                    <div class="info-wrap">
                        <div class="info-item d-flex" data-aos="fade-up" data-aos-delay="200">
                            <i class="bi bi-geo-alt flex-shrink-0"></i>
                            <div>
                                <h3>Địa Chỉ</h3>
                                <p>A108 Adam Street, New York, NY 535022</p>
                            </div>
                        </div><!-- End Info Item -->

                        <div class="info-item d-flex" data-aos="fade-up" data-aos-delay="300">
                            <i class="bi bi-telephone flex-shrink-0"></i>
                            <div>
                                <h3>Số Điện Thoại</h3>
                                <p>+1 5589 55488 55</p>
                            </div>
                        </div><!-- End Info Item -->

                        <div class="info-item d-flex" data-aos="fade-up" data-aos-delay="400">
                            <i class="bi bi-envelope flex-shrink-0"></i>
                            <div>
                                <h3>Email</h3>
                                <p>info@example.com</p>
                            </div>
                        </div><!-- End Info Item -->

                        <iframe src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d48389.78314118045!2d-74.006138!3d40.710059!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x89c25a22a3bda30d%3A0xb89d1fe6bc499443!2sDowntown%20Conference%20Center!5e0!3m2!1sen!2sus!4v1676961268712!5m2!1sen!2sus"
                                frameborder="0" style="border:0; width: 100%; height: 270px;" allowfullscreen=""
                                loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                    </div>
                </div>

                <div class="col-lg-7">
                    <form action="sendSupport" method="post" class="php-email-form"
                          data-aos-delay="200">
                        <div class="row gy-4">
                            <input type="hidden" name="userId" value="${sessionScope.user.userId}" />
                            <div class="col-md-6">
                                <label for="name-field" class="pb-2">Tên</label>
                                <input type="text" id="name-field" class="form-control" required="">
                            </div>


                            <div class="col-md-12">
                                <label for="subject-field" class="pb-2">Chủ Đề</label>
                                <input type="text" class="form-control" name="title" id="subject-field" required="">
                            </div>

                            <div class="col-md-12">
                                <label for="message-field" class="pb-2">Nội Dung</label>
                                <textarea class="form-control" name="message" rows="10" id="message-field"
                                          required=""></textarea>
                            </div>

                            <div class="col-md-12 text-center">
                                <div class="loading">Loading</div>
                                <div class="error-message"></div>
                                <div class="sent-message">Your message has been sent. Thank you!</div>
                                <button type="submit">Gửi</button>
                            </div>

                        </div>
                    </form>
                </div><!-- End Contact Form -->

            </div>

        </div>

    </section><!-- /Contact Section -->
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
<a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>

<!-- Preloader -->
<div id="preloader"></div>

<!-- Vendor JS Files -->
<script src="assetsHome/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="assetsHome/vendor/aos/aos.js"></script>
<script src="assetsHome/vendor/glightbox/js/glightbox.min.js"></script>
<script src="assetsHome/vendor/purecounter/purecounter_vanilla.js"></script>
<script src="assetsHome/vendor/imagesloaded/imagesloaded.pkgd.min.js"></script>
<script src="assetsHome/vendor/isotope-layout/isotope.pkgd.min.js"></script>
<script src="assetsHome/vendor/swiper/swiper-bundle.min.js"></script>

<!-- Main JS File -->
<script src="assetsHome/js/main.js"></script>
</body>
</html>
