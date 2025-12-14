<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 10:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Thêm sản phẩm</h2>

<form action="${pageContext.request.contextPath}/admin/products/add" method="post">

    <label>Nhà mạng:</label>
    <select name="providerId" required>
        <c:forEach var="p" items="${providers}">
            <option value="${p.providerId}">${p.providerName}</option>
        </c:forEach>
    </select>
    <br/>

    <label>Danh mục:</label>
    <select name="categoryId" required>
        <c:forEach var="c" items="${categories}">
            <option value="${c.categoryId}">${c.categoryName}</option>
        </c:forEach>
    </select>
    <br/>

    <label>Tên sản phẩm:</label>
    <input type="text" name="productName" required />
    <br/>

    <label>Giá bán:</label>
    <input type="number" name="price" step="0.01" required />
    <br/>

    <label>Số lượng tồn ban đầu:</label>
    <input type="number" name="quantity" min="0" required />
    <br/>

    <label>Ảnh sản phẩm (URL):</label>
    <input type="text" name="imageUrl" />
    <br/>

    <button type="submit">Thêm sản phẩm</button>
</form>

