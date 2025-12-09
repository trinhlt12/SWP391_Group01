<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 08/12/2025
  Time: 11:25 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập</title>
</head>
<body>
<h2>Login</h2>

<form action="login" method="post">
    <label>Email:</label>
    <input type="text" name="email" required><br><br>

    <label>Password:</label>
    <input type="password" name="password" required><br><br>

    <button type="submit">Login</button>
</form>

<% String error = (String) request.getAttribute("error");
    if (error != null) { %>
<p style="color:red;"><%= error %>
</p>
<% } %>

</body>
</html>

