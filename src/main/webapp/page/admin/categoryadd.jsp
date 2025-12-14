<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 11:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2>Add Category</h2>

<!-- Error message -->
<c:if test="${not empty error}">
    <div style="color:red; margin-bottom: 10px;">
        ${error}
    </div>
</c:if>

<form method="post"
      action="${pageContext.request.contextPath}/admin/category/add">

    <!-- Category Name -->
    <div style="margin-bottom: 10px;">
        <label for="categoryName">Category Name</label><br>
        <input type="text"
               id="categoryName"
               name="categoryName"
               value="${categoryName}"
               required>
    </div>

    <!-- Description -->
    <div style="margin-bottom: 10px;">
        <label for="description">Description</label><br>
        <textarea id="description"
                  name="description"
                  rows="4"
                  cols="40">${description}</textarea>
    </div>

    <!-- Action buttons -->
    <div>
        <button type="submit">Save</button>
        <a href="${pageContext.request.contextPath}/admin/category">
            Cancel
        </a>
    </div>

</form>


