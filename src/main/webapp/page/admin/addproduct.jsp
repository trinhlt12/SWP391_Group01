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

        /* NEW: label + error on same line */
        .field-head{
            display: flex;
            align-items: baseline;
            justify-content: space-between;
            gap: 12px;
            margin-top: 10px;
        }
        .field-head label{
            margin-top: 0; /* override old label margin so it aligns nicely */
        }
        .field-head .err-msg{
            white-space: nowrap;
            margin: 0;
            text-align: right;
            max-width: 60%;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        button { background: #667eea; color: white; padding: 10px 32px; border: none; border-radius: 6px; font-weight: bold;}
        button:hover { background: #4051ad;}
        .btn-group {
            display: flex;
            justify-content: space-between;
            margin-top: 24px;
        }
        .btn-reset { background: #94a3b8; }
        .btn-reset:hover { background: #64748b; }
        .btn-back {
            background: #e5e7eb;
            color: #334155;
            text-decoration: none;
            padding: 10px 32px;
            border-radius: 6px;
            font-weight: bold;
            display: inline-block;
        }
        .btn-back:hover { background: #cbd5e1; }
    </style>
</head>
<body>
<div class="container">
    <h2>Add New Product</h2>
    <form action="${pageContext.request.contextPath}/admin/products/add" method="post" enctype="multipart/form-data">

        <!-- Product Name -->
        <div class="field-head">
            <label>Product Name *</label>
            <c:if test="${not empty validateErrors['productName']}">
                <div class="err-msg">${validateErrors['productName']}</div>
            </c:if>
        </div>
        <input type="text" name="productName" value="${inputProductName != null ? inputProductName : ''}">

        <!-- Category -->
        <div class="field-head">
            <label>Category *</label>
            <c:if test="${not empty validateErrors['categoryId']}">
                <div class="err-msg">${validateErrors['categoryId']}</div>
            </c:if>
        </div>
        <select name="categoryId">
            <option value="">-- Select Category --</option>
            <c:forEach var="c" items="${listCategory}">
                <option value="${c.categoryId}" ${inputCategoryId == c.categoryId ? 'selected' : ''}>${c.categoryName}</option>
            </c:forEach>
        </select>

        <!-- Provider -->
        <div class="field-head">
            <label>Provider *</label>
            <c:if test="${not empty validateErrors['providerId']}">
                <div class="err-msg">${validateErrors['providerId']}</div>
            </c:if>
        </div>
        <select name="providerId">
            <option value="">-- Select Provider --</option>
            <c:forEach var="p" items="${listProvider}">
                <option value="${p.providerId}" ${inputProviderId == p.providerId ? 'selected' : ''}>${p.providerName}</option>
            </c:forEach>
        </select>

        <!-- Price -->
        <div class="field-head">
            <label>Price *</label>
            <c:if test="${not empty validateErrors['price']}">
                <div class="err-msg">${validateErrors['price']}</div>
            </c:if>
        </div>
        <input type="number" step="1000" name="price" min="0" value="${inputPrice != null ? inputPrice : ''}">

        <!-- Image -->
        <div class="field-head">
            <label>Image</label>
            <c:if test="${not empty validateErrors['image']}">
                <div class="err-msg">${validateErrors['image']}</div>
            </c:if>
        </div>
        <input type="file" name="image" accept=".jpg,.jpeg,.png">

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/admin/products" class="btn-back">⬅ Back</a>
            <div>
                <button type="reset" class="btn-reset">Reset</button>
                <button type="submit">Add Product</button>
            </div>
        </div>

    </form>
</div>
</body>
</html>