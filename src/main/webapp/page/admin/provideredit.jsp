<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Sửa nhà cung cấp</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8fafc; }
        . container { max-width: 500px; margin: 60px auto; background: white; border-radius: 14px; box-shadow: 0 4px 22px #cfd8dc; padding: 32px; }
        label { display: block; margin-top: 16px; font-weight: 600;}
        input { width: 100%; padding:  8px 10px; border-radius: 5px; border: 1px solid #bcc4cc; margin-top: 5px;}
        button { background: #4f46e5; color: white; font-weight:  bold; padding: 10px 38px; border: none; border-radius: 7px; margin-top: 18px;}
    </style>
</head>
<body>
<div class="container">
    <h2>Sửa nhà cung cấp</h2>
    <form method="post" action="${pageContext.request.contextPath}/admin/providers/edit">
        <input type="hidden" name="providerId" value="${provider.providerId}" />
        <label>Tên nhà cung cấp *</label>
        <input type="text" name="providerName" value="${provider.providerName}" required />
        <label>Logo URL</label>
        <input type="text" name="logoUrl" value="${provider.logoUrl}" />
        <button type="submit">Cập nhật</button>
    </form>
</div>
</body>
</html>