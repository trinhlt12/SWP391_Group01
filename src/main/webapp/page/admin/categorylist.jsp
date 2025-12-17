<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Quản lý loại thẻ | Admin</title>

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
</head>
<body>
<div class="app">

    <!-- Header (same as productlist) -->
    <header class="app-header app-header-dark">
        <div class="top-bar">
            <div class="top-bar-brand">
                <button class="hamburger hamburger-squeeze mr-2" type="button" data-toggle="aside-menu">
                    <span class="hamburger-box"><span class="hamburger-inner"></span></span>
                </button>
                <a href="${pageContext.request.contextPath}/home">
                    <!-- You can keep your SVG logo here -->
                    <svg xmlns="http://www.w3.org/2000/svg" height="28" viewBox="0 0 351 100">
                        <defs><path id="a" d="M156.538 45.644v1.04a6.347 6.347 0 0 1-1.847 3.98L127.708 77.67a6.338 6.338 0 0 1-3.862 1.839h-1.272a6.34 6.34 0 0 1-3.862-1.839L91.728 50.664a6.353 6.353 0 0 1 0-9l9.11-9.117-2.136-2.138a3.171 3.171 0 0 0-4.498 0L80.711 43.913a3.177 3.177 0 0 0-.043 4.453l-.002.003.048.047 24.733 24.754-4.497 4.5a6.339 6.339 0 0 1-3.863 1.84h-1.27a6.337 6.337 0 0 1-3.863-1.84L64.971 50.665a6.353 6.353 0 0 1 0-9l26.983-27.008a6.336 6.336 0 0 1 4.498-1.869c1.626 0 3.252.622 4.498 1.87l26.986 27.006a6.353 6.353 0 0 1 0 9l-9.11 9.117 2.136 2.138a3.171 3.171 0 0 0 4.498 0l13.49-13.504a3.177 3.177 0 0 0 .046-4.453l.002-.002-.047-.048-24.737-24.754 4.498-4.5a6.344 6.344 0 0 1 8.996 0l26.983 27.006a6.347 6.347 0 0 1 1.847 3.98zm-46.707-4.095l-2.362 2.364a3.178 3.178 0 0 0 0 4.501l2.362 2.364 2.361-2.364a3.178 3.178 0 0 0 0-4.501l-2.361-2.364z"></path></defs>
                        <g fill="none" fill-rule="evenodd"><path fill="currentColor" fill-rule="nonzero" d="M39.252 80.385c-13.817 0-21.06-8.915-21.06-22.955V13.862H.81V.936h33.762V58.1c0 6.797 4.346 9.026 9.026 9.026 2.563 0 5.237-.446 8.58-1.783l3.677 12.034c-5.794 1.894-9.694 3.009-16.603 3.009zM164.213 99.55V23.78h13.372l1.225 5.571h.335c4.457-4.011 10.585-6.908 16.491-6.908 13.817 0 22.174 11.031 22.174 28.08 0 18.943-11.588 29.863-23.957 29.863-4.903 0-9.694-2.117-13.594-6.017h-.446l.78 9.025V99.55h-16.38zm25.852-32.537c6.128 0 10.92-4.903 10.92-16.268 0-9.917-3.232-14.932-10.14-14.932-3.566 0-6.797 1.56-10.252 5.126v22.397c3.12 2.674 6.686 3.677 9.472 3.677zm69.643 13.372c-17.272 0-30.643-10.586-30.643-28.972 0-18.163 13.928-28.971 28.748-28.971 17.049 0 26.075 11.477 26.075 26.52 0 3.008-.558 6.017-.78 7.354h-37.663c1.56 8.023 7.465 11.589 16.491 11.589 5.014 0 9.36-1.337 14.263-3.9l5.46 9.917c-6.351 4.011-14.597 6.463-21.951 6.463zm-1.338-45.463c-6.462 0-11.031 3.454-12.702 10.363h23.622c-.78-6.797-4.568-10.363-10.92-10.363zm44.238 44.126V23.779h13.371l1.337 12.034h.334c5.46-9.025 13.595-13.371 22.398-13.371 4.902 0 7.465.78 10.697 2.228l-3.343 13.706c-3.454-1.003-5.683-1.56-9.806-1.56-6.797 0-13.928 3.566-18.608 13.483v28.749h-16.38z"></path><use class="fill-warning" xlink:href="#a"></use></g>
                    </svg>
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

    <!-- Aside (same menu as productlist) -->
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
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/providers" class="menu-link">
                                <span class="menu-icon fas fa-plus-circle"></span> <span class="menu-text">Quản lý nhà mạng</span>
                            </a>
                        </li>
                        <li class="menu-item has-active">
                            <a href="${pageContext.request.contextPath}/admin/category" class="menu-link">
                                <span class="menu-icon fas fa-plus-circle"></span> <span class="menu-text">Quản lý loại thẻ</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/admin/carditems" class="menu-link">
                                <span class="menu-icon fas fa-warehouse"></span> <span class="menu-text">Kho hàng</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </aside>

    <!-- Main content -->
    <main class="app-main">
        <div class="wrapper">
            <div class="page">
                <div class="page-inner">

                    <header class="page-title-bar">
                        <div class="d-flex justify-content-between align-items-center">
                            <h1 class="page-title">Danh sách loại thẻ</h1>
                            <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-success">
                                <i class="fas fa-plus mr-1"></i> Thêm mới
                            </a>
                        </div>
                    </header>

                    <div class="page-section">
                        <div class="card card-fluid">
                            <div class="card-body">
                                <!-- Alert Messages -->
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

                                <!-- Optional filter/search (kept simple for categories) -->
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <form action="${pageContext.request.contextPath}/admin/category" method="get">
                                            <div class="form-row">
                                                <div class="col-md-6 mb-3">
                                                    <div class="input-group input-group-alt">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><span class="oi oi-magnifying-glass"></span></span>
                                                        </div>
                                                        <input type="text" class="form-control" name="search"
                                                               value="${param.search}"
                                                               placeholder="Tìm kiếm loại thẻ...">
                                                    </div>
                                                </div>

                                                <div class="col-md-2 mb-3 ml-auto">
                                                    <button type="submit" class="btn btn-secondary w-100" title="Lọc">
                                                        <i class="fas fa-filter"></i> Lọc
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>

                                <!-- Table -->
                                <div class="table-responsive">
                                    <c:choose>
                                        <c:when test="${empty list}">
                                            <div class="text-center py-5">
                                                <i class="fas fa-folder-open fa-4x text-muted mb-3"></i>
                                                <h5 class="text-muted">Không tìm thấy loại thẻ nào</h5>
                                                <p class="text-muted">Hãy thêm loại thẻ mới để bắt đầu</p>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <table class="table table-hover">
                                                <thead class="thead-light">
                                                <tr>
                                                    <th style="width: 60px;">ID</th>
                                                    <th>Tên loại thẻ</th>
                                                    <th style="width: 160px;" class="text-right">Hành động</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach var="c" items="${list}">
                                                    <tr>
                                                        <td class="align-middle font-weight-bold">#${c.categoryId}</td>
                                                        <td class="align-middle"><strong>${c.categoryName}</strong></td>
                                                        <td class="align-middle text-right">
                                                            <a href="${pageContext.request.contextPath}/admin/category/update?id=${c.categoryId}"
                                                               class="btn btn-sm btn-icon btn-secondary"
                                                               title="Cập nhật">
                                                                <i class="fa fa-pencil-alt"></i>
                                                            </a>

                                                            <a href="${pageContext.request.contextPath}/admin/category/delete?id=${c.categoryId}"
                                                               class="btn btn-sm btn-icon btn-secondary"
                                                               onclick="return confirm('Bạn có chắc muốn xóa loại thẻ này?');"
                                                               title="Xóa">
                                                                <i class="far fa-trash-alt"></i>
                                                            </a>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>

                                            <!-- Optional pagination area (render only if values exist) -->
                                            <c:if test="${not empty totalPages and totalPages > 1}">
                                                <nav aria-label="Page navigation" class="mt-4">
                                                    <ul class="pagination justify-content-center">
                                                        <c:forEach var="i" begin="1" end="${totalPages}">
                                                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                                <a class="page-link"
                                                                   href="${pageContext.request.contextPath}/admin/category?page=${i}&search=${param.search}">
                                                                    ${i}
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                    </ul>
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
    // Auto hide alert after 5 seconds
    setTimeout(function() {
        var alert = document.querySelector('.alert');
        if (alert) {
            $(alert).alert('close');
        }
    }, 5000);
</script>

</body>
</html>