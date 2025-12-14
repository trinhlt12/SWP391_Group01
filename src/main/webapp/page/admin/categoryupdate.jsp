<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 11:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>

<h2>Edit Category</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/category/update">

    <input type="hidden" name="categoryId" value="${category.categoryId}">

    Category Name:<br>
    <input type="text" name="categoryName"
           value="${category.categoryName}" required><br><br>

    Description:<br>
    <textarea name="description">${category.description}</textarea><br><br>

    <button type="submit">Save</button>
    <a href="${pageContext.request.contextPath}/admin/category">Cancel</a>
</form>

