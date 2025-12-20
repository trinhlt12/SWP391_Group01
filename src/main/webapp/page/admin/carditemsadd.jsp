<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Card Items</title>

    <link rel="apple-touch-icon" sizes="144x144" href="${pageContext.request.contextPath}/assetAdmin/apple-touch-icon.png">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assetAdmin/favicon.ico">
    <meta name="theme-color" content="#3063A0">
    <link href="https://fonts.googleapis.com/css?family=Fira+Sans:400,500,600" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/vendor/open-iconic/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/vendor/fontawesome/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/theme.min.css" data-skin="default">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/theme-dark.min.css" data-skin="dark">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/custom.css">

    <script>
        var skin = localStorage.getItem('skin') || 'default';
        var disabledSkinStylesheet = document.querySelector('link[data-skin]:not([data-skin="' + skin + '"])');
        if (disabledSkinStylesheet) {
            disabledSkinStylesheet.setAttribute('rel', '');
            disabledSkinStylesheet.setAttribute('disabled', true);
        }
    </script>

    <style>
        .table-fixed thead th { position: sticky; top: 0; z-index: 1; }
        .hide-code { font-family: monospace; letter-spacing: 2px; color: #64748b; }
        .stock-badge { display:inline-block; padding:4px 8px; border-radius: 999px; font-weight:700; font-size:12px; }
        .stock-badge.ok { background:#ecfdf5; color:#065f46; border:1px solid #bbf7d0; }
        .stock-badge.low { background:#fffbeb; color:#92400e; border:1px solid #fde68a; }
        .stock-badge.out { background:#fff1f2; color:#9f1239; border:1px solid #fecaca; }
        .btn-add-row { margin-top: 8px; }
        .required { color: #ef4444; margin-left: 4px; }
        .is-invalid { border-color: #dc3545; }
        .invalid-feedback { display: none; color: #dc3545; font-size: 12px; margin-top: 4px; }
        .is-invalid + .invalid-feedback { display: block; }
    </style>
</head>
<body>
<div class="app">

    <header class="app-header app-header-dark">
        <div class="top-bar">
            <div class="top-bar-brand">
                <button class="hamburger hamburger-squeeze mr-2" type="button" data-toggle="aside-menu">
                    <span class="hamburger-box"><span class="hamburger-inner"></span></span>
                </button>
                <a href="${pageContext.request.contextPath}/home">
                    <svg xmlns="http://www.w3.org/2000/svg" height="28" viewBox="0 0 351 100">...</svg>
                </a>
            </div>
            <div class="top-bar-list">
                <div class="top-bar-item top-bar-item-right px-0 d-none d-sm-flex">
                    <div class="dropdown d-flex">
                        <button class="btn-account d-none d-md-flex" type="button" data-toggle="dropdown">
                            <span class="user-avatar user-avatar-md">
                                <img src="${pageContext.request.contextPath}/assetAdmin/assets/images/avatars/profile.jpg" alt="">
                            </span>
                            <span class="account-summary pr-lg-4 d-none d-lg-block">
                                <span class="account-name">Admin</span>
                                <span class="account-description">Quản lý</span>
                            </span>
                        </button>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#"><span class="dropdown-icon oi oi-person"></span> Profile</a>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/logout"><span class="dropdown-icon oi oi-account-logout"></span> Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <aside class="app-aside app-aside-expand-md app-aside-light">
        <!-- aside menu omitted for brevity -->
    </aside>

    <main class="app-main">
        <div class="wrapper">
            <div class="page">
                <div class="page-inner">

                    <header class="page-title-bar">
                        <div class="d-flex justify-content-between align-items-center">
                            <h1 class="page-title">Nhập thẻ</h1>
                            <a href="${pageContext.request.contextPath}/admin/carditems" class="btn btn-secondary">
                                <i class="fas fa-arrow-left mr-1"></i> Quay lại
                            </a>
                        </div>
                    </header>

                    <div class="page-section">
                        <div class="card card-fluid">
                            <div class="card-body">

                                <!-- Messages -->
                                <c:if test="${not empty errorMessages}">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <c:forEach var="m" items="${errorMessages}">
                                            <div><c:out value="${m}" /></div>
                                        </c:forEach>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>

                                <c:if test="${not empty successMessages}">
                                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                                        <c:forEach var="m" items="${successMessages}">
                                            <div><c:out value="${m}" /></div>
                                        </c:forEach>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>

                                <c:if test="${not empty warningMessages}">
                                    <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                        <c:forEach var="m" items="${warningMessages}">
                                            <div><c:out value="${m}" /></div>
                                        </c:forEach>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                </c:if>

                                <c:if test="${not empty sessionScope.message}">
                                    <div class="alert alert-${sessionScope.messageType == 'success' ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                                        <c:out value="${sessionScope.message}"/>
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <c:remove var="message" scope="session"/>
                                    <c:remove var="messageType" scope="session"/>
                                </c:if>

                                <!-- FORM -->
                                <form action="${pageContext.request.contextPath}/admin/carditems/add" method="post" id="cardForm" novalidate>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label class="font-weight-bold">Sản phẩm <span class="required">*</span></label>
                                            <select name="productId" id="productId" class="custom-select" required>
                                                <option value="">-- Chọn sản phẩm --</option>
                                                <c:forEach var="prd" items="${products}">
                                                    <option value="${prd.productId}"
                                                            data-available="${prd.quantity}" data-price="${prd.price}"
                                                            <c:if test="${param.productId == prd.productId}">selected</c:if>>
                                                        ${prd.productName} — ${prd.price} VNĐ
                                                    </option>
                                                </c:forEach>
                                            </select>
                                            <small class="text-muted d-block mt-2">
                                                <span>Available: </span>
                                                <span id="availableCount" class="stock-badge ok">0</span>
                                            </small>
                                            <div class="invalid-feedback" id="productError">Vui lòng chọn sản phẩm.</div>
                                        </div>
                                        <input type="hidden" name="defaultStatus" value="AVAILABLE" />
                                    </div>

                                    <hr/>

                                    <c:set var="serialList" value="${empty paramValues['serialNumber[]'] ? paramValues.serialNumber : paramValues['serialNumber[]']}" />
                                    <c:set var="codeList" value="${empty paramValues['cardCode[]'] ? paramValues.cardCode : paramValues['cardCode[]']}" />
                                    <c:set var="dateList" value="${empty paramValues['expirationDate[]'] ? paramValues.expirationDate : paramValues['expirationDate[]']}" />
                                    <c:set var="rowLen" value="${fn:length(serialList)}" />

                                    <div class="table-responsive">
                                        <table class="table table-hover table-fixed" id="add-list">
                                            <thead class="thead-light">
                                                <tr>
                                                    <th style="width: 34%;">Serial <span class="required">*</span></th>
                                                    <th style="width: 34%;">Mã thẻ <span class="required">*</span></th>
                                                    <th style="width: 20%;">Ngày hết hạn</th>
                                                    <th style="width: 12%;" class="text-center">Hành động</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <!-- Render lại dữ liệu đã nhập nếu có, ngược lại hiển thị một hàng trống -->
                                                <c:choose>
                                                    <c:when test="${rowLen gt 0}">
                                                        <c:forEach var="s" items="${serialList}" varStatus="st">
                                                            <tr>
                                                                <td>
                                                                    <input type="text" name="serialNumber[]" class="form-control serial-input"
                                                                           required minlength="3" maxlength="64"
                                                                           pattern="^[A-Za-z0-9_-]{3,64}$"
                                                                           title="Chỉ chữ/số/dấu gạch dưới/gạch ngang. Độ dài 3–64."
                                                                           placeholder="Serial"
                                                                           value="${fn:trim(s)}" />
                                                                    <div class="invalid-feedback">Serial 3–64 ký tự, chỉ chữ/số/-/_</div>
                                                                </td>
                                                                <td>
                                                                    <input type="text" name="cardCode[]" class="form-control code-input"
                                                                           required minlength="4" maxlength="64"
                                                                           pattern="^[A-Za-z0-9_-]{4,64}$"
                                                                           title="Chỉ chữ/số/dấu gạch dưới/gạch ngang. Độ dài 4–64."
                                                                           placeholder="Code"
                                                                           value="${fn:trim(codeList[st.index])}" />
                                                                    <div class="invalid-feedback">Mã thẻ 4–64 ký tự, chỉ chữ/số/-/_</div>
                                                                </td>
                                                                <td>
                                                                    <input type="date" name="expirationDate[]" class="form-control date-input"
                                                                           value="${dateList[st.index]}" />
                                                                    <div class="invalid-feedback">Ngày hết hạn phải từ hôm nay trở đi.</div>
                                                                </td>
                                                                <td class="text-center">
                                                                    <button type="button" class="btn btn-sm btn-danger" onclick="removeRow(this)">🗑</button>
                                                                </td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <tr>
                                                            <td>
                                                                <input type="text" name="serialNumber[]" class="form-control serial-input"
                                                                       required minlength="3" maxlength="64"
                                                                       pattern="^[A-Za-z0-9_-]{3,64}$"
                                                                       title="Chỉ chữ/số/dấu gạch dưới/gạch ngang. Độ dài 3–64."
                                                                       placeholder="Serial" />
                                                                <div class="invalid-feedback">Serial 3–64 ký tự, chỉ chữ/số/-/_</div>
                                                            </td>
                                                            <td>
                                                                <input type="text" name="cardCode[]" class="form-control code-input"
                                                                       required minlength="4" maxlength="64"
                                                                       pattern="^[A-Za-z0-9_-]{4,64}$"
                                                                       title="Chỉ chữ/số/dấu gạch dưới/gạch ngang. Độ dài 4–64."
                                                                       placeholder="Code" />
                                                                <div class="invalid-feedback">Mã thẻ 4–64 ký tự, chỉ chữ/số/-/_</div>
                                                            </td>
                                                            <td>
                                                                <input type="date" name="expirationDate[]" class="form-control date-input" />
                                                                <div class="invalid-feedback">Ngày hết hạn phải từ hôm nay trở đi.</div>
                                                            </td>
                                                            <td class="text-center">
                                                                <button type="button" class="btn btn-sm btn-danger" onclick="removeRow(this)">🗑</button>
                                                            </td>
                                                        </tr>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tbody>
                                        </table>
                                    </div>

                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <button type="button" id="addrow-btn" class="btn btn-primary btn-add-row">
                                                <i class="fas fa-plus mr-1"></i> Thêm hàng
                                            </button>
                                            <span class="ml-3 text-muted">Tổng hàng:
                                                <strong id="rowCount">
                                                    <c:out value="${rowLen gt 0 ? rowLen : 1}" />
                                                </strong>
                                            </span>
                                        </div>
                                        <div class="form-actions">
                                            <button type="submit" class="btn btn-success" id="submitBtn">
                                                <i class="fas fa-save mr-1"></i> Lưu
                                            </button>
                                            <button type="reset" class="btn btn-secondary" onclick="return confirm('Bạn có chắc muốn đặt lại form?')">
                                                Reset
                                            </button>
                                        </div>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </main>

</div>

<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/pace/pace.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/stacked-menu/stacked-menu.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/javascript/theme.min.js"></script>

<script>
    function updateRowCount() {
        const tbody = document.querySelector('#add-list tbody');
        document.getElementById('rowCount').textContent = tbody.rows.length;
    }

    function addRow() {
        const tbody = document.querySelector('#add-list tbody');
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <input type="text" name="serialNumber[]" class="form-control serial-input"
                       required minlength="3" maxlength="64"
                       pattern="^[A-Za-z0-9_-]{3,64}$"
                       title="Chỉ chữ/số/dấu gạch dưới/gạch ngang. Độ dài 3–64."
                       placeholder="Serial" />
                <div class="invalid-feedback">Serial 3–64 ký tự, chỉ chữ/số-/_</div>
            </td>
            <td>
                <input type="text" name="cardCode[]" class="form-control code-input"
                       required minlength="4" maxlength="64"
                       pattern="^[A-Za-z0-9_-]{4,64}$"
                       title="Chỉ chữ/số/dấu gạch dưới/gạch ngang. Độ dài 4–64."
                       placeholder="Code" />
                <div class="invalid-feedback">Mã thẻ 4–64 ký tự, chỉ chữ/số-/_</div>
            </td>
            <td>
                <input type="date" name="expirationDate[]" class="form-control date-input" />
                <div class="invalid-feedback">Ngày hết hạn phải từ hôm nay trở đi.</div>
            </td>
            <td class="text-center"><button type="button" class="btn btn-sm btn-danger" onclick="removeRow(this)">🗑</button></td>
        `;
        tbody.appendChild(tr);
        updateRowCount();
        tr.querySelector('input').focus();
        attachInputHandlers(tr);
    }

    function removeRow(btn) {
        const tbody = document.querySelector('#add-list tbody');
        if (tbody.rows.length > 1) {
            const row = btn.closest('tr');
            row.parentNode.removeChild(row);
            updateRowCount();
        } else {
            alert('Ít nhất phải có 1 hàng.');
        }
    }

    document.getElementById('addrow-btn').addEventListener('click', addRow);

    // Update available count when product changes
    const productSelect = document.getElementById('productId');
    const availableCountEl = document.getElementById('availableCount');

    if (productSelect) {
        // initialize badge from selected on load
        (function initAvailable() {
            const opt = productSelect.options[productSelect.selectedIndex];
            const available = opt ? parseInt(opt.getAttribute('data-available') || '0', 10) : 0;
            availableCountEl.textContent = available;
            availableCountEl.className = 'stock-badge';
            if (available === 0) availableCountEl.classList.add('out');
            else if (available < 10) availableCountEl.classList.add('low');
            else availableCountEl.classList.add('ok');
        })();

        productSelect.addEventListener('change', function () {
            const opt = this.options[this.selectedIndex];
            const available = opt ? parseInt(opt.getAttribute('data-available') || '0', 10) : 0;
            availableCountEl.textContent = available;
            availableCountEl.className = 'stock-badge';
            if (available === 0) availableCountEl.classList.add('out');
            else if (available < 10) availableCountEl.classList.add('low');
            else availableCountEl.classList.add('ok');

            if (!this.value) {
                this.classList.add('is-invalid');
                document.getElementById('productError').style.display = 'block';
            } else {
                this.classList.remove('is-invalid');
                document.getElementById('productError').style.display = 'none';
            }
        });
    }

    function trimValue(el) {
        if (el && typeof el.value === 'string') {
            el.value = el.value.trim();
        }
    }

    function validateDateInput(input) {
        const val = input.value;
        if (!val) {
            input.classList.remove('is-invalid');
            return true;
        }
        const today = new Date();
        today.setHours(0,0,0,0);
        const d = new Date(val);
        if (isNaN(d.getTime()) || d < today) {
            input.classList.add('is-invalid');
            return false;
        } else {
            input.classList.remove('is-invalid');
            return true;
        }
    }

    function validateTextInput(input, minLen) {
        trimValue(input);
        const v = input.value;
        const patternAttr = input.getAttribute('pattern');
        const pattern = patternAttr ? new RegExp(patternAttr) : null;
        const maxLen = parseInt(input.getAttribute('maxlength') || '64', 10);
        const ok = v.length >= minLen && v.length <= maxLen && (!pattern || pattern.test(v));
        if (!ok) input.classList.add('is-invalid');
        else input.classList.remove('is-invalid');
        return ok;
    }

    function attachInputHandlers(scope) {
        const serials = (scope || document).querySelectorAll('.serial-input');
        const codes = (scope || document).querySelectorAll('.code-input');
        const dates = (scope || document).querySelectorAll('.date-input');

        serials.forEach(inp => {
            inp.addEventListener('blur', () => validateTextInput(inp, 3));
            inp.addEventListener('input', () => inp.classList.remove('is-invalid'));
        });
        codes.forEach(inp => {
            inp.addEventListener('blur', () => validateTextInput(inp, 4));
            inp.addEventListener('input', () => inp.classList.remove('is-invalid'));
        });
        dates.forEach(inp => {
            inp.addEventListener('change', () => validateDateInput(inp));
        });
    }

    attachInputHandlers(document);

    // Submit validation + trim
    document.getElementById('cardForm').addEventListener('submit', function (e) {
        document.querySelectorAll('input[type="text"]').forEach(trimValue);

        const productIdEl = document.getElementById('productId');
        const productId = productIdEl.value;
        if (!productId) {
            e.preventDefault();
            productIdEl.classList.add('is-invalid');
            document.getElementById('productError').style.display = 'block';
            alert('Vui lòng chọn sản phẩm.');
            return false;
        }

        const serials = [];
        const codes = [];
        const serialInputs = document.querySelectorAll('input[name="serialNumber[]"]');
        const codeInputs = document.querySelectorAll('input[name="cardCode[]"]');
        const dateInputs = document.querySelectorAll('input[name="expirationDate[]"]');

        for (let i = 0; i < serialInputs.length; i++) {
            const sInp = serialInputs[i];
            const cInp = codeInputs[i];
            const dInp = dateInputs[i];

            const sOk = validateTextInput(sInp, 3);
            const cOk = validateTextInput(cInp, 4);
            const dOk = validateDateInput(dInp);

            if (!sOk || !cOk || !dOk) {
                e.preventDefault();
                alert(`Hàng ${i+1} có lỗi. Vui lòng kiểm tra lại các trường đánh dấu đỏ.`);
                return false;
            }

            const s = sInp.value;
            const c = cInp.value;

            if (serials.includes(s)) {
                e.preventDefault();
                sInp.classList.add('is-invalid');
                alert('Trùng serial trong form: ' + s);
                return false;
            }
            if (codes.includes(c)) {
                e.preventDefault();
                cInp.classList.add('is-invalid');
                alert('Trùng mã thẻ trong form: ' + c);
                return false;
            }
            serials.push(s);
            codes.push(c);
        }

        if (!confirm(`Bạn có chắc muốn thêm ${serials.length} thẻ?`)) {
            e.preventDefault();
            return false;
        }

        document.getElementById('submitBtn').disabled = true;
    });

    // initialize row count based on rendered rows
    updateRowCount();
</script>
</body>
</html>