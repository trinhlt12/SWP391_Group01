<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kết Quả Giao Dịch</title>
    <!-- Link CSS của bạn -->
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .result-box {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            text-align: center;
        }
        .icon-result { font-size: 80px; margin-bottom: 20px; }
    </style>
</head>
<body>

<div class="container">
    <div class="result-box">

        <c:choose>
            <c:when test="${statusAlert == 'success'}">
                <div class="icon-result">✅</div>
                <h2 class="text-success">Thành Công!</h2>
            </c:when>
            <c:when test="${statusAlert == 'info'}">
                <div class="icon-result">ℹ️</div>
                <h2 class="text-info">Thông Báo</h2>
            </c:when>
            <c:otherwise>
                <div class="icon-result">❌</div>
                <h2 class="text-danger">Thất Bại</h2>
            </c:otherwise>
        </c:choose>

        <h4 class="mt-3">${message}</h4>

        <c:if test="${not empty amount && amount > 0}">
            <p class="mt-3">Số tiền: <strong><fmt:formatNumber value="${amount}" type="currency" currencySymbol="₫"/></strong></p>
        </c:if>

        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/ewallet" class="btn btn-primary">Về ví của tôi</a>
        </div>
    </div>
</div>

</body>
</html>