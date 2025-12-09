<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 08/12/2025
  Time: 11:55 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>

</body>
</html>
