<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 08/12/2025
  Time: 11:54 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.embanthe.model.User" %>
<%
    User user = (User) session.getAttribute("currentUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<h2>Xin chào, <%= user.getFullName() %>!</h2>
<p>Email: <%= user.getEmail() %></p>
<p>Số dư: <%= user.getBalance() %></p>

<a href="logout.jsp">Logout</a>
</body>
</html>
