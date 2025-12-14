<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 11:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Category Management</h2>

<a href="${pageContext.request.contextPath}/admin/category/add">
    ➕ Add Category
</a>

<table border="1" cellpadding="8">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${list}" var="c">
        <tr>
            <td>${c.categoryId}</td>
            <td>${c.categoryName}</td>
            <td>${c.description}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/category/update?id=${c.categoryId}">
                    Edit
                </a>
                |
                <a href="${pageContext.request.contextPath}/admin/category/delete?id=${c.categoryId}"
                   onclick="return confirm('Delete this category?')">
                    Delete
                </a>
            </td>
        </tr>
    </c:forEach>
</table>


