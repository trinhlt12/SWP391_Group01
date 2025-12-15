<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/14/2025
  Time: 12:38 PM
  To change this template use File | Settings | File Templates.
--%>
<<%@ page contentType="text/html;charset=UTF-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <!DOCTYPE html>
 <html>
 <head>
     <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <title>Product Management</title>
     <style>
         * {
             margin: 0;
             padding: 0;
             box-sizing: border-box;
         }

         body {
             font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
             background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
             min-height: 100vh;
             padding: 20px;
         }

         .container {
             max-width: 1200px;
             margin: 0 auto;
             background: white;
             border-radius: 16px;
             box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
             overflow: hidden;
         }

         .header {
             background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
             padding: 30px 40px;
             color: white;
             display: flex;
             justify-content: space-between;
             align-items: center;
             flex-wrap: wrap;
             gap: 20px;
         }

         .header h2 {
             font-size: 28px;
             font-weight: 600;
             display: flex;
             align-items: center;
             gap: 10px;
         }

         .header h2::before {
             content: "🛒";
             font-size: 32px;
         }

         .btn-add {
             background: white;
             color: #667eea;
             padding: 12px 24px;
             border-radius: 8px;
             text-decoration: none;
             font-weight: 600;
             display: inline-flex;
             align-items: center;
             gap: 8px;
             transition: all 0.3s ease;
             box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
         }

         .btn-add:hover {
             transform: translateY(-2px);
             box-shadow: 0 6px 20px rgba(0, 0, 0, 0.3);
             background: #f8f9ff;
         }

         .table-container {
             padding: 40px;
             overflow-x: auto;
         }

         table {
             width: 100%;
             border-collapse: separate;
             border-spacing: 0;
             background: white;
         }

         thead {
             background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
             color: white;
         }

         th {
             padding: 16px;
             text-align: left;
             font-weight: 600;
             font-size: 14px;
             text-transform: uppercase;
             letter-spacing: 0.5px;
         }

         th:first-child {
             border-radius: 8px 0 0 0;
         }

         th:last-child {
             border-radius: 0 8px 0 0;
         }

         tbody tr {
             border-bottom: 1px solid #e5e7eb;
             transition: all 0.3s ease;
         }

         tbody tr:hover {
             background: #f8f9ff;
             transform: scale(1.01);
             box-shadow: 0 4px 12px rgba(102, 126, 234, 0.1);
         }

         tbody tr:last-child {
             border-bottom: none;
         }

         td {
             padding: 16px;
             color: #374151;
         }

         td:first-child {
             font-weight: 600;
             color: #667eea;
         }

         .action-links {
             display: flex;
             gap: 12px;
             align-items: center;
         }

         .btn-action {
             padding: 6px 14px;
             border-radius: 6px;
             text-decoration: none;
             font-size: 13px;
             font-weight: 500;
             transition: all 0.3s ease;
             display: inline-flex;
             align-items: center;
             gap: 4px;
         }

         .btn-update {
             background: #10b981;
             color: white;
         }

         .btn-update:hover {
             background: #059669;
             transform: translateY(-1px);
             box-shadow: 0 4px 12px rgba(16, 185, 129, 0.4);
         }

         .btn-delete {
             background: #ef4444;
             color: white;
         }

         .btn-delete:hover {
             background: #dc2626;
             transform: translateY(-1px);
             box-shadow: 0 4px 12px rgba(239, 68, 68, 0.4);
         }

         .empty-state {
             text-align: center;
             padding: 60px 20px;
             color: #9ca3af;
         }

         .empty-state-icon {
             font-size: 64px;
             margin-bottom: 20px;
         }

         .empty-state h3 {
             font-size: 20px;
             margin-bottom: 10px;
             color: #6b7280;
         }

         .badge {
             display: inline-block;
             padding: 4px 10px;
             border-radius: 12px;
             font-size: 12px;
             font-weight: 600;
             background: #e0e7ff;
             color: #667eea;
         }

         .product-image {
             border-radius: 8px;
             max-width: 60px;
             display: block;
         }

         .paging {margin-top:20px; text-align:center;}
         .paging .pg {padding:7px 14px; margin:0 3px; border-radius:6px; border:1px solid #ddd; background:#eee; color:#333; text-decoration:none;}
         .paging .selected {background:#667eea; color:white;}
         .filter-bar {margin:24px 0;}
         .filter-bar form > * {display:inline-block; margin-right:10px;}
         .filter-bar input[type="text"] {padding:7px;border-radius:6px;border:1px solid #ccc;}
         .filter-bar select, .filter-bar button {padding:7px 10px;border-radius:6px;border:1px solid #ccc;}

         @media (max-width: 768px) {
             .header {
                 flex-direction: column;
                 align-items: flex-start;
             }

             .table-container {
                 padding: 20px;
             }

             table {
                 font-size: 14px;
             }

             th, td {
                 padding: 12px 8px;
             }

             .action-links {
                 flex-direction: column;
                 gap: 8px;
             }
         }
     </style>
 </head>
 <body>
     <div class="container">
         <div class="header">
             <h2>Product Management</h2>
             <a href="${pageContext.request.contextPath}/admin/products/add" class="btn-add">
                 ➕ Add New Product
             </a>
         </div>

         <div class="table-container">
             <!-- Filter, sort và pageSize -->
             <div class="filter-bar">
                 <form method="get" action="${pageContext.request.contextPath}/admin/products">
                     <input type="text" name="search" value="${search}" placeholder="Search name/category/provider..."/>
                     <select name="categoryId">
                         <option value="">-- All Categories --</option>
                         <c:forEach var="c" items="${categories}">
                             <option value="${c.categoryId}" ${categoryId == c.categoryId ? 'selected' : ''}>${c.categoryName}</option>
                         </c:forEach>
                     </select>
                     <select name="providerId">
                         <option value="">-- All Providers --</option>
                         <c:forEach var="p" items="${providers}">
                             <option value="${p.providerId}" ${providerId == p.providerId ? 'selected' : ''}>${p.providerName}</option>
                         </c:forEach>
                     </select>
                     <select name="sort">
                         <option value="">Sort by</option>
                         <option value="price_asc" ${sort == 'price_asc' ? 'selected' : ''}>Price ↑</option>
                         <option value="price_desc" ${sort == 'price_desc' ? 'selected' : ''}>Price ↓</option>
                     </select>
                     <select name="pageSize">
                         <option value="5" ${pageSize == 5 ? 'selected' : ''}>5/page</option>
                         <option value="10" ${pageSize == 10 ? 'selected' : ''}>10/page</option>
                         <option value="25" ${pageSize == 25 ? 'selected' : ''}>25/page</option>
                         <option value="50" ${pageSize == 50 ? 'selected' : ''}>50/page</option>
                     </select>
                     <button type="submit">Filter/Search</button>
                 </form>
             </div>
             <c:choose>
                 <c:when test="${empty list}">
                     <div class="empty-state">
                         <div class="empty-state-icon">📭</div>
                         <h3>No Products Found</h3>
                         <p>Start by adding your first product.</p>
                     </div>
                 </c:when>
                 <c:otherwise>
                     <table>
                         <thead>
                             <tr>
                                 <th>ID</th>
                                 <th>Image</th>
                                 <th>Product Name</th>
                                 <th>Provider</th>
                                 <th>Category</th>
                                 <th>Price</th>
                                 <th>Stock</th>
                                 <th>Actions</th>
                             </tr>
                         </thead>
                         <tbody>
                             <c:forEach var="p" items="${list}">
                                 <tr>
                                     <td><span class="badge">#${p.productId}</span></td>
                                     <td>
                                         <c:choose>
                                             <c:when test="${not empty p.imageUrl}">
                                                 <img src="${pageContext.request.contextPath}/image/${p.imageUrl}" class="product-image" alt="Product Image"/>
                                             </c:when>
                                             <c:otherwise>
                                                 <span style="color:#a0aec0; font-size:28px;">🖼️</span>
                                             </c:otherwise>
                                         </c:choose>
                                     </td>
                                     <td><b>${p.productName}</b></td>
                                     <!-- Provider Name: lookup theo providerId -->
                                                             <td>
                                                                 <c:set var="provName" value="-" />
                                                                 <c:forEach var="prov" items="${providers}">
                                                                     <c:if test="${prov.providerId == p.providerId}">
                                                                         <c:set var="provName" value="${prov.providerName}" />
                                                                     </c:if>
                                                                 </c:forEach>
                                                                 ${provName}
                                                             </td>

                                                             <!-- Category Name: lookup theo categoryId -->
                                                             <td>
                                                                 <c:set var="catName" value="-" />
                                                                 <c:forEach var="cat" items="${categories}">
                                                                     <c:if test="${cat.categoryId == p.categoryId}">
                                                                         <c:set var="catName" value="${cat.categoryName}" />
                                                                     </c:if>
                                                                 </c:forEach>
                                                                 ${catName}
                                                             </td>
                                     <td>${p.price}</td>
                                     <td>${p.quantity}</td>
                                     <td>
                                         <div class="action-links">
                                             <a href="${pageContext.request.contextPath}/admin/products/update?id=${p.productId}"
                                                class="btn-action btn-update">
                                                 ✏️ Update
                                             </a>
                                             <a href="${pageContext.request.contextPath}/admin/products/delete?id=${p.productId}"
                                                class="btn-action btn-delete"
                                                onclick="return confirm('Are you sure you want to delete this product?')">
                                                 🗑️ Delete
                                             </a>
                                         </div>
                                     </td>
                                 </tr>
                             </c:forEach>
                         </tbody>
                     </table>
                     <!-- PHÂN TRANG -->
                     <div class="paging">
                         <c:if test="${totalPages > 1}">
                             <c:forEach var="i" begin="1" end="${totalPages}">
                                 <a class="pg ${i == currentPage ? 'selected' : ''}"
                                    href="${pageContext.request.contextPath}/admin/products?page=${i}&pageSize=${pageSize}&search=${search}&sort=${sort}">
                                     ${i}
                                 </a>
                             </c:forEach>
                         </c:if>
                         <div style="margin-top:7px;font-size:13px;">
                             Total products: <b>${totalItems}</b> | Page ${currentPage} / ${totalPages}
                         </div>
                     </div>
                 </c:otherwise>
             </c:choose>
         </div>
     </div>
 </body>
 </html>