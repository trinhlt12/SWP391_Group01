<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 10/12/2025
  Time: 9:55 SA
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
                            <c:choose>
                                <c:when test="${sessionScope.actionType eq 'forgotPassword'}">
                                    <h4 class="text-center mb-3" style="color:#1a97f5; font-weight:bold;">
                                        Xác nhận mật khẩu
                                    </h4>
                                </c:when>
                                <c:when test="${sessionScope.actionType eq 'register'}">
                                    <h4 class="text-center mb-3" style="color:#1a97f5; font-weight:bold;">
                                        Xác nhận đăng ký
                                    </h4>
                                </c:when>
                            </c:choose>

                            <c:if test="${not empty message}">
                                <p class="text-center">
                                <div class="text-center" style="color:#1a97f5; font-weight:bold; ">
                                        ${message}
                                </div>
                                </p>
                            </c:if>

                            <form action="validateOtp" method="post">
                                <div class="mb-3"><label for="otpInput" class="form-label">Nhập OTP</label> <input
                                        type="text" name="otp" class="form-control" id="otpInput"
                                        aria-describedby="otpHelp" pattern="[0-9]*" inputmode="numeric" required>
                                    <div id="otpHelp" class="form-text">Chỉ nhập số (OTP gồm 6 chữ số).</div>
                                </div>
                                <button type="submit" class="btn btn-primary w-100 py-8 fs-4 mb-4 rounded-2">
                                    <c:choose>
                                        <c:when test="${sessionScope.actionType eq 'forgotPassword'}">
                                            Xác nhận OTP mật khẩu
                                        </c:when>
                                        <c:when test="${sessionScope.actionType eq 'register'}">
                                            Xác nhận OTP đăng ký
                                        </c:when>
                                        <c:otherwise>
                                            Nhập OTP
                                        </c:otherwise>
                                    </c:choose>
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
