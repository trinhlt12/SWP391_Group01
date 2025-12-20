<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 18/12/2025
  Time: 1:45 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap-icons/bootstrap-icons.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/aos/aos.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assetsHome/css/main.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetsHome/css/userProfile.css">
    <base href="${pageContext.request.contextPath}/">


    <style> /* Khung tổng thể */
    .support-section {
        background: #fff;
        border-radius: 8px;
        padding: 20px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    }

    /* Tiêu đề */
    .support-section h5 {
        font-weight: 600;
        color: #2c3e50;
        border-left: 4px solid #0d6efd;
        padding-left: 10px;
    }

    /* Form lọc */
    .support-section form {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 15px;
    }

    .support-section select {
        padding: 6px 12px;
        border-radius: 6px;
        border: 1px solid #ced4da;
    }

    .support-section button {
        padding: 6px 16px;
        border-radius: 6px;
    }

    /* Bảng */
    .support-section table {
        border-radius: 6px;
        overflow: hidden;
    }

    .support-section thead {
        background: #f8f9fa;
    }

    .support-section th {
        font-weight: 600;
        text-align: center;
    }

    .support-section td {
        vertical-align: middle;
    }

    /* Badge trạng thái */
    .badge {
        padding: 6px 10px;
        font-size: 0.85rem;
        border-radius: 20px;
    }

    .bg-warning {
        background-color: #ffc107 !important;
        color: #212529;
    }

    .bg-success {
        background-color: #28a745 !important;
    }

    .bg-secondary {
        background-color: #6c757d !important;
    }

    /* Hover row */
    .support-section tbody tr:hover {
        background-color: #f1f5f9;
        transition: background-color 0.2s ease-in-out;
    } </style>
</head>
<body class="index-page">
<jsp:include page="/header.jsp"/>
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
                                class="text-[#000000] font-medium">Support</span></li>
                    </ul>
                </nav>
                <div class="card border-0 shadow-sm">
                    <div class="card-body p-0">
                        <div class="row g-0">
                            <!-- Sidebar -->
                            <div class="col-lg-3 ">
                                <div class="p-4">
                                    <div class="nav flex-column nav-pills">
                                        <a class="nav-link " href="userprofile"><i class="fas fa-user me-2"></i>Thông
                                            Tin Cá Nhân</a>
                                        <a class="nav-link" href="changePassword"><i class="fas fa-lock me-2"></i>Đổi
                                            Mật Khẩu</a>
                                        <a class="nav-link active" href="listSupport"><i
                                                class="fas fa-credit-card me-2"></i>Support</a>

                                    </div>
                                </div>
                            </div>

                            <!-- Content Area -->
                            <div class="col-lg-9">

                                <div class="p-4">
                                    <!-- Personal Information -->
                                    <div class=" support-section mb-4">
                                        <h5 class="mb-4">Danh sách yêu cầu hỗ trợ</h5>
                                        <form method="get" action="listSupport"><select name="status">
                                            <option value="">--Tất cả--</option>
                                            <option value="Processing" ${selectedStatus == 'Processing' ? 'selected' : ''}>
                                                Processing
                                            </option>
                                            <option value="Approved" ${selectedStatus == 'Approved' ? 'selected' : ''}>
                                                Approved
                                            </option>
                                            <option value="Rejected" ${selectedStatus == 'Rejected' ? 'selected' : ''}>
                                                Rejected
                                            </option>
                                        </select>
                                            <button type="submit">Lọc</button>
                                        </form>
                                        <div class="table-responsive">
                                            <table class="table table-bordered table-hover align-middle">
                                                <thead class="table-light">
                                                <tr>
                                                    <th scope="col">ID</th>
                                                    <th scope="col">Tiêu đề</th>
                                                    <th scope="col">Nội dung</th>
                                                    <th scope="col">Trạng thái</th>
                                                    <th scope="col">Ngày gửi</th>
                                                    <th scope="col">Ghi chú xử lý</th>
                                                    <th scope="col">Ngày xử lý</th>
                                                </tr>
                                                </thead>
                                                <tbody><c:forEach var="req" items="${supportRequests}">
                                                    <tr>
                                                        <td>${req.requestId}</td>
                                                        <td class="fw-bold text-primary">${req.title}</td>
                                                        <td>${req.message}</td>
                                                        <td><span
                                                                class="badge <c:choose> <c:when test="${req.status == 'Processing'}">bg-warning</c:when>
                                                            <c:when test="${req.status == 'Approved'}">bg-success</c:when>
                                                            <c:when test="${req.status == 'Rejected'}">bg-secondary</c:when>
                                                            </c:choose>"> ${req.status} </span>
                                                        </td>
                                                        <td><fmt:formatDate value="${req.createdAt}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>

                                                        <td>${req.processNote}</td>

                                                        <td><fmt:formatDate value="${req.processedAt}"
                                                                            pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                                    </tr>
                                                </c:forEach></tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <c:set var="totalPages"
                                       value="${(totalRequests / pageSize) + (totalRequests % pageSize > 0 ? 1 : 0)}"/>

                                <nav aria-label="Page navigation">
                                    <ul class="pagination">
                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                <a class="page-link" href="listSupport?page=${i}">${i}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </nav>

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
