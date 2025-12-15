<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dịch vụ - Em Bán Thẻ</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: #f9fafb;
            min-height: 100vh;
        }

        .service-container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }

        /* Breadcrumb */
        .breadcrumb {
            margin-bottom: 20px;
            color: #6b7280;
            font-size: 0.9rem;
        }

        .breadcrumb a {
            color: #059669;
            text-decoration: none;
        }

        .breadcrumb a:hover {
            text-decoration: underline;
        }

        /* Main Layout: 3 columns */
        .main-layout {
            display: grid;
            grid-template-columns: 280px 1fr 350px;
            gap: 20px;
        }

        /* ========== LEFT SIDEBAR - CATEGORIES ========== */
        .category-sidebar {
            background: white;
            border-radius: 15px;
            padding: 20px;
            height: fit-content;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            position: sticky;
            top: 20px;
        }

        .sidebar-title {
            font-size: 1.2rem;
            font-weight: 700;
            color: #059669;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 2px solid #e5e7eb;
        }

        .category-item {
            padding: 15px;
            margin-bottom: 10px;
            border-radius: 10px;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 12px;
            color: #374151;
            font-weight: 500;
        }

        .category-item:hover {
            background: #f0fdf4;
            color: #059669;
        }

        .category-item.active {
            background: #10B981;
            color: white;
        }

        .category-icon {
            font-size: 1.3rem;
        }

        /* ========== CENTER - PRODUCT GRID ========== */
        .product-section {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
        }

        .section-header {
            margin-bottom: 25px;
        }

        .section-header h2 {
            color: #059669;
            font-size: 1.8rem;
            margin-bottom: 8px;
        }

        .section-subtitle {
            color: #6b7280;
            font-size: 0.95rem;
        }

        /* Product Grid */
        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
            gap: 20px;
        }

        .product-card {
            background: #f9fafb;
            border: 2px solid #e5e7eb;
            border-radius: 15px;
            padding: 20px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            position: relative;
        }

        .product-card:hover {
            border-color: #10B981;
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(16, 185, 129, 0.15);
        }

        .product-card.selected {
            border-color: #10B981;
            background: #f0fdf4;
        }

        .product-logo {
            width: 80px;
            height: 80px;
            margin: 0 auto 15px;
            background: white;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2rem;
            border: 2px solid #e5e7eb;
        }

        .product-name {
            font-size: 0.85rem;
            color: #6b7280;
            margin-bottom: 10px;
        }

        .product-value {
            font-size: 1.5rem;
            font-weight: 700;
            color: #059669;
            margin-bottom: 8px;
        }

        .product-price {
            font-size: 0.9rem;
            color: #374151;
            font-weight: 500;
        }

        /* Selected badge */
        .selected-badge {
            position: absolute;
            top: 10px;
            right: 10px;
            background: #10B981;
            color: white;
            width: 24px;
            height: 24px;
            border-radius: 50%;
            display: none;
            align-items: center;
            justify-content: center;
            font-size: 0.9rem;
        }

        .product-card.selected .selected-badge {
            display: flex;
        }

        /* ========== RIGHT SIDEBAR - ORDER SUMMARY ========== */
        .order-sidebar {
            background: white;
            border-radius: 15px;
            padding: 25px;
            height: fit-content;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            position: sticky;
            top: 20px;
        }

        .order-title {
            font-size: 1.3rem;
            font-weight: 700;
            color: #059669;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 2px solid #e5e7eb;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-label {
            display: block;
            color: #374151;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 0.9rem;
        }

        .form-input {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e5e7eb;
            border-radius: 10px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background: #f9fafb;
        }

        .form-input:focus {
            outline: none;
            border-color: #10B981;
            background: white;
        }

        .form-input:read-only {
            background: #f3f4f6;
            color: #6b7280;
            cursor: not-allowed;
        }

        /* Quantity Control */
        .quantity-control {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .quantity-btn {
            width: 40px;
            height: 40px;
            border: 2px solid #10B981;
            background: white;
            color: #10B981;
            border-radius: 8px;
            font-size: 1.2rem;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .quantity-btn:hover {
            background: #10B981;
            color: white;
        }

        .quantity-btn:active {
            transform: scale(0.95);
        }

        .quantity-input {
            flex: 1;
            text-align: center;
            font-weight: 600;
            font-size: 1.1rem;
        }

        /* Total Price */
        .total-section {
            background: #f0fdf4;
            padding: 20px;
            border-radius: 12px;
            margin: 25px 0;
        }

        .total-label {
            color: #374151;
            font-size: 0.9rem;
            margin-bottom: 8px;
        }

        .total-price {
            font-size: 2rem;
            font-weight: 700;
            color: #059669;
        }

        /* Submit Button */
        .submit-btn {
            width: 100%;
            padding: 15px;
            background: linear-gradient(135deg, #10B981 0%, #059669 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(16, 185, 129, 0.3);
        }

        .submit-btn:active {
            transform: translateY(0);
        }

        .submit-btn:disabled {
            background: #d1d5db;
            cursor: not-allowed;
            transform: none;
        }

        /* Empty State */
        .empty-selection {
            text-align: center;
            padding: 40px 20px;
            color: #9ca3af;
        }

        .empty-selection .icon {
            font-size: 3rem;
            margin-bottom: 15px;
            opacity: 0.5;
        }

        /* Responsive */
        @media (max-width: 1200px) {
            .main-layout {
                grid-template-columns: 250px 1fr 320px;
            }
        }

        @media (max-width: 992px) {
            .main-layout {
                grid-template-columns: 1fr;
            }

            .category-sidebar,
            .order-sidebar {
                position: static;
            }
        }

        @media (max-width: 768px) {
            .product-grid {
                grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            }
        }
    </style>
</head>
<body>
<jsp:include page="/header.jsp"/>

<div class="service-container">

    <!-- Main 3-column Layout -->
    <div class="main-layout">

        <!-- LEFT: Category Sidebar -->
        <aside class="category-sidebar">
            <h3 class="sidebar-title">Danh Mục</h3>

            <div class="category-item active" onclick="switchCategory('phone')">
                <span class="category-icon">📱</span>
                <span>Thẻ điện thoại</span>
            </div>

            <div class="category-item" onclick="switchCategory('game')">
                <span class="category-icon">🎮</span>
                <span>Thẻ game</span>
            </div>
        </aside>

        <!-- CENTER: Product Grid -->
        <main class="product-section">
            <div class="section-header">
                <h2 id="categoryTitle">Thẻ Điện Thoại</h2>
                <p class="section-subtitle">Chọn mệnh giá thẻ bạn muốn mua</p>
            </div>

            <!-- Phone Cards Grid -->
            <div class="product-grid" id="phoneProducts">
                <div class="product-card" onclick="selectProduct('Thẻ Viettel 10.000đ', 10000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #e30613;">📞</div>
                    <div class="product-name">Viettel</div>
                    <div class="product-value">10.000₫</div>
                    <div class="product-price">10.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Viettel 20.000đ', 20000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #e30613;">📞</div>
                    <div class="product-name">Viettel</div>
                    <div class="product-value">20.000₫</div>
                    <div class="product-price">20.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Viettel 50.000đ', 50000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #e30613;">📞</div>
                    <div class="product-name">Viettel</div>
                    <div class="product-value">50.000₫</div>
                    <div class="product-price">50.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Viettel 100.000đ', 100000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #e30613;">📞</div>
                    <div class="product-name">Viettel</div>
                    <div class="product-value">100.000₫</div>
                    <div class="product-price">100.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Mobifone 10.000đ', 10000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #1c4c9e;">📱</div>
                    <div class="product-name">Mobifone</div>
                    <div class="product-value">10.000₫</div>
                    <div class="product-price">10.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Mobifone 20.000đ', 20000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #1c4c9e;">📱</div>
                    <div class="product-name">Mobifone</div>
                    <div class="product-value">20.000₫</div>
                    <div class="product-price">20.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Vinaphone 10.000đ', 10000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #8b1f8a;">📞</div>
                    <div class="product-name">Vinaphone</div>
                    <div class="product-value">10.000₫</div>
                    <div class="product-price">10.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Vinaphone 50.000đ', 50000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #8b1f8a;">📞</div>
                    <div class="product-name">Vinaphone</div>
                    <div class="product-value">50.000₫</div>
                    <div class="product-price">50.000₫</div>
                </div>
            </div>

            <!-- Game Cards Grid (Hidden by default) -->
            <div class="product-grid" id="gameProducts" style="display: none;">
                <div class="product-card" onclick="selectProduct('Thẻ Garena 20.000đ', 20000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #ff4500;">🎮</div>
                    <div class="product-name">Garena</div>
                    <div class="product-value">20.000₫</div>
                    <div class="product-price">20.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Garena 50.000đ', 50000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #ff4500;">🎮</div>
                    <div class="product-name">Garena</div>
                    <div class="product-value">50.000₫</div>
                    <div class="product-price">50.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Garena 100.000đ', 100000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #ff4500;">🎮</div>
                    <div class="product-name">Garena</div>
                    <div class="product-value">100.000₫</div>
                    <div class="product-price">100.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Steam 100.000đ', 100000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #171a21;">🎯</div>
                    <div class="product-name">Steam</div>
                    <div class="product-value">100.000₫</div>
                    <div class="product-price">100.000₫</div>
                </div>

                <div class="product-card" onclick="selectProduct('Thẻ Steam 200.000đ', 200000)">
                    <div class="selected-badge">✓</div>
                    <div class="product-logo" style="color: #171a21;">🎯</div>
                    <div class="product-name">Steam</div>
                    <div class="product-value">200.000₫</div>
                    <div class="product-price">200.000₫</div>
                </div>
            </div>
        </main>

        <!-- RIGHT: Order Summary -->
        <aside class="order-sidebar">
            <h3 class="order-title">Thông Tin Đơn Hàng</h3>

            <!-- Empty State (shown when no product selected) -->
            <div class="empty-selection" id="emptyState">
                <div class="icon">🛒</div>
                <p>Chưa có sản phẩm nào được chọn</p>
            </div>

            <!-- Order Form (hidden by default) -->
            <form id="orderForm" style="display: none;" action="<%= request.getContextPath() %>/purchase" method="POST">

                <div class="form-group">
                    <label class="form-label">Tên sản phẩm</label>
                    <input type="text" id="productName" name="productName" class="form-input" readonly>
                </div>

                <div class="form-group">
                    <label class="form-label">Số lượng</label>
                    <div class="quantity-control">
                        <button type="button" class="quantity-btn" onclick="decreaseQuantity()">−</button>
                        <input type="number" id="quantity" name="quantity" class="form-input quantity-input" value="1" min="1" max="100" readonly>
                        <button type="button" class="quantity-btn" onclick="increaseQuantity()">+</button>
                    </div>
                </div>

                <div class="total-section">
                    <div class="total-label">Tổng tiền</div>
                    <div class="total-price" id="totalPrice">0₫</div>
                </div>

                <input type="hidden" id="unitPrice" name="unitPrice">
                <input type="hidden" id="totalAmount" name="totalAmount">

                <button type="submit" class="submit-btn">
                    Đồng ý mua
                </button>
            </form>
        </aside>

    </div>
</div>

<script>
    let selectedProduct = null;
    let unitPrice = 0;
    let quantity = 1;

    // Switch category
    function switchCategory(category) {
        // Update category buttons
        document.querySelectorAll('.category-item').forEach(item => {
            item.classList.remove('active');
        });
        event.target.closest('.category-item').classList.add('active');

        // Show/hide product grids
        if (category === 'phone') {
            document.getElementById('phoneProducts').style.display = 'grid';
            document.getElementById('gameProducts').style.display = 'none';
            document.getElementById('categoryTitle').textContent = 'Thẻ Điện Thoại';
        } else {
            document.getElementById('phoneProducts').style.display = 'none';
            document.getElementById('gameProducts').style.display = 'grid';
            document.getElementById('categoryTitle').textContent = 'Thẻ Game';
        }

        // Reset selection
        clearSelection();
    }

    // Select product
    function selectProduct(name, price) {
        // Remove previous selection
        document.querySelectorAll('.product-card').forEach(card => {
            card.classList.remove('selected');
        });

        // Add selection to clicked card
        event.currentTarget.classList.add('selected');

        // Store product info
        selectedProduct = name;
        unitPrice = price;
        quantity = 1;

        // Update form
        document.getElementById('productName').value = name;
        document.getElementById('quantity').value = quantity;
        document.getElementById('unitPrice').value = price;

        // Show form, hide empty state
        document.getElementById('emptyState').style.display = 'none';
        document.getElementById('orderForm').style.display = 'block';

        // Update total
        updateTotal();
    }

    // Increase quantity
    function increaseQuantity() {
        const input = document.getElementById('quantity');
        if (input.value < 100) {
            quantity = parseInt(input.value) + 1;
            input.value = quantity;
            updateTotal();
        }
    }

    // Decrease quantity
    function decreaseQuantity() {
        const input = document.getElementById('quantity');
        if (input.value > 1) {
            quantity = parseInt(input.value) - 1;
            input.value = quantity;
            updateTotal();
        }
    }

    // Update total price
    function updateTotal() {
        const total = unitPrice * quantity;
        document.getElementById('totalPrice').textContent =
            total.toLocaleString('vi-VN') + '₫';
        document.getElementById('totalAmount').value = total;
    }

    // Clear selection
    function clearSelection() {
        selectedProduct = null;
        document.querySelectorAll('.product-card').forEach(card => {
            card.classList.remove('selected');
        });
        document.getElementById('emptyState').style.display = 'block';
        document.getElementById('orderForm').style.display = 'none';
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>