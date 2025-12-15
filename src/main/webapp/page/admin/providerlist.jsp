<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Danh sách nhà cung cấp</title>
    <style>
        body { font-family: Arial, sans-serif; background:  #f8fafc; }
        .container { max-width: 1000px; margin: 40px auto; background: white; border-radius: 14px; box-shadow: 0 4px 22px #cfd8dc; padding: 28px; }
        h2 { color: #334155; }
        .btn { background: #4f46e5; color: white; padding:  8px 20px; border-radius: 6px; text-decoration: none; font-weight: 600;}
        .btn:hover { background: #4338ca; }
        .btn-edit { background: #0ea5e9; }
        . btn-delete { background: #dc2626; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px;}
        th, td { text-align: left; padding: 12px; }
        th { background: #f1f5f9; color: #475569; }
        tr:nth-child(even) { background: #f9fafb;}
        .search-bar { margin:  20px 0;}
        .search-bar input { padding: 7px 10px; border-radius: 5px; border: 1px solid #d1d5db; margin-right: 8px;}
        .search-bar button { padding: 7px 16px; border-radius: 5px; border: none; background: #64748b; color: white;}
        .paging { margin-top: 20px; text-align: center;}
        .paging a { padding: 7px 14px; margin: 0 3px; border-radius: 6px; border:  1px solid #ddd; background: #eee; color: #333; text-decoration: none;}
        . paging . selected { background: #4f46e5; color: white;}
        img. logo { width: 60px; height: 60px; object-fit: contain;}
    </style>
</head>
<body>
<div class="container">
    <h2>Danh sách nhà cung cấp</h2>
    <a href="${pageContext.request. contextPath}/admin/providers/add" class="btn">➕ Thêm nhà cung cấp</a>
    <form class="search-bar" method="get" action="${pageContext.request. contextPath}/admin/providers">
        <input type="text" name="search" placeholder="Tìm tên nhà cung cấp" value="${search}">
        <button type="submit">Tìm kiếm</button>
    </form>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Logo</th>
            <th>Tên nhà cung cấp</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${list}">
        <tr>
            <td>${p. providerId}</td>
            <td><img src="${pageContext.request.contextPath}${p.logoUrl}" alt="logo" class="logo"/></td>
            <td>${p.providerName}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/providers/edit?id=${p.providerId}" class="btn btn-edit">Sửa</a>
                <a href="${pageContext.request.contextPath}/admin/providers/delete?id=${p.providerId}" class="btn btn-delete" onclick="return confirm('Xóa nhà cung cấp này? ')">Xóa</a>
            </td>
        </tr>
        </c: forEach>
        </tbody>
    </table>
    <div class="paging">
        <c:if test="${totalPages > 1}">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a class="${i == currentPage ? 'selected' : ''}" href="?page=${i}&search=${search}">${i}</a>
            </c:forEach>
        </c:if>
        <div style="margin-top: 10px;">Tổng:  <b>${totalItems}</b> | Trang ${currentPage}/${totalPages}</div>
    </div>
</div>
</body>
</html>