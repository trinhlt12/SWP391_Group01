<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 10:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Product</title>
    <style>
        body { font-family: Arial,sans-serif; background: #f6f8fa;}
        .container { max-width: 600px; margin: 40px auto; background: white; padding: 32px; border-radius: 12px; box-shadow: 0 8px 30px #cfd8dc;}
        h2 { margin-bottom: 24px; color: #333}
        label { margin-top: 10px; font-weight: 600;}
        input, select { width: 100%; padding: 10px; margin: 5px 0 18px 0; border: 1px solid #bbb; border-radius: 5px;}
        .err-msg { color: red; font-size: 13px;}
        button { background: #667eea; color: white; padding: 10px 32px; border: none; border-radius: 6px; font-weight: bold;}
        button:hover { background: #4051ad;}
    </style>
</head>
<body>
<div class="container">
    <h2>Add New Product</h2>
    <form action="${pageContext.request.contextPath}/admin/products/add" method="post" enctype="multipart/form-data">

        <label>Product Name *</label>
        <input type="text" name="productName" value="${inputProductName != null ? inputProductName : ''}">
        <c:if test="${not empty validateErrors['productName']}">
            <div class="err-msg">${validateErrors['productName']}</div>
        </c:if>

        <label>Category *</label>
        <select name="categoryId">
            <option value="">-- Select Category --</option>
            <c:forEach var="c" items="${listCategory}">
                <option value="${c.categoryId}" ${inputCategoryId == c.categoryId ? 'selected' : ''}>${c.categoryName}</option>
            </c:forEach>
        </select>
        <c:if test="${not empty validateErrors['categoryId']}">
            <div class="err-msg">${validateErrors['categoryId']}</div>
        </c:if>

        <label>Provider *</label>
        <select name="providerId">
            <option value="">-- Select Provider --</option>
            <c:forEach var="p" items="${listProvider}">
                <option value="${p.providerId}" ${inputProviderId == p.providerId ? 'selected' : ''}>${p.providerName}</option>
            </c:forEach>
        </select>
        <c:if test="${not empty validateErrors['providerId']}">
            <div class="err-msg">${validateErrors['providerId']}</div>
        </c:if>

        <label>Price *</label>
        <input type="number" step="1000" name="price" min="0" value="${inputPrice != null ? inputPrice : ''}">
        <c:if test="${not empty validateErrors['price']}">
            <div class="err-msg">${validateErrors['price']}</div>
        </c:if>

        <label>Image</label>
        <input type="file" name="image" accept=".jpg,.jpeg,.png">
        <c:if test="${not empty validateErrors['image']}">
            <div class="err-msg">${validateErrors['image']}</div>
        </c:if>

        <button type="submit">Add Product</button>
    </form>
</div>
</body>
</html>