<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/14/2025
  Time: 11:38 PM
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Card Items</title>
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
            max-width: 900px;
            margin: 0 auto;
            background: white;
            border-radius: 16px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            overflow: hidden;
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

        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 30px 40px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 15px;
        }

        .header h2 {
            font-size: 28px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .header h2::before {
            content: "🎴";
            font-size: 32px;
        }

        .btn-back {
            background: white;
            color: #667eea;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 600;
            display: inline-flex;
            align-items: center;
            gap: 8px;
            transition: all 0.3s ease;
            font-size: 14px;
        }

        .btn-back:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
        }

        .content {
            padding: 40px;
        }

        .alert {
            padding: 16px 20px;
            border-radius: 8px;
            margin-bottom: 24px;
            animation: slideDown 0.5s ease;
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .alert-success {
            background: #d1fae5;
            border-left: 4px solid #10b981;
            color: #065f46;
        }

        .alert-error {
            background: #fee2e2;
            border-left: 4px solid #ef4444;
            color: #991b1b;
        }

        .alert-warning {
            background: #fef3c7;
            border-left: 4px solid #f59e0b;
            color: #92400e;
        }

        .alert-info {
            background: #dbeafe;
            border-left: 4px solid #3b82f6;
            color: #1e40af;
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

        .select-wrapper {
            position: relative;
        }

        .form-select {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e5e7eb;
            border-radius: 8px;
            font-size: 15px;
            font-family: inherit;
            background: white;
            cursor: pointer;
            transition: all 0.3s ease;
            appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 12 12'%3E%3Cpath fill='%23667eea' d='M6 9L1 4h10z'/%3E%3C/svg%3E");
            background-repeat: no-repeat;
            background-position: right 12px center;
            padding-right: 40px;
        }

        .form-select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }

        .product-info {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            margin-left: 8px;
            font-size: 13px;
            color: #6b7280;
        }

        .stock-badge {
            display: inline-block;
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 11px;
            font-weight: 600;
            background: #d1fae5;
            color: #065f46;
        }

        .stock-badge.low {
            background: #fef3c7;
            color: #92400e;
        }

        .stock-badge.out {
            background: #fee2e2;
            color: #991b1b;
        }

        .table-container {
            overflow-x: auto;
            margin: 24px 0;
            border: 2px solid #e5e7eb;
            border-radius: 8px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        th {
            padding: 14px 12px;
            text-align: left;
            font-weight: 600;
            font-size: 13px;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #e5e7eb;
        }

        tbody tr:hover {
            background: #f9fafb;
        }

        .form-input {
            width: 100%;
            padding: 10px 12px;
            border: 2px solid #e5e7eb;
            border-radius: 6px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.3s ease;
        }

        .form-input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .btn {
            padding: 10px 20px;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            border: none;
            font-family: inherit;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }

        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
        }

        .btn-success {
            background: #10b981;
            color: white;
        }

        .btn-success:hover {
            background: #059669;
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(16, 185, 129, 0.4);
        }

        .btn-danger {
            background: #ef4444;
            color: white;
        }

        .btn-danger:hover {
            background: #dc2626;
            transform: translateY(-2px);
        }

        .btn-add-row {
            margin: 20px 0;
        }

        .form-actions {
            display: flex;
            gap: 12px;
            margin-top: 32px;
            padding-top: 24px;
            border-top: 2px solid #e5e7eb;
        }

        .row-counter {
            text-align: right;
            color: #6b7280;
            font-size: 13px;
            margin-top: 8px;
            font-weight: 500;
        }

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                align-items: flex-start;
            }

            .content {
                padding: 24px;
            }

            .table-container {
                font-size: 13px;
            }

            th, td {
                padding: 8px 6px;
            }

            .form-actions {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h2>Add Card Items</h2>
            <a href="${pageContext.request.contextPath}/admin/carditems" class="btn-back">
                ← Back to Card List
            </a>
        </div>

        <div class="content">
            <c:if test="${not empty message}">
                <c:set var="messageType" value="info" />
                <c:if test="${message.contains('success') || message.contains('successfully') || message.contains('thành công')}">
                    <c:set var="messageType" value="success" />
                </c:if>
                <c:if test="${message.contains('error') || message.contains('lỗi') || message.contains('failed')}">
                    <c:set var="messageType" value="error" />
                </c:if>
                <c:if test="${message.contains('warning') || message.contains('bỏ qua') || message.contains('skipped')}">
                    <c:set var="messageType" value="warning" />
                </c:if>

                <div class="alert alert-${messageType}">
                    ${message}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/carditems/add" method="post" id="cardForm">
                <div class="form-group">
                    <label for="productId" class="form-label">
                        Select Product
                        <span class="required">*</span>
                    </label>
                    <div class="select-wrapper">
                        <select name="productId" required id="productId" class="form-select">
                            <option value="" disabled selected>-- Choose a product --</option>
                            <c:forEach var="prd" items="${products}">
                                <option value="${prd.productId}"
                                        data-available="${prd.quantity}"
                                        data-price="${prd.price}">
                                    ${prd.productName} - ${prd.price}₫
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="productInfo" style="margin-top: 12px; display: none;">
                        <span class="product-info">
                            💳 Available cards: <span id="availableCount" class="stock-badge">0</span>
                        </span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Card Details</label>
                    <div class="table-container">
                        <table id="add-list">
                            <thead>
                                <tr>
                                    <th style="width: 30%;">Serial Number <span class="required">*</span></th>
                                    <th style="width: 30%;">Card Code <span class="required">*</span></th>
                                    <th style="width: 25%;">Expiration Date</th>
                                    <th style="width: 15%;">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td><input type="text" name="serialNumber[]" class="form-input" required minlength="5" placeholder="Enter serial" /></td>
                                    <td><input type="text" name="cardCode[]" class="form-input" required minlength="6" placeholder="Enter code" /></td>
                                    <td><input type="date" name="expirationDate[]" class="form-input" /></td>
                                    <td><button type="button" class="btn btn-danger" onclick="removeRow(this)">🗑️ Delete</button></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="row-counter">
                        Total rows: <strong id="rowCount">1</strong>
                    </div>
                    <button type="button" id="addrow-btn" class="btn btn-primary btn-add-row">
                        ➕ Add New Row
                    </button>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-success" style="flex: 1;">
                        💾 Save Card List
                    </button>
                    <button type="reset" class="btn btn-danger" onclick="return confirm('Reset all fields?')">
                        🔄 Reset Form
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function updateRowCount() {
            const count = document.getElementById("add-list").getElementsByTagName("tbody")[0].rows.length;
            document.getElementById("rowCount").textContent = count;
        }

        function addRow() {
            const tbody = document.getElementById("add-list").getElementsByTagName("tbody")[0];
            const row = tbody.insertRow();
            row.innerHTML = `
                <td><input type="text" name="serialNumber[]" class="form-input" required minlength="5" placeholder="Enter serial" /></td>
                <td><input type="text" name="cardCode[]" class="form-input" required minlength="6" placeholder="Enter code" /></td>
                <td><input type="date" name="expirationDate[]" class="form-input" /></td>
                <td><button type="button" class="btn btn-danger" onclick="removeRow(this)">🗑️ Delete</button></td>
            `;
            updateRowCount();

            // Focus on the first input of the new row
            row.cells[0].querySelector('input').focus();
        }

        function removeRow(btn) {
            const tbody = document.getElementById("add-list").getElementsByTagName("tbody")[0];
            if (tbody.rows.length > 1) {
                const row = btn.parentNode.parentNode;
                row.parentNode.removeChild(row);
                updateRowCount();
            } else {
                alert('At least one row is required!');
            }
        }

        // Product selection handler
        document.getElementById('productId').addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const available = selectedOption.getAttribute('data-available') || 0;
            const productInfo = document.getElementById('productInfo');
            const availableCount = document.getElementById('availableCount');

            availableCount.textContent = available;
            productInfo.style.display = 'block';

            // Update badge color based on stock
            availableCount.className = 'stock-badge';
            if (available == 0) {
                availableCount.className = 'stock-badge out';
            } else if (available < 10) {
                availableCount.className = 'stock-badge low';
            }
        });

        // Add row button
        document.getElementById('addrow-btn').addEventListener('click', addRow);

        // Form validation
        document.getElementById('cardForm').addEventListener('submit', function(e) {
            const productId = document.getElementById('productId').value;
            if (!productId) {
                e.preventDefault();
                alert('Please select a product!');
                return false;
            }

            const tbody = document.getElementById("add-list").getElementsByTagName("tbody")[0];
            if (tbody.rows.length === 0) {
                e.preventDefault();
                alert('Please add at least one card!');
                return false;
            }

            // Check for duplicate serials in the form
            const serials = [];
            const codes = [];
            const inputs = document.querySelectorAll('input[name="serialNumber[]"]');
            const codeInputs = document.querySelectorAll('input[name="cardCode[]"]');

            for (let i = 0; i < inputs.length; i++) {
                const serial = inputs[i].value.trim();
                const code = codeInputs[i].value.trim();

                if (serials.includes(serial)) {
                    e.preventDefault();
                    alert(`Duplicate serial number found: ${serial}`);
                    inputs[i].focus();
                    return false;
                }

                if (codes.includes(code)) {
                    e.preventDefault();
                    alert(`Duplicate card code found: ${code}`);
                    codeInputs[i].focus();
                    return false;
                }

                serials.push(serial);
                codes.push(code);
            }

            // Confirm before submit
            if (!confirm(`Add ${tbody.rows.length} card(s) to the system?`)) {
                e.preventDefault();
                return false;
            }
        });

        // Initialize row count
        updateRowCount();

        // Keyboard shortcuts
        document.addEventListener('keydown', function(e) {
            // Ctrl/Cmd + Enter to add row
            if ((e.ctrlKey || e.metaKey) && e.key === 'Enter') {
                e.preventDefault();
                addRow();
            }
        });
    </script>
</body>
</html>