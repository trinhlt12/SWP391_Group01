<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product List - Admin</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background: #f2f2f2; }
        img.product-img { max-width: 80px; height: auto; }
        .pagination a { margin: 0 4px; padding: 6px 10px; border: 1px solid #ccc; text-decoration: none; }
        .pagination .current { font-weight: bold; background: #eee; padding: 6px 10px; border: 1px solid #999; }
        .actions a { margin-right: 6px; }
        .top-bar { display:flex; gap:12px; align-items:center; margin-bottom:12px; }
        .search-box input[type="text"] { padding:6px; }
        .search-box button { padding:6px 10px; }
    </style>
</head>
<body>
<h2>Danh sách sản phẩm</h2>

<div class="top-bar">
    <form method="get" action="${pageContext.request.contextPath}/products" class="search-box">
        <input type="text" name="q" placeholder="Tìm theo tên..." value="${fn:escapeXml(param.q)}"/>
        <select name="sort">
            <option value="">Sắp xếp</option>
            <option value="newest" ${param.sort == 'newest' ? 'selected' : ''}>Mới nhất</option>
            <option value="name_asc" ${param.sort == 'name_asc' ? 'selected' : ''}>Tên A-Z</option>
            <option value="name_desc" ${param.sort == 'name_desc' ? 'selected' : ''}>Tên Z-A</option>
            <option value="price_asc" ${param.sort == 'price_asc' ? 'selected' : ''}>Giá tăng dần</option>
            <option value="price_desc" ${param.sort == 'price_desc' ? 'selected' : ''}>Giá giảm dần</option>
        </select>
        <!-- Nếu có danh mục, có thể render options ở đây bằng loop -->
        <button type="submit">Tìm</button>
    </form>

    <div style="margin-left:auto">
        <a href="${pageContext.request.contextPath}/admin/product/create">Thêm sản phẩm</a>
    </div>
</div>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Ảnh</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Denomination</th>
        <th>Category ID</th>
        <th>Provider ID</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty products}">
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.productId}</td>
                    <td>
                        <c:if test="${not empty p.imageUrl}">
                            <img class="product-img" src="${pageContext.request.contextPath}${p.imageUrl}" alt="${p.productName}"/>
                        </c:if>
                    </td>
                    <td>${p.productName}</td>
                    <td>${p.price}</td>
                    <td>${p.denomination}</td>
                    <td>${p.categoryId}</td>
                    <td>${p.providerId}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/admin/product/edit?id=${p.productId}">Edit</a>
                        <a href="${pageContext.request.contextPath}/admin/product/delete?id=${p.productId}" onclick="return confirm('Xác nhận xoá?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr><td colspan="8">Không có sản phẩm</td></tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<div class="pagination" style="margin-top:12px">
    <c:if test="${totalPages > 1}">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:url var="pageUrl" value="${pageContext.request.requestURI}">
                <c:param name="page" value="${i}"/>
                <c:if test="${not empty q}"><c:param name="q" value="${q}"/></c:if>
                <c:if test="${not empty sort}"><c:param name="sort" value="${sort}"/></c:if>
            </c:url>
            <c:choose>
                <c:when test="${i == page}">
                    <span class="current">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageUrl}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
</div>

</body>
</html>