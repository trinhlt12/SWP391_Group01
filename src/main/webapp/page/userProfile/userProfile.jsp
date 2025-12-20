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

    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetsHome/css/userProfile.css">
    <base href="${pageContext.request.contextPath}/">

</head>
<body class="index-page">

<jsp:include page="/header.jsp" />

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
                                class="text-[#000000] font-medium">Tài khoản</span></li>
                    </ul>
                </nav>
                <div class="card border-0 shadow-sm">
                    <div class="card-body p-0">
                        <div class="row g-0">
                            <!-- Sidebar -->
                            <div class="col-lg-3 border-end">
                                <div class="p-4">
                                    <div class="nav flex-column nav-pills">
                                        <a class="nav-link active" href="userprofile"><i class="fas fa-user me-2"></i>Thông
                                            Tin Cá Nhân</a>
                                        <a class="nav-link" href="changePassword"><i class="fas fa-lock me-2"></i>Đổi
                                            Mật Khẩu</a>
                                        <a class="nav-link" href="listSupport"><i class="fas fa-credit-card me-2"></i>Support</a>

                                    </div>
                                </div>
                            </div>

                            <!-- Content Area -->
                            <div class="col-lg-9">
                                <c:if test="${not empty success}">
                                    <div style="color:blue; font-weight:bold;">
                                            ${success}
                                    </div>
                                    <c:remove var="success" scope="session"/>
                                </c:if>
                                <c:if test="${not empty error}">
                                    <div style="color:red; font-weight:bold;">
                                            ${error}
                                    </div>
                                </c:if>
                                <div class="p-4">
                                    <!-- Personal Information -->
                                    <div class="mb-4">
                                        <h5 class="mb-4">Thông tin cá nhân</h5>
<%--                                        <label class="form-label">Username:</label>--%>
<%--                                        <span class="fw-bold text-primary"> ${sessionScope.user.username} </span>--%>
                                        <form action="userprofile" method="post" id="profileForm">
                                            <div class="position-absolute top-0 end-0 p-3">
                                                <!-- Nút Edit -->
                                                <button type="button" class="btn btn-light" id="editBtn">
                                                    <i class="fas fa-edit me-2"></i>Edit Cover
                                                </button>
                                                <!-- Nút Save (ẩn mặc định) -->
                                                <button type="submit" class="btn btn-primary d-none" id="saveBtn">
                                                    Save
                                                </button>
                                                <!-- Nút Cancel (ẩn mặc định) -->
                                                <button type="button" class="btn btn-secondary d-none" id="cancelBtn">
                                                    Cancel
                                                </button>
                                            </div>
                                            <div class="row g-3">

                                                <div class="col-md-6">
                                                    <label class="form-label">Fullname</label>
                                                    <input type="text" class="form-control" id="fullNameInput"
                                                           name="fullName"
                                                           value="${sessionScope.user.fullName}" readonly>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Phone <span class="text-muted">(tùy chọn)</span></label>
                                                    <input type="tel" class="form-control" id="phoneInput" name="phone"
                                                           value="${sessionScope.user.phone != null ? sessionScope.user.phone : ''}"
                                                           placeholder="Để trống nếu không muốn cập nhật"
                                                           readonly>
                                                </div>
                                                <div class="col-md-6">
                                                    <label class="form-label">Email</label>
                                                    <input type="tel" class="form-control" id="emailInput" name="email"
                                                           value="${sessionScope.user.email}" readonly>
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

<script>
    const editBtn = document.getElementById("editBtn");
    const saveBtn = document.getElementById("saveBtn");
    const cancelBtn = document.getElementById("cancelBtn");

    // Chỉ lấy 3 input cần sửa
    const editableInputs = [
        document.getElementById("fullNameInput"),
        document.getElementById("phoneInput"),
        document.getElementById("emailInput")
    ];

    editBtn.addEventListener("click", function () {
        editableInputs.forEach(input => input.removeAttribute("readonly"));
        saveBtn.classList.remove("d-none");
        cancelBtn.classList.remove("d-none");
        editBtn.classList.add("d-none");
    });

    cancelBtn.addEventListener("click", function () {
        // Khôi phục readonly
        editableInputs.forEach(input => input.setAttribute("readonly", true));
        // Ẩn Save/Cancel, hiện lại Edit
        saveBtn.classList.add("d-none");
        cancelBtn.classList.add("d-none");
        editBtn.classList.remove("d-none");
        // Reset giá trị input về dữ liệu gốc
        editableInputs.forEach(input => input.value = input.defaultValue);
    });

</script>
</body>


</html>
