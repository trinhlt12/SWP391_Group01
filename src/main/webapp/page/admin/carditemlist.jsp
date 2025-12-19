<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Danh sách tồn kho thẻ | Admin</title>

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
        /* Small tweaks to make carditem list feel consistent with product list */
        .hide-code { font-size: 14px; font-family: monospace; letter-spacing: 2px; color: #64748b; }
        .status-AVAILABLE { color: #15803d; font-weight: 700; }
        .status-SOLD { color: #0b5ed7; font-weight: 700; }
        .status-EXPIRED, .status-LOCKED { color: #be123c; font-weight: 700; }
        .code-toggle { margin-left: 6px; text-decoration: none; color: #374151; }
        .code-toggle:hover { color: #111827; }
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
                    <!-- logo SVG (same as productlist) -->
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
        <div class="aside-content">
            <div class="aside-menu overflow-hidden">
                <nav id="stacked-menu" class="stacked-menu">
                    <ul class="menu">
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/home" class="menu-link">
                                <span class="menu-icon fas fa-home"></span> <span class="menu-text">Trang chủ</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/user-list" class="menu-link">
                                <span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý User</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/products" class="menu-link">
                                <span class="menu-icon fas fa-box"></span> <span class="menu-text">Quản lý Product</span>
                            </a>
                        </li>
                        <li class="menu-item has-active">
                            <a href="${pageContext.request.contextPath}/admin/carditems" class="menu-link">
                                <span class="menu-icon fas fa-warehouse"></span> <span class="menu-text">Kho hàng</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </aside>

    <main class="app-main">
        <div class="wrapper">
            <div class="page">
                <div class="page-inner">

                    <header class="page-title-bar">
                        <div class="d-flex justify-content-between align-items-center">
                            <h1 class="page-title">Danh sách tồn kho thẻ</h1>
                            <a href="${pageContext.request.contextPath}/admin/carditems/add" class="btn btn-success">
                                <i class="fas fa-plus mr-1"></i> Nhập thẻ mới
                            </a>
                        </div>
                    </header>

                    <div class="page-section">
                        <div class="card card-fluid">
                            <div class="card-body">

                                <!-- START: Messages inserted here -->
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
                                <!-- END: Messages inserted here -->

                                <!-- Legacy session-based single message (kept for backward compatibility) -->
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

                                <!-- Filter / Search (styled like product list) -->
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <form action="${pageContext.request.contextPath}/admin/carditems" method="get">
                                            <div class="form-row">
                                                <div class="col-md-4 mb-3">
                                                    <div class="input-group input-group-alt">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><span class="oi oi-magnifying-glass"></span></span>
                                                        </div>
                                                        <input type="text" class="form-control" name="searchSerial"
                                                               value="${searchSerial}" placeholder="Tìm Serial...">
                                                    </div>
                                                </div>


                                                <div class="col-md-2 mb-3">
                                                    <select class="custom-select" name="productId">
                                                        <option value="">-- Tất cả sản phẩm --</option>
                                                        <c:forEach var="prd" items="${products}">
                                                            <option value="${prd.productId}" ${productId == prd.productId ? 'selected' : ''}>
                                                                ${prd.productName} (${prd.price}đ)
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <div class="col-md-1 mb-3">
                                                    <select class="custom-select" name="status">
                                                        <option value="">-- Trạng thái --</option>
                                                        <option value="AVAILABLE" ${status == 'AVAILABLE' ? 'selected' : ''}>Chưa bán</option>
                                                        <option value="SOLD" ${status == 'SOLD' ? 'selected' : ''}>Đã bán</option>
                                                        <option value="EXPIRED" ${status == 'EXPIRED' ? 'selected' : ''}>Hết hạn</option>
                                                        <option value="LOCKED" ${status == 'LOCKED' ? 'selected' : ''}>Bị khóa</option>
                                                    </select>
                                                </div>

                                                <div class="col-md-1 mb-3">
                                                    <select class="custom-select" name="pageSize">
                                                        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                                        <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                                                        <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                                                    </select>
                                                </div>

                                                <div class="col-md-1 mb-3">
                                                    <button type="submit" class="btn btn-secondary w-100" title="Lọc">
                                                        <i class="fas fa-filter"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <!-- Table -->
                                <div class="table-responsive">
                                    <c:choose>
                                        <c:when test="${empty cardList}">
                                            <div class="text-center py-5">
                                                <i class="fas fa-box-open fa-4x text-muted mb-3"></i>
                                                <h5 class="text-muted">Không có thẻ nào</h5>
                                                <p class="text-muted">Hãy nhập thẻ mới để bắt đầu</p>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <table class="table table-hover">
                                                <thead class="thead-light">
                                                <tr>
                                                    <th style="width:60px;">#</th>
                                                    <th>Serial</th>
                                                    <!-- Mã nạp column removed -->
                                                    <th>Sản phẩm</th>
                                                    <th>Giá trị</th>
                                                    <th>Trạng thái</th>
                                                    <th>Ngày hết hạn</th>
                                                    <!-- 'Ngày nhập' column removed -->
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="card" items="${cardList}" varStatus="st">
                                                    <tr>
                                                        <td class="align-middle">${(currentPage - 1) * pageSize + st.index + 1}</td>
                                                        <td class="align-middle"><strong><c:out value="${card.serialNumber}"/></strong></td>

                                                        <!-- removed code display here -->

                                                        <td class="align-middle">
                                                            <c:choose>
                                                                <c:when test="${not empty productMap and not empty productMap[card.productId]}">
                                                                    <c:out value="${productMap[card.productId].productName}"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:out value="${card.productName}"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td class="align-middle">
                                                            <c:choose>
                                                                <c:when test="${not empty productMap and not empty productMap[card.productId]}">
                                                                    <strong class="text-success"><c:out value="${productMap[card.productId].price}"/> đ</strong>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <strong class="text-success"><c:out value="${card.price}"/> đ</strong>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td class="align-middle status-${card.status}"><c:out value="${card.status}"/></td>
                                                        <td class="align-middle">
                                                            <c:if test="${not empty card.expirationDate}">
                                                                <c:out value="${card.expirationDate}"/>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>

                                            <!-- Pagination -->
                                            <c:if test="${totalPages > 1}">
                                                <nav aria-label="Page navigation" class="mt-4">
                                                    <ul class="pagination justify-content-center">
                                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                <a class="page-link"
                                                                   href="${pageContext.request.contextPath}/admin/carditems?page=${i}&pageSize=${pageSize}&searchSerial=${fn:escapeXml(searchSerial)}&searchCode=${fn:escapeXml(searchCode)}&productId=${productId}&status=${fn:escapeXml(status)}">
                                                                    ${i}
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
                                                    <div class="text-center text-muted mt-2">
                                                        <small>Tổng số thẻ: <strong>${totalItems}</strong> | Trang ${currentPage} / ${totalPages}</small>
                                                    </div>
                                                </nav>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

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
    // toggleCode removed — no longer expose card codes

    // Auto hide alert after 5 seconds (same as productlist)
    setTimeout(function() {
        var alert = document.querySelector('.alert');
        if (alert) {
            $(alert).alert('close');
        }
    }, 5000);
</script>

</body>
</html>