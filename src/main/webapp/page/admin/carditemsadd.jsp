<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/14/2025
  Time: 11:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm nhiều thẻ từng dòng</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f6f7fc;}
        .container { max-width: 700px; margin: 40px auto; background: white; border-radius: 16px; box-shadow: 0 4px 22px #b8b8b8; padding: 32px; }
        label {font-weight:600;}
        select, input[type="text"], input[type="date"] { padding:7px 10px; border-radius:5px; border:1px solid #bcc4cc;}
        table { width:100%; border-collapse:collapse; margin-top:12px;}
        th, td { padding:8px; }
        th { background:#eee;}
        button, .btn { background:#4f46e5; color:white;font-weight:bold; padding:9px 24px; border:none; border-radius:7px;cursor:pointer;}
        .btn-del {background:#be123c}
        .msg {margin-bottom:15px;}
    </style>
    <script>
        function addRow() {
            const table = document.getElementById("add-list");
            const row = table.insertRow();
            const idx = table.rows.length;
            row.innerHTML = `
                <td><input type="text" name="serialNumber" required minlength="5" /></td>
                <td><input type="text" name="cardCode" required minlength="6" /></td>
                <td><input type="date" name="expirationDate" /></td>
                <td><button type="button" class="btn btn-del" onclick="removeRow(this)">Xóa</button></td>
            `;
        }
        function removeRow(btn) {
            const row = btn.parentNode.parentNode;
            row.parentNode.removeChild(row);
        }
        window.onload = function() {
            document.getElementById('addrow-btn').onclick = addRow;
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Thêm nhiều thẻ từng dòng</h2>
    <c:if test="${not empty message}">
        <div class="msg">${message}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/admin/carditems/add" method="post">
        <label for="productId">Sản phẩm *</label>
        <select name="productId" required id="productId">
            <option value="" disabled selected>--Chọn sản phẩm--</option>
            <c:forEach var="prd" items="${products}">
                <option value="${prd.productId}">${prd.productName} (${prd.price}đ)</option>
            </c:forEach>
        </select>
        <table id="add-list">
            <thead>
                <tr>
                    <th>Serial Number *</th>
                    <th>Mã nạp *</th>
                    <th>Hạn dùng</th>
                    <th></th>
                </tr>
            </thead>
            <!-- ... phần head và css giữ nguyên ... -->
            <tbody>
                <tr>
                    <td><input type="text" name="serialNumber[]" required minlength="5" /></td>
                    <td><input type="text" name="cardCode[]" required minlength="6" /></td>
                    <td><input type="date" name="expirationDate[]" /></td>
                    <td><button type="button" class="btn btn-del" onclick="removeRow(this)">Xóa</button></td>
                </tr>
            </tbody>

        </table>
        <button type="button" id="addrow-btn" class="btn" style="margin:18px 0 28px 0;">➕ Thêm dòng nhập</button>
        <br/>
        <button type="submit">Lưu danh sách thẻ</button>
    </form>
</div>
</body>
</html>