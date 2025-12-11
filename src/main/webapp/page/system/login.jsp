<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 09/12/2025
  Time: 10:52 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Em Bán Thẻ</title>
    <link rel="shortcut icon" type="image/png" href="${pageContext.request.contextPath}/image/Logo.png"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/styles.min.css"/>
    <base href="${pageContext.request.contextPath}/">
</head>

<body>
<!--  Body Wrapper -->
<div class="page-wrapper" id="main-wrapper" data-layout="vertical" data-navbarbg="skin6" data-sidebartype="full"
     data-sidebar-position="fixed" data-header-position="fixed">
    <div
            class="position-relative overflow-hidden text-bg-light min-vh-100 d-flex align-items-center justify-content-center">
        <div class="d-flex align-items-center justify-content-center w-100">
            <div class="row justify-content-center w-100">
                <div class="col-md-8 col-lg-6 col-xxl-3">
                    <div class="card mb-0">
                        <div class="card-body">
                            <a href="" class="text-nowrap logo-img text-center d-block py-3 w-100">
                                <img src="image/Logo.png" style="width: 120px; height: auto;" alt="">
                            </a>
                            <c:if test="${not empty message}">
                                <div style="color:red; font-weight:bold;">
                                        ${message}
                                </div>
                            </c:if>
                            <form action="login" method="post">
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">Email</label>
                                    <input type="email" name="email" class="form-control" id="exampleInputEmail1"
                                           aria-describedby="emailHelp">
                                </div>
                                <div class="mb-4">
                                    <label for="exampleInputPassword1" class="form-label">Mật khẩu</label>
                                    <input type="password" name="password" class="form-control"
                                           id="exampleInputPassword1">
                                </div>
                                <div class="d-flex align-items-center justify-content-between mb-4">
                                    <div class="form-check">
                                        <input class="form-check-input primary" type="checkbox" value=""
                                               id="flexCheckChecked" checked>
                                        <label class="form-check-label text-dark" for="flexCheckChecked">
                                            Remeber this Device
                                        </label>
                                    </div>
                                    <a class="text-primary fw-bold" href="forgotPassword">Forgot Password ?</a>
                                </div>
                                <button type="submit" class="btn btn-primary w-100 py-8 fs-4 mb-4 rounded-2">Đăng nhập
                                </button>
                                <div class="d-flex align-items-center my-4">
                                    <hr class="flex-grow-1 bg-secondary">
                                    <span class="px-3 text-muted fw-bold">HOẶC</span>
                                    <hr class="flex-grow-1 bg-secondary">
                                </div>
                                <div class="d-grid">
                                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/SWP391_Group01/login&response_type=code&client_id=866767645826-0drlmutip5cpi5ap5fjn6uus7mfktltp.apps.googleusercontent.com&approval_prompt=force"
                                       class="btn btn-outline-primary w-100 py-8 fs-4 rounded-2 d-flex align-items-center justify-content-center gap-3">
                                        <img src="https://www.google.com/favicon.ico" width="20" height="20"
                                             alt="Google">
                                        <span>Đăng nhập bằng Google</span>
                                    </a>
                                </div>
                                <div class="d-flex align-items-center justify-content-center">
                                    <p class="fs-4 mb-0 fw-bold">Chưa có tài khoản?</p>
                                    <a class="text-primary fw-bold ms-2" href="register">Tạo tài khoản mới</a>
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
<!-- solar icons -->
<script src="https://cdn.jsdelivr.net/npm/iconify-icon@1.0.8/dist/iconify-icon.min.js"></script>
</body>

</html>
