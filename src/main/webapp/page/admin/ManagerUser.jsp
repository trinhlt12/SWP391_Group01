<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title> Em Ban The | Admin System </title>

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
        disabledSkinStylesheet.setAttribute('rel', '');
        disabledSkinStylesheet.setAttribute('disabled', true);
    </script>
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
    <a href="${pageContext.request.contextPath}/home"
       class="text-decoration-none d-flex align-items-center">

    <span style="
        font-size: 22px;
        font-weight: 700;
        color: #ffc107;
        letter-spacing: 1px;
    ">
        EMBANTHE
    </span>

        <span style="
        font-size: 14px;
        margin-left: 6px;
        color: #ffffff;
        opacity: 0.8;
    ">
    </span>
    </a>

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
                        <li class="menu-item has-active">
                            <a href="${pageContext.request.contextPath}/user/list" class="menu-link">
                                <span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý User</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/user/list" class="menu-link">
                                <span class="menu-icon fas fa-users"></span> <span class="menu-text">Quản lý System</span>
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
                            <h1 class="page-title"> Danh sách người dùng </h1>
                            <button type="button" class="btn btn-success" data-toggle="modal" data-target="#createUserModal">
                                <i class="fas fa-plus mr-1"></i> Thêm mới
                            </button>
                        </div>
                    </header>

                    <div class="page-section">
                        <div class="card card-fluid">
                            <div class="card-body">
                                <div class="row mb-3">
                                    <div class="col-12">
                                        <form action="${pageContext.request.contextPath}/admin/user-list" method="get">
                                            <div class="form-row">

                                                <div class="col-md-5 mb-3">
                                                    <div class="input-group input-group-alt">
                                                        <div class="input-group-prepend">
                                                            <span class="input-group-text"><span class="oi oi-magnifying-glass"></span></span>
                                                        </div>
                                                        <input type="text" class="form-control" name="keyword"
                                                               value="${param.keyword}"
                                                               placeholder="Nhập tên hoặc email...">
                                                    </div>
                                                </div>

                                                <div class="col-md-3 mb-3">
                                                    <select class="custom-select" name="role" onchange="this.form.submit()">
                                                        <option value="">-- Tất cả Role --</option>

                                                        <option value="Admin" ${param.role == 'Admin' ? 'selected' : ''}>Admin</option>
                                                        <option value="User" ${param.role == 'User' ? 'selected' : ''}>User</option>
                                                    </select>
                                                </div>

                                                <div class="col-md-3 mb-3">
                                                    <select class="custom-select" name="status" onchange="this.form.submit()">
                                                        <option value="">-- Tất cả Trạng thái --</option>
                                                        <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                                                        <option value="Inactive" ${param.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
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
                                <div class="table-responsive">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead class="thead-light">
                                        <tr>
                                            <th style="width: 50px;">ID</th>
                                            <th>Tài khoản</th>
                                            <th>Họ và tên</th>
                                            <th>Email</th>
                                            <th>Role</th>
                                            <th>Status</th>
                                            <th>Reset Password</th>
                                            <th>Xem chi tiết</th>
                                            <th style="width: 100px;" class="text-right">Hành động</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:if test="${empty listUser}">
                                            <tr>
                                                <td colspan="5" class="text-center">Chưa có dữ liệu user nào.</td>
                                            </tr>
                                        </c:if>

                                        <c:forEach items="${listUser}" var="u">
                                            <tr>
                                                <td class="align-middle font-weight-bold">#${u.userId}</td>
                                                <td class="align-middle">${u.username}</td>
                                                <td class="align-middle">${u.fullName}</td>
                                                <td class="align-middle">${u.email}</td>
                                                <td class="align-middle">${u.role}</td>
                                                <td class="align-middle">${u.status}</td>
                                                <td class="align-middle text-center">
                                                    <form action="${pageContext.request.contextPath}/admin/user-reset-pass"
                                                          method="post"
                                                          style="display:inline; color: #0a53be"
                                                          onsubmit="return confirm('Reset mật khẩu về: ${u.username}@123 ?');">

                                                        <input type="hidden" name="id" value="${u.userId}">

                                                        <button type="submit" class="btn btn-sm btn-icon btn-warning" title="Reset mật khẩu">
                                                            <i class="fas fa-key" ></i>
                                                        </button>
                                                    </form>

                                                </td>
                                                <td class="align-middle text-center">
                                                    <a href="${pageContext.request.contextPath}/admin/user-detail?id=${u.userId}"
                                                       class="btn btn-sm btn-icon btn-info" title="Xem chi tiết">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                </td>
                                                <td class="align-middle text-center">
                                                    <button type="button" class="btn btn-sm btn-icon btn-secondary"
                                                            data-toggle="modal"
                                                            data-target="#editUserModal"
                                                            onclick="fillDataToModal('${u.userId}', '${u.username}', '${u.fullName}', '${u.email}', '${u.phone}', '${u.role}', '${u.status}')">
                                                        <i class="fa fa-pencil-alt"></i>
                                                    </button>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        </div></div></div></div></div>
    </main>

</div>
// modal edit user
<div class="modal fade" id="editUserModal" tabindex="-1" role="dialog" aria-labelledby="editUserModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editUserModalLabel">Cập nhật thông tin</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>

            <form action="${pageContext.request.contextPath}/admin/user-edit" method="post">
                <div class="modal-body">
                    <input type="hidden" class="form-control " name="id" id="modalUserId">

                    <div class="form-group">
                        <label>Tài khoản</label>
                        <input type="text" class="form-control " id="modalUsername" readonly>
                    </div>

                    <div class="form-group">
                        <label>Họ và tên</label>
                        <input type="text" class="form-control text-muted" name="fullname" id="modalFullName" maxlength="60" required>
                    </div>

                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" class="form-control text-muted" name="email" id="modalEmail" maxlength="30" required>
                    </div>

                    <div class="form-group">
                        <label>Số điện thoại</label>
                        <input type="text" class="form-control text-muted" name="phone" id="modalPhone" maxlength="10">
                    </div>

                    <div class="form-row">
                        <div class="form-group">
                            <label>Vai trò</label>
                            <select class="form-control" name="role" id="modalRole">
                                <option value="CUSTOMER" ${user.role == 'CUSTOMER' ? 'selected' : ''}>Customer</option>
                                <option value="ADMIN" ${user.role == 'ADMIN' ? 'selected' : ''}>Admin</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Trạng thái</label>
                            <select class="form-control" name="status" id="modalStatus">
                                <option value="ACTIVE" ${user.status == 'ACTIVE' ? 'selected' : ''}>Active</option>
                                <option value="INACTIVE" ${user.status == 'INACTIVE' ? 'selected' : ''}>Inactive</option>
                                <option value="BANNED" ${user.status == 'BANNED' ? 'selected' : ''}>Banned</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="modal fade" id="resetPassModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title"> <i class="fas fa-key"></i> Đổi mật khẩu</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="${pageContext.request.contextPath}/admin/user-reset-pass" method="post">
                <div class="modal-body">
                    <input type="hidden" name="id" id="resetPassId">

                    <p>Đang đổi mật khẩu cho tài khoản: <strong id="resetPassUsername" class="text-primary"></strong></p>

                    <div class="form-group">
                        <label>Mật khẩu mới</label>
                        <input type="password" class="form-control" name="newPass" required placeholder="Nhập mật khẩu mới...">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="submit" class="btn btn-warning">Lưu mật khẩu</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%--Modal Add new user--%>
<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog" aria-labelledby="createUserModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document"> <div class="modal-content">
        <div class="modal-header">
            <h5 class="modal-title" id="createUserModalLabel">
                <i class="fas fa-user-plus text-success"></i> Thêm người dùng mới
            </h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>



        <form action="${pageContext.request.contextPath}/admin/user-create" method="post">
            <div class="modal-body">
                <c:if test="${not empty sessionScope.error}">
                    <div class="alert alert-danger">
                        <i class="fas fa-exclamation-circle mr-1"></i>
                            ${sessionScope.error}
                    </div>
                </c:if>
                <div class="row">
                    <div class="col-md-6">
                        <h6 class="text-muted border-bottom pb-2">Thông tin tài khoản</h6>
                        <div class="form-group">
                            <label>Tài khoản (Username) <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" name="username" required placeholder="Nhập tên đăng nhập...">
                        </div>

                        <div class="form-group">
                            <label>Mật khẩu <span class="text-danger">*</span></label>
                            <input type="password" class="form-control" name="password" required placeholder="Nhập mật khẩu...">
                        </div>

                        <div class="form-group">
                            <label>Vai trò</label>
                            <select class="form-control" name="role">
                                <option value="CUSTOMER">Customer</option>
                                <option value="ADMIN">Admin</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label>Trạng thái</label>
                            <select class="form-control" name="status">
                                <option value="ACTIVE" selected>Active</option>
                                <option value="INACTIVE">Inactive</option>
                            </select>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <h6 class="text-muted border-bottom pb-2">Thông tin cá nhân</h6>
                        <div class="form-group">
                            <label>Họ và tên <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" name="fullname" required placeholder="Nhập họ tên đầy đủ..." maxlength="20">
                        </div>

                        <div class="form-group">
                            <label>Email <span class="text-danger">*</span></label>
                            <input type="email" class="form-control" name="email" required placeholder="example@email.com" maxlength="50">
                        </div>

                        <div class="form-group">
                            <label>Số điện thoại</label>
                            <input type="text" class="form-control" name="phone" placeholder="Nhập số điện thoại..." maxlength="10">
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                <button type="submit" class="btn btn-success">Tạo mới</button>
            </div>
        </form>
    </div>
    </div>
</div>
<script>

    function fillDataToModal(id, username, fullname, email, phone, role, status) {
        // Gán giá trị vào các ô input trong Modal
        document.getElementById('modalUserId').value = id;
        document.getElementById('modalUsername').value = username;
        document.getElementById('modalFullName').value = fullname;
        document.getElementById('modalEmail').value = email;
        document.getElementById('modalPhone').value = phone;

        // Gán giá trị cho Select Box (jQuery cho nhanh vì giao diện có sẵn jQuery)
        $('#modalRole').val(role);
        $('#modalStatus').val(status);
    }
</script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/bootstrap/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/pace/pace.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/stacked-menu/stacked-menu.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<script src="${pageContext.request.contextPath}/assetAdmin/assets/javascript/theme.min.js"></script>
<c:if test="${not empty sessionScope.error and not empty sessionScope.openCreateModal}">
    <script>
        $(document).ready(function () {
            $('#createUserModal').modal('show');
        });
    </script>

    <%-- Xóa cờ mở modal đi --%>
    <c:remove var="openCreateModal" scope="session"/>
</c:if>
</body>
</html>
