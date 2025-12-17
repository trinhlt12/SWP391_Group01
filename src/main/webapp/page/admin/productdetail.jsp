<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f6f8fa; }
        .container { max-width: 900px; margin: 40px auto; background: white; padding: 28px; border-radius: 12px; box-shadow: 0 8px 30px #cfd8dc; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 18px; }
        .header h2 { margin: 0; color: #333; }
        .btn-back { background: #e5e7eb; color: #334155; text-decoration: none; padding: 8px 16px; border-radius: 6px; font-weight: bold; }
        .btn-back:hover { background: #cbd5e1; }
        .content { display: flex; gap: 24px; flex-wrap: wrap; }
        .left { flex: 0 0 320px; }
        .right { flex: 1 1 520px; }
        .card { background: #ffffff; border-radius: 8px; padding: 18px; border: 1px solid #eceff1; }
        .img-wrap { display:flex; align-items:center; justify-content:center; height:320px; overflow:hidden; border-radius:6px; background:#fafafa; }
        .img-wrap img { max-width:100%; max-height:100%; object-fit:contain; }
        .field { margin-bottom: 14px; }
        .label { display:block; font-weight:700; margin-bottom:6px; color:#374151; }
        .value { padding:10px 12px; border:1px solid #e2e8f0; border-radius:6px; background:#fbfdff; color:#111827; }
        .price { color:#16a34a; font-weight:800; }
        .badge { display:inline-block; padding:6px 10px; border-radius:999px; font-weight:700; font-size:13px; }
        .badge-success { background:#ecfdf5; color:#065f46; border:1px solid #bbf7d0; }
        .badge-warning { background:#fffbeb; color:#92400e; border:1px solid #fde68a; }
        .badge-danger { background:#fff1f2; color:#9f1239; border:1px solid #fecaca; }
        .actions { display:flex; gap:10px; margin-top:18px; }
        .btn { padding:10px 16px; border-radius:6px; text-decoration:none; color:#fff; font-weight:700; }
        .btn-primary { background:#667eea; }
        .btn-primary:hover { background:#4051ad; }
        .btn-secondary { background:#94a3b8; }
        .btn-danger { background:#ef4444; }
        .muted { color:#6b7280; font-size:14px; }
        pre.description { white-space:pre-wrap; background:#fbfdff; padding:10px 12px; border-radius:6px; border:1px solid #e2e8f0; }
        @media (max-width: 720px) {
            .content { flex-direction: column; }
            .left, .right { flex: 1 1 100%; }
            .img-wrap { height:240px; }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2>Chi tiết sản phẩm #<c:out value="${product.productId}" /></h2>
        <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">⬅ Quay lại</a>
    </div>
<fmt:setLocale value="vi_VN" />
    <div class="content">
        <!-- Left: Image -->
        <div class="left card">
            <div class="img-wrap">
                <c:choose>
                    <c:when test="${not empty product.imageUrl}">
                        <img src="${pageContext.request.contextPath}/image/${product.imageUrl}" alt="${product.productName}" />
                    </c:when>
                    <c:otherwise>
                        <div class="muted">Không có hình ảnh</div>
                    </c:otherwise>
                </c:choose>
            </div>
            <div style="margin-top:12px; text-align:center;">
                <div class="muted">Nhà cung cấp</div>
                <div style="font-weight:800; margin-top:6px;"><c:out value="${providerName}" /></div>
            </div>
        </div>

        <!-- Right: Details -->
        <div class="right card">
            <div class="field">
                <span class="label">Tên sản phẩm</span>
                <div class="value"><c:out value="${product.productName}" /></div>
            </div>

            <div class="field">
                <span class="label">Tên loại thẻ</span>
                <div class="value"><c:out value="${categoryName}" /></div>
            </div>

            <div class="field">
                <span class="label">Tên nhà mạng</span>
                <div class="value"><c:out value="${providerName}" /></div>
            </div>

            <div class="field">
                <span class="label">Giá bán</span>
                <div class="value price">
  <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0" />
  VND
</div>
            </div>

            <div class="field">
                <span class="label">Số lượng tồn</span>
                <div>
                    <c:choose>
                        <c:when test="${product.quantity > 10}">
                            <span class="badge badge-success">${product.quantity} thẻ</span>
                        </c:when>
                        <c:when test="${product.quantity > 0}">
                            <span class="badge badge-warning">${product.quantity} thẻ</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">Hết hàng</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="actions">
                <a href="${pageContext.request.contextPath}/admin/products/update?id=${product.productId}" class="btn btn-primary">✎ Chỉnh sửa</a>
                <a href="${pageContext.request.contextPath}/admin/products/delete?id=${product.productId}" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa?');">🗑 Xóa</a>
                <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Quay về danh sách</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>