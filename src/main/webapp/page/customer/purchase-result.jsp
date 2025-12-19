<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả giao dịch</title>
    <link href="${pageContext.request.contextPath}/assetsHome/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #e5e7eb; font-family: 'Inter', sans-serif; height: 100vh; display: flex; align-items: center; justify-content: center; margin: 0; }
        .result-popup { background: white; width: 400px; border-radius: 20px; padding: 30px; text-align: center; box-shadow: 0 10px 25px rgba(0,0,0,0.1); }

        /* Icon styles */
        .icon-wrapper { width: 80px; height: 80px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: 0 auto 20px; }
        .icon-success { background: #dbeafe; color: #3b82f6; } /* Xanh dương */
        .icon-failed { background: #fee2e2; color: #ef4444; } /* Đỏ */
        .status-icon { font-size: 40px; }

        .title { font-size: 1.25rem; font-weight: 700; color: #111827; margin-bottom: 5px; }
        .message { color: #6b7280; margin-bottom: 25px; }

        /* Info Row */
        .info-row { display: flex; justify-content: space-between; padding: 12px 0; border-bottom: 1px solid #f3f4f6; font-size: 0.9rem; }
        .info-label { color: #6b7280; }
        .info-value { font-weight: 600; color: #374151; }

        /* Buttons */
        .btn-action { display: block; width: 100%; padding: 12px; border-radius: 10px; text-decoration: none; font-weight: 600; transition: 0.3s; margin-top: 20px; border: none; }
        .btn-success-action { background: #059669; color: white; }
        .btn-success-action:hover { background: #10B981; color: white; }
        .btn-failed-action { background: #6b7280; color: white; }
        .btn-failed-action:hover { background: #4b5563; color: white; }
    </style>
</head>
<body>

<div class="result-popup">

    <c:choose>
        <%-- TRƯỜNG HỢP THÀNH CÔNG --%>
        <c:when test="${status == 'SUCCESS'}">
            <div class="icon-wrapper icon-success">
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-check-lg" viewBox="0 0 16 16">
                    <path d="M12.736 3.97a.733.733 0 0 1 1.047 0c.286.289.29.756.01 1.05L7.88 12.01a.733.733 0 0 1-1.065.02L3.217 8.384a.757.757 0 0 1 0-1.06.733.733 0 0 1 1.047 0l3.052 3.093 5.4-6.425a.247.247 0 0 1 .02-.022Z"/>
                </svg>
            </div>
            <div class="title">Thanh toán thành công</div>
            <div class="message" style="color: #3b82f6; font-weight: 600; font-size: 1.1rem;">
                <fmt:formatNumber value="${-trans.amount}" pattern="#,###"/> vnd
            </div>

            <div class="text-start">
                <div class="info-row">
                    <span class="info-label">Mã giao dịch</span>
                    <span class="info-value">#${trans.transactionId}</span>
                </div>
                <div class="info-row" style="border: none;">
                    <span class="info-label">Thời gian</span>
                    <span class="info-value">
                            <fmt:formatDate value="${trans.createdAt}" pattern="HH:mm dd/MM/yyyy"/>
                        </span>
                </div>
            </div>

            <div style="font-size: 0.85rem; background: #f0fdf4; color: #166534; padding: 10px; border-radius: 8px; margin-top: 15px;">
                Truy cập Ewallet để xem mã thẻ vừa mua.
            </div>

            <a href="${pageContext.request.contextPath}/ewallet" class="btn-action btn-success-action">
                Đến Ewallet
            </a>
        </c:when>

        <%-- TRƯỜNG HỢP THẤT BẠI --%>
        <c:otherwise>
            <div class="icon-wrapper icon-failed">
                <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-x-lg" viewBox="0 0 16 16">
                    <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z"/>
                </svg>
            </div>
            <div class="title" style="color: #ef4444;">Giao dịch thất bại</div>
            <div class="message">${errorMessage}</div>

            <a href="${pageContext.request.contextPath}/service" class="btn-action btn-failed-action">
                Xác nhận
            </a>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>