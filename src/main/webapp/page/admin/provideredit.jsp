<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật nhà cung cấp</title>
    <style>
        /* Đồng bộ giao diện với addproduct/provideradd */
        /* Match categoryupdate.jsp styling */
        body { font-family: Arial, sans-serif; background: #f6f8fa; }
        .container { max-width: 600px; margin: 40px auto; background: white; padding: 32px; border-radius: 12px; box-shadow: 0 8px 30px #cfd8dc; }
        h2 { margin-bottom: 24px; color: #333; }
        label { margin-top: 10px; font-weight: 600; display:block; }
        input { width: 100%; padding: 10px; margin: 5px 0 18px 0; border: 1px solid #bbb; border-radius: 5px; box-sizing: border-box; }
        input, select, textarea { width: 100%; padding: 10px; margin: 5px 0 18px 0; border: 1px solid #bbb; border-radius: 5px; box-sizing: border-box; font-family: inherit; }
        textarea { min-height: 120px; resize: vertical; }
        .err-msg { color: red; font-size: 13px; margin-top: -12px; margin-bottom: 12px; }
        button { background: #667eea; color: white; padding: 10px 32px; border: none; border-radius: 6px; font-weight: bold; }
        button:hover { background: #4051ad; cursor: pointer; }
        .btn-group { display: flex; justify-content: space-between; align-items: center; margin-top: 24px; gap:12px; }
        .btn-reset { background: #94a3b8; color: white; padding: 10px 20px; border-radius: 6px; border: none; }
        .btn-reset:hover { background: #64748b; cursor: pointer; }
        .btn-back {
            background: #e5e7eb;
            color: #334155;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: bold;
            display: inline-block;
        }
        .btn-back:hover { background: #cbd5e1; }
        .small-note { font-size: 13px; color: #6b7280; margin-top: -12px; margin-bottom: 10px; }
        .id-badge { color: #6b7280; font-size: 14px; margin-left: 6px; }
    </style>
</head>
<body>
<div class="container">
    <h2>Cập nhật nhà cung cấp <span class="id-badge">#<c:out value="${provider.providerId}"/></span></h2>

    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-${sessionScope.messageType}" 
                 style="padding: 10px; margin-bottom: 16px; border-radius: 6px; 
                        background-color: ${sessionScope.messageType == 'success' ? '#dcfce7' : '#fee2e2'}; 
                        color: ${sessionScope.messageType == 'success' ? '#166534' : '#991b1b'}; 
                        border: 1px solid ${sessionScope.messageType == 'success' ? '#86efac' : '#fecaca'};">
                <c:out value="${sessionScope.message}"/>
            </div>
            <c:remove var="message" scope="session"/>
            <c:remove var="messageType" scope="session"/>
        </c:if>

    <form method="post" action="${pageContext.request.contextPath}/admin/providers/edit">
        <input type="hidden" name="providerId" value="${provider.providerId}" />

        <label>Tên nhà cung cấp *</label>
        <input type="text" name="providerName"
               value="${not empty inputProviderName ? inputProviderName : provider.providerName}" required />
        <c:if test="${not empty validateErrors['providerName']}">
            <div class="err-msg"><c:out value="${validateErrors['providerName']}"/></div>
        </c:if>

        <label>Logo URL</label>
        <input type="text" name="logoUrl"
               value="${not empty inputLogoUrl ? inputLogoUrl : provider.logoUrl}"
               placeholder="/assetAdmin/assets/images/providers/logo.png" />
        <div class="small-note">Đường dẫn có thể là tương đối (ví dụ: /assetAdmin/assets/images/xxx.png) hoặc URL đầy đủ.</div>
        <c:if test="${not empty validateErrors['logoUrl']}">
            <div class="err-msg"><c:out value="${validateErrors['logoUrl']}"/></div>
        </c:if>

        <label>Trạng thái</label>
        <select name="status" required>
            <option value="1" ${provider.status == 1 ? 'selected' : ''}>Đang hoạt động</option>
            <option value="0" ${provider.status == 0 ? 'selected' : ''}>Ngừng hoạt động</option>
        </select>

        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/admin/providers" class="btn-back">⬅ Quay về</a>

            <div style="display:flex; gap:8px;">
                <button type="reset" class="btn-reset">Reset</button>
                <button type="submit">Cập nhật</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>