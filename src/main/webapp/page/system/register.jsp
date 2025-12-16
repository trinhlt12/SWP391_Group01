<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Em Bán Thẻ - Đăng ký</title>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/image/Logo.png"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.min.css"/>
    <base href="${pageContext.request.contextPath}/">
</head>

<body>
<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6"
     data-sidebartype="full" data-sidebar-position="fixed" data-header-position="fixed">
    <div class="position-relative overflow-hidden text-bg-light min-vh-100 d-flex align-items-center justify-content-center">
        <div class="d-flex align-items-center justify-content-center w-100">
            <div class="row justify-content-center w-100">
                <div class="col-md-8 col-lg-6 col-xxl-3">
                    <div class="card mb-0">
                        <div class="card-body">
                            <a href="home" class="text-nowrap logo-img text-center d-block py-3 w-100">
                                <img src="image/Logo.png" style="width: 120px; height: auto;" alt="Logo">
                            </a>

                            <p class="text-center">Đăng ký tài khoản mới</p>
                            <c:if test="${not empty message}">
                                <div style="color:red; font-weight:bold; text-align:center;">
                                        ${message}
                                </div>
                            </c:if>

                            <form action="register" method="post">
                                <div class="mb-3">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" name="username" class="form-control" id="username" required>
                                </div>
                                <div class="mb-3">
                                    <label for="fullName" class="form-label">FullName</label>
                                    <input type="text" name="fullName" class="form-control" id="fullName" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email Address</label>
                                    <input type="email" name="email" class="form-control" id="email" required>
                                </div>
                                <div class="mb-3">
                                    <label for="phone" class="form-label">Phone Number</label>
                                    <input type="text" name="phone" class="form-control" id="phone" required>
                                </div>
                                <div class="mb-3">
                                    <label for="password" class="form-label">Password</label>
                                    <input type="password" name="password" class="form-control" id="password" required>
                                </div>
                                <div class="mb-3">
                                    <label for="confirmPassword" class="form-label">Confirm Password</label>
                                    <input type="password" name="confirmPassword" class="form-control"
                                           id="confirmPassword" required>
                                </div>

                                <button type="submit" class="btn btn-primary w-100 py-2 fs-5 mb-3 rounded-2">
                                    Đăng kí
                                </button>
                                <div class="d-flex align-items-center justify-content-center">
                                    <p class="mb-0 fw-bold">Already have an Account?</p>
                                    <a class="text-primary fw-bold ms-2" href="login"> Đăng nhập</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/assets/libs/jquery/dist/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/libs/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/iconify-icon@1.0.8/dist/iconify-icon.min.js"></script>
</body>
</html>
