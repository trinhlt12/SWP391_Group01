    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title> Dashboard | Admin EMBANTHE </title>
        <link href="${pageContext.request.contextPath}/image/Logo.png" rel="icon">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/vendor/fontawesome/css/all.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/vendor/open-iconic/css/open-iconic-bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/theme.min.css" data-skin="default">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assetAdmin/assets/stylesheets/custom.css">
    </head>
    <body>
    <div class="app">
        <header class="app-header app-header-dark">
            <div class="top-bar">
                <div class="top-bar-brand">
                    <button class="hamburger hamburger-squeeze mr-2" type="button" data-toggle="aside-menu">
                        <span class="hamburger-box"><span class="hamburger-inner"></span></span>
                    </button>
                    <a href="${pageContext.request.contextPath}/admin" class="text-white">EMBANTHE</a>
                </div>
                <div class="top-bar-list">
                    <div class="top-bar-item top-bar-item-right px-0 d-none d-sm-flex">
                        <div class="dropdown d-flex">
                            <button class="btn-account d-none d-md-flex" type="button" data-toggle="dropdown">
                                <span class="user-avatar user-avatar-md"><img src="${pageContext.request.contextPath}/assetAdmin/assets/images/avatars/profile.jpg" alt=""></span>
                                <span class="account-summary pr-lg-4 d-none d-lg-block">
                                        <span class="account-name">Admin</span>
                                        <span class="account-description">Hệ thống quản trị</span>
                                    </span>
                            </button>
                            <div class="dropdown-menu">
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
                            <li class="menu-item has-active">
                                <a href="${pageContext.request.contextPath}/admin" class="menu-link"><span class="menu-icon fas fa-home"></span> <span class="menu-text">Dashboard</span></a>
                            </li>
                            <li class="menu-header">Hệ thống</li>
                            <li class="menu-item">
                                <a href="${pageContext.request.contextPath}/admin/user-list" class="menu-link"><span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý User</span></a>
                            </li>
                            <li class="menu-item">
                                <a href="${pageContext.request.contextPath}/admin/products" class="menu-link"><span class="menu-icon fas fa-box"></span> <span class="menu-text">Quản lý Sản Phẩm</span></a>
                            </li>
                            <li class="menu-item">
                                <a href="${pageContext.request.contextPath}/admin/providers" class="menu-link"><span class="menu-icon fas fa-building"></span> <span class="menu-text">Quản lý Nhà mạng</span></a>
                            </li>
                            <li class="menu-item">
                                <a href="${pageContext.request.contextPath}/admin/category" class="menu-link"><span class="menu-icon fas fa-tags"></span> <span class="menu-text">Quản lý Loại thẻ</span></a>
                            </li>
                            <li class="menu-item ">
                                <a href="${pageContext.request.contextPath}/admin/support-list" class="menu-link ">
                                    <span class="menu-icon oi oi-browser"></span> <span
                                        class="menu-text">Quản lý Support</span>
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

        <main class="app-main">
            <div class="wrapper">
                <div class="page">
                    <div class="page-inner">
                        <header class="page-title-bar">
                            <h1 class="page-title"> Dashboard Thống Kê </h1>
                        </header>

                        <div class="page-section">
                            <div class="section-block">
                                <div class="metric-row">
                                    <div class="col-lg-4">
                                        <div class="metric metric-bordered align-items-center">
                                            <h2 class="metric-label"> Tổng thành viên </h2>
                                            <p class="metric-value h3">
                                                <span class="value">${totalUsers != null ? totalUsers : "0"}</span>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="metric metric-bordered align-items-center">
                                            <h2 class="metric-label"> Doanh thu (Nạp tiền) </h2>
                                            <p class="metric-value h3 text-success">
                                                <span class="value">
                                                    <fmt:formatNumber value="${totalRevenue != null ? totalRevenue : 0}" type="currency" currencySymbol="VNĐ" maxFractionDigits="0"/>
                                                </span>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="col-lg-4">
                                        <div class="metric metric-bordered align-items-center">
                                            <h2 class="metric-label"> Giao dịch hôm nay </h2>
                                            <p class="metric-value h3 text-primary">
                                                <span class="value">${transactionsToday != null ? transactionsToday : "0"}</span>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-12">
                                    <div class="card card-fluid">
                                        <div class="card-body">
                                            <h3 class="card-title"> Biểu đồ Đơn hàng 7 ngày gần nhất </h3>
                                            <div style="height: 300px">
                                                <canvas id="orderChart"></canvas>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="card card-fluid">
                                <div class="card-header">
                                    <div class="d-flex align-items-center justify-content-between">
                                        <h6 class="mb-0">Hoạt động gần đây</h6>
                                    </div>
                                </div>

                                <div class="card-body border-bottom">
                                    <form method="get" action="${pageContext.request.contextPath}/admin">
                                        <div class="form-row">
                                            <div class="col-md-3 mb-2">
                                                <input type="text" class="form-control" name="keyword" placeholder="Nhập username..." value="${paramKeyword}">
                                            </div>

                                            <div class="col-md-2 mb-2">
                                                <select class="form-control" name="type">
                                                    <option value="">-- Tất cả --</option>
                                                    <option value="DEPOSIT" ${paramType == 'DEPOSIT' ? 'selected' : ''}>DEPOSIT</option>
                                                    <option value="PURCHASE" ${paramType == 'PURCHASE' ? 'selected' : ''}>PURCHASE</option>
                                                    <option value="REFUND" ${paramType == 'REFUND' ? 'selected' : ''}>REFUND</option>
                                                </select>
                                            </div>

                                            <div class="col-md-3 mb-2">
                                                <div class="input-group">
                                                    <div class="input-group-prepend"><span class="input-group-text">Từ</span></div>
                                                    <input type="date" class="form-control" name="fromDate" value="${paramFromDate}">
                                                </div>
                                            </div>

                                            <div class="col-md-3 mb-2">
                                                <div class="input-group">
                                                    <div class="input-group-prepend"><span class="input-group-text">Đến</span></div>
                                                    <input type="date" class="form-control" name="toDate" value="${paramToDate}">
                                                </div>
                                            </div>

                                            <div class="col-md-1 mb-2">
                                                <button type="submit" class="btn btn-primary btn-block"><i class="fas fa-search"></i></button>
                                            </div>
                                        </div>
                                    </form>
                                </div>

                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th> STT </th>
                                            <th> Người dùng </th>
                                            <th> Chi tiết hành động </th>
                                            <th> Loại </th>
                                            <th> Thời gian </th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${recentActivities}" var="act" varStatus="loop">
                                            <tr>
                                                <td class="align-middle">${((currentPage - 1) * 10) + loop.index + 1}</td>
                                                <td class="align-middle font-weight-bold">${act.userName}</td>
                                                <td class="align-middle">
                            <span class="${act.type == 'DEPOSIT' ? 'text-success' : (act.type == 'PURCHASE' ? 'text-danger' : 'text-dark')}">
                                    ${act.action}
                            </span>
                                                </td>
                                                <td class="align-middle">
                            <span class="badge ${act.type == 'DEPOSIT' ? 'badge-success' : (act.type == 'PURCHASE' ? 'badge-primary' : 'badge-warning')}">
                                    ${act.type}
                            </span>
                                                </td>
                                                <td class="align-middle text-muted">${act.time}</td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty recentActivities}">
                                            <tr><td colspan="5" class="text-center py-4">Không tìm thấy dữ liệu nào phù hợp.</td></tr>
                                        </c:if>
                                        </tbody>
                                    </table>
                                </div>

                                <c:if test="${totalPages > 1}">
                                    <div class="card-footer">
                                        <nav aria-label="Page navigation">
                                            <ul class="pagination justify-content-center mb-0">

                                                <c:set var="filterParams" value="&keyword=${paramKeyword}&type=${paramType}&fromDate=${paramFromDate}&toDate=${paramToDate}" />

                                                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                    <a class="page-link" href="?page=${currentPage - 1}${filterParams}">Trước</a>
                                                </li>

                                                <c:forEach begin="1" end="${totalPages}" var="i">
                                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                        <a class="page-link" href="?page=${i}${filterParams}">${i}</a>
                                                    </li>
                                                </c:forEach>

                                                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                                    <a class="page-link" href="?page=${currentPage + 1}${filterParams}">Sau</a>
                                                </li>

                                            </ul>
                                        </nav>
                                    </div>
                                </c:if>
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
    <script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/stacked-menu/stacked-menu.min.js"></script>
    <script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
    <script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/chart.js/Chart.min.js"></script>
    <script src="${pageContext.request.contextPath}/assetAdmin/assets/javascript/theme.min.js"></script>

    <script>
        // 1. Chuẩn bị dữ liệu từ Server gửi về (ordersMap)
        // Lưu ý: ordersMap là Map<String, Integer> (Ngày -> Số lượng)

        var chartLabels = [];
        var chartData = [];

        <c:if test="${not empty ordersMap}">
        <c:forEach items="${ordersMap}" var="entry">
        chartLabels.push("${entry.key}"); // Ngày (key)
        chartData.push(${entry.value});   // Số lượng (value)
        </c:forEach>
        </c:if>

        // 2. Cấu hình Chart.js
        var ctx = document.getElementById('orderChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'line', // Dạng biểu đồ đường
            data: {
                labels: chartLabels,
                datasets: [{
                    label: 'Số đơn hàng',
                    data: chartData,
                    borderColor: '#3063A0',
                    backgroundColor: 'rgba(48, 99, 160, 0.2)',
                    pointBackgroundColor: '#3063A0',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4 // Tạo đường cong mềm mại
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true,
                            stepSize: 1 // Chỉ hiển thị số nguyên (1 đơn, 2 đơn...)
                        }
                    }]
                },
                tooltips: {
                    mode: 'index',
                    intersect: false
                }
            }
        });
    </script>
    </body>
    </html>