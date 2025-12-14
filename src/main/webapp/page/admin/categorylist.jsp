<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 11:47 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category Management</title>
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
            content: "📂";
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

        .btn-edit {
            background: #10b981;
            color: white;
        }

        .btn-edit:hover {
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
            <h2>Category Management</h2>
            <a href="${pageContext.request.contextPath}/admin/category/add" class="btn-add">
                ➕ Add New Category
            </a>
        </div>

        <div class="table-container">
            <c:choose>
                <c:when test="${empty list}">
                    <div class="empty-state">
                        <div class="empty-state-icon">📭</div>
                        <h3>No Categories Found</h3>
                        <p>Start by adding your first category</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Category Name</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${list}" var="c">
                                <tr>
                                    <td><span class="badge">#${c.categoryId}</span></td>
                                    <td><strong>${c.categoryName}</strong></td>
                                    <td>${c.description}</td>
                                    <td>
                                        <div class="action-links">
                                            <a href="${pageContext.request.contextPath}/admin/category/update?id=${c.categoryId}"
                                               class="btn-action btn-edit">
                                                ✏️ Edit
                                            </a>
                                            <a href="${pageContext.request.contextPath}/admin/category/delete?id=${c.categoryId}"
                                               class="btn-action btn-delete"
                                               onclick="return confirm('Are you sure you want to delete this category?')">
                                                🗑️ Delete
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script>
        // Smooth scroll animation for better UX
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                e.preventDefault();
                document.querySelector(this.getAttribute('href')).scrollIntoView({
                    behavior: 'smooth'
                });
            });
        });

        // Add ripple effect on button clicks
        document.querySelectorAll('.btn-action, .btn-add').forEach(button => {
            button.addEventListener('click', function(e) {
                let ripple = document.createElement('span');
                this.appendChild(ripple);
                let x = e.clientX - e.target.offsetLeft;
                let y = e.clientY - e.target.offsetTop;
                ripple.style.left = `${x}px`;
                ripple.style.top = `${y}px`;
                setTimeout(() => {
                    ripple.remove();
                }, 600);
            });
        });
    </script>
</body>
</html>