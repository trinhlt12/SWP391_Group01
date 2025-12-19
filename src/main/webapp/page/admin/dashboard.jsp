<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                <a href="index.html" class="text-white">EMBANTHE</a>
            </div>
            <div class="top-bar-list">
                <div class="top-bar-item top-bar-item-right px-0 d-none d-sm-flex">
                    <div class="dropdown d-flex">
                        <button class="btn-account d-none d-md-flex" type="button" data-toggle="dropdown">
                            <span class="user-avatar user-avatar-md"><img src="assets/images/avatars/profile.jpg" alt=""></span>
                            <span class="account-summary pr-lg-4 d-none d-lg-block">
                                    <span class="account-name">Admin</span>
                                    <span class="account-description">Hệ thống quản trị</span>
                                </span>
                        </button>
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
                            <a href="dashboard" class="menu-link"><span class="menu-icon fas fa-home"></span> <span class="menu-text">Dashboard</span></a>
                        </li>
                        <li class="menu-header">Hệ thống</li>
                        <li class="menu-item">
                            <a href="admin/user-list" class="menu-link"><span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý User</span></a>
                        </li>
                        <li class="menu-item">
                            <a href="admin/orders" class="menu-link"><span class="menu-icon fas fa-shopping-cart"></span> <span class="menu-text">Quản lý Sản Phẩm</span></a>
                        </li>
                        <li class="menu-item">
                            <a href="admin/orders" class="menu-link"><span class="menu-icon fas fa-shopping-cart"></span> <span class="menu-text">Quản lý nhà mạng</span></a>
                        </li>
                        <li class="menu-item">
                            <a href="admin/orders" class="menu-link"><span class="menu-icon fas fa-shopping-cart"></span> <span class="menu-text">Quản lý loại thẻ</span></a>
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
                                <div class="col-lg-6">
                                    <div class="metric metric-bordered align-items-center">
                                        <h2 class="metric-label"> Total Users </h2>
                                        <p class="metric-value h3"> <span class="value">${totalUsers != null ? totalUsers : "0"}</span> </p>
                                    </div>
                                </div>
                                <div class="col-lg-6">
                                    <div class="metric metric-bordered align-items-center">
                                        <h2 class="metric-label"> Total Revenue </h2>
                                        <p class="metric-value h3 text-success"> <span class="value">${totalRevenue != null ? totalRevenue : "0"} VNĐ</span> </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <div class="card card-fluid">
                                    <div class="card-body">
                                        <h3 class="card-title"> Biểu đồ tăng trưởng đơn hàng (Chart Orders) </h3>
                                        <div style="height: 300px">
                                            <canvas id="orderChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="card card-fluid">
                            <div class="card-header"> Hoạt động gần đây (Recent Activities) </div>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th> STT </th>
                                        <th> Người dùng </th>
                                        <th> Hành động </th>
                                        <th> Thời gian </th>
                                        <th> Trạng thái </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${recentActivities}" var="act" varStatus="loop">
                                        <tr>
                                            <td class="align-middle">${loop.index + 1}</td>
                                            <td class="align-middle">${act.userName}</td>
                                            <td class="align-middle">${act.action}</td>
                                            <td class="align-middle">${act.time}</td>
                                            <td class="align-middle"><span class="badge badge-success">Thành công</span></td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty recentActivities}">
                                        <tr><td colspan="5" class="text-center">Chưa có dữ liệu hoạt động.</td></tr>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<script src="assets/vendor/jquery/jquery.min.js"></script>
<script src="assets/vendor/bootstrap/js/popper.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="assets/vendor/stacked-menu/stacked-menu.min.js"></script>
<script src="assets/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<script src="assets/vendor/chart.js/Chart.min.js"></script>
<script src="assets/javascript/theme.min.js"></script>

<script>
    var ctx = document.getElementById('orderChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [${chartLabels != null ? chartLabels : "'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'"}],
            datasets: [{
                label: 'Số đơn hàng',
                data: [${chartData != null ? chartData : "0, 0, 0, 0, 0, 0, 0"}],
                borderColor: '#3063A0',
                backgroundColor: 'rgba(48, 99, 160, 0.1)',
                fill: true
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
</script>
</body>
</html>