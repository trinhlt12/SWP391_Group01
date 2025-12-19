<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 10/12/2025
  Time: 9:51 SA
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">

<head>
    <meta charset="UTF-8">
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
                            <p class="text-center">Nhập mật khẩu mới</p>
                            <c:if test="${not empty message}">
                                <div style="color:red; font-weight:bold;">
                                        ${message}
                                </div>
                            </c:if>
                            <span>Tài khoản đăng nhập của bạn là: ${sessionScope.user.username}</span>
                            <form action="googleConfirm" method="post">
                                <input type="hidden" name="email" value="${sessionScope.email}"/>
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">Mật khẩu mới</label>
                                    <input type="password" name="password" class="form-control" id="exampleInputEmail1"
                                           aria-describedby="emailHelp">
                                </div>
                                <div class="mb-3">
                                    <label for="exampleInputEmail1" class="form-label">Nhập lại mật khẩu</label>
                                    <input type="password" name="confPassword" class="form-control"
                                           id="exampleInputEmail1"
                                           aria-describedby="emailHelp">
                                </div>
                                <button type="submit" class="btn btn-primary w-100 py-8 fs-4 mb-4 rounded-2">
                                    Tao Mat Khau
                                </button>

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
