<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/13/2025
  Time: 11:50 PM
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Category</title>
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
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .form-container {
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
            max-width: 600px;
            width: 100%;
            animation: slideUp 0.5s ease;
        }

        @keyframes slideUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .form-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 30px 40px;
            color: white;
        }

        .form-header h2 {
            font-size: 28px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .form-header h2::before {
            content: "➕";
            font-size: 32px;
        }

        .form-header p {
            margin-top: 8px;
            opacity: 0.9;
            font-size: 14px;
        }

        .form-body {
            padding: 40px;
        }

        .alert {
            padding: 14px 18px;
            border-radius: 8px;
            margin-bottom: 24px;
            display: flex;
            align-items: center;
            gap: 12px;
            animation: shake 0.5s ease;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-10px); }
            75% { transform: translateX(10px); }
        }

        .alert-error {
            background: #fee2e2;
            border-left: 4px solid #ef4444;
            color: #991b1b;
        }

        .alert-error::before {
            content: "⚠️";
            font-size: 20px;
        }

        .form-group {
            margin-bottom: 24px;
        }

        .form-label {
            display: block;
            font-weight: 600;
            color: #374151;
            margin-bottom: 8px;
            font-size: 14px;
        }

        .form-label .required {
            color: #ef4444;
            margin-left: 4px;
        }

        .form-input,
        .form-textarea {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e5e7eb;
            border-radius: 8px;
            font-size: 15px;
            font-family: inherit;
            transition: all 0.3s ease;
            background: white;
        }

        .form-input:focus,
        .form-textarea:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }

        .form-input:hover,
        .form-textarea:hover {
            border-color: #cbd5e1;
        }

        .form-textarea {
            resize: vertical;
            min-height: 120px;
        }

        .char-counter {
            text-align: right;
            font-size: 12px;
            color: #9ca3af;
            margin-top: 4px;
        }

        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 32px;
            padding-top: 24px;
            border-top: 1px solid #e5e7eb;
        }

        .btn {
            padding: 12px 24px;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            border: none;
            font-family: inherit;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            flex: 1;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-primary:active {
            transform: translateY(0);
        }

        .btn-secondary {
            background: #f3f4f6;
            color: #6b7280;
            padding: 12px 20px;
        }

        .btn-secondary:hover {
            background: #e5e7eb;
            color: #374151;
        }

        .input-icon {
            position: relative;
        }

        .input-icon::before {
            content: "📝";
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            font-size: 18px;
        }

        .input-icon .form-input {
            padding-left: 44px;
        }

        @media (max-width: 768px) {
            .form-container {
                margin: 0;
            }

            .form-header {
                padding: 24px;
            }

            .form-body {
                padding: 24px;
            }

            .form-actions {
                flex-direction: column;
            }

            .btn-primary {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <div class="form-header">
            <h2>Add New Category</h2>
            <p>Create a new category for your products</p>
        </div>

        <div class="form-body">
            <!-- Error message -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ${error}
                </div>
            </c:if>

            <form method="post"
                  action="${pageContext.request.contextPath}/admin/category/add"
                  id="categoryForm">

                <!-- Category Name -->
                <div class="form-group">
                    <label for="categoryName" class="form-label">
                        Category Name
                        <span class="required">*</span>
                    </label>
                    <div class="input-icon">
                        <input type="text"
                               id="categoryName"
                               name="categoryName"
                               class="form-input"
                               value="${categoryName}"
                               placeholder="Enter category name"
                               maxlength="100"
                               required>
                    </div>
                    <div class="char-counter">
                        <span id="nameCounter">0</span>/100 characters
                    </div>
                </div>

                <!-- Description -->
                <div class="form-group">
                    <label for="description" class="form-label">
                        Description
                    </label>
                    <textarea id="description"
                              name="description"
                              class="form-textarea"
                              placeholder="Enter category description (optional)"
                              maxlength="500">${description}</textarea>
                    <div class="char-counter">
                        <span id="descCounter">0</span>/500 characters
                    </div>
                </div>

                <!-- Action buttons -->
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        💾 Save Category
                    </button>
                    <a href="${pageContext.request.contextPath}/admin/category"
                       class="btn btn-secondary">
                        ← Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script>
        // Character counter for category name
        const nameInput = document.getElementById('categoryName');
        const nameCounter = document.getElementById('nameCounter');

        nameInput.addEventListener('input', function() {
            nameCounter.textContent = this.value.length;
        });

        // Initialize counter on page load
        if (nameInput.value) {
            nameCounter.textContent = nameInput.value.length;
        }

        // Character counter for description
        const descInput = document.getElementById('description');
        const descCounter = document.getElementById('descCounter');

        descInput.addEventListener('input', function() {
            descCounter.textContent = this.value.length;
        });

        // Initialize counter on page load
        if (descInput.value) {
            descCounter.textContent = descInput.value.length;
        }

        // Form validation
        document.getElementById('categoryForm').addEventListener('submit', function(e) {
            const categoryName = nameInput.value.trim();

            if (categoryName === '') {
                e.preventDefault();
                alert('Please enter a category name');
                nameInput.focus();
                return false;
            }

            if (categoryName.length < 2) {
                e.preventDefault();
                alert('Category name must be at least 2 characters long');
                nameInput.focus();
                return false;
            }
        });

        // Auto-focus on category name input
        window.addEventListener('load', function() {
            nameInput.focus();
        });

        // Add animation to buttons
        document.querySelectorAll('.btn').forEach(button => {
            button.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-2px)';
            });

            button.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0)';
            });
        });
    </script>
</body>
</html>