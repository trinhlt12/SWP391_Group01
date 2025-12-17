<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật loại thẻ</title>
    <style>
        /* Đồng bộ giao diện với addproduct/provideradd */
        body { font-family: Arial, sans-serif; background: #f6f8fa; }
        .container { max-width: 600px; margin: 40px auto; background: white; padding: 32px; border-radius: 12px; box-shadow: 0 8px 30px #cfd8dc; }
        h2 { margin-bottom: 24px; color: #333; }
        label { margin-top: 10px; font-weight: 600; display:block; }
        input, textarea { width: 100%; padding: 10px; margin: 5px 0 18px 0; border: 1px solid #bbb; border-radius: 5px; box-sizing: border-box; }
        textarea { min-height: 120px; resize: vertical; }
        .err-msg { color: red; font-size: 13px; margin-top: -12px; margin-bottom: 12px; }
        .alert { background: #fee2e2; color: #991b1b; border: 1px solid #ef4444; padding: 10px 14px; border-radius: 6px; margin-bottom: 16px; }
        .btn-group { display: flex; justify-content: space-between; align-items: center; margin-top: 24px; gap:12px; }
        .btn-reset { background: #94a3b8; color: white; padding: 10px 20px; border-radius: 6px; border: none; }
        .btn-reset:hover { background: #64748b; cursor: pointer; }
        button { background: #667eea; color: white; padding: 10px 32px; border: none; border-radius: 6px; font-weight: bold; }
        button:hover { background: #4051ad; cursor: pointer; }
        .btn-back { background: #e5e7eb; color: #334155; text-decoration: none; padding: 10px 20px; border-radius: 6px; font-weight: bold; display: inline-block; }
        .btn-back:hover { background: #cbd5e1; }
        .id-badge { color: #6b7280; font-size: 14px; margin-left: 6px; }
    </style>
</head>
<body>
<div class="container">
    <h2>Cập nhật loại thẻ <span class="id-badge">#<c:out value="${category.categoryId}"/></span></h2>

    <!-- Thông báo (nếu có) -->
    <c:if test="${not empty sessionScope.message}">
        <div class="alert">
            <c:out value="${sessionScope.message}"/>
        </div>
        <c:remove var="message" scope="session"/>
        <c:remove var="messageType" scope="session"/>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/category/update">
        <input type="hidden" name="categoryId" value="<c:out value='${category.categoryId}'/>"/>

        <label>Tên loại thẻ *</label>
        <input type="text" name="categoryName"
               value="<c:out value='${not empty inputCategoryName ? inputCategoryName : category.categoryName}'/>"
               required />
        <c:if test="${not empty validateErrors['categoryName']}">
            <div class="err-msg"><c:out value="${validateErrors['categoryName']}"/></div>
        </c:if>

        <label>Mô tả</label>
        <textarea name="description"
                  placeholder="Nhập mô tả (không bắt buộc)"><c:out value='${not empty inputDescription ? inputDescription : category.description}'/></textarea>
        <c:if test="${not empty validateErrors['description']}">
            <div class="err-msg"><c:out value="${validateErrors['description']}"/></div>
        </c:if>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/admin/category" class="btn-back">⬅ Quay về</a>
            <div style="display:flex; gap:8px;">
                <button type="reset" class="btn-reset">Reset</button>
                <button type="submit">Lưu thay đổi</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>