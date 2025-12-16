<%--
  Created by IntelliJ IDEA.
  User: tn030
  Date: 12/14/2025
  Time: 9:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách tồn kho thẻ</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8fafc; }
        .container { max-width: 1200px; margin: 32px auto; background: white; border-radius: 14px; box-shadow: 0 4px 24px #cfd8dc; padding: 24px; }
        h2 { color: #334155; }
        .action { margin-bottom: 18px; }
        .btn { background: #667eea; color: white; padding: 7px 22px; border-radius: 6px; text-decoration: none; font-weight: 600;}
        .btn:hover { background: #4051ad; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px;}
        th, td { text-align: left; padding: 14px 10px; }
        th { background: #f1f5f9; color: #475569; font-size: 14px;}
        tr:nth-child(even) { background: #f9fafb;}
        tr:hover { background: #f0f4f8;}
        .status-AVAILABLE { color: #15803d; font-weight: bold; }
        .status-SOLD { color: #2563eb; font-weight: bold; }
        .status-EXPIRED, .status-LOCKED { color: #be123c; font-weight: bold; }
        .paging {margin-top:20px;text-align:center;}
        .paging .pg {padding:7px 14px; margin:0 3px; border-radius:6px; border:1px solid #ddd; background:#eee; color:#333; text-decoration:none;}
        .paging .selected {background:#667eea; color:white;}
        .search-bar input, .search-bar select {padding:6px 10px;border-radius:5px;border:1px solid #d1d5db;margin-right:8px;}
        .search-bar button {padding:6px 16px;border-radius:5px;border:none;background:#64748b;color:white;}
        .search-bar {margin:20px 0;}
        .hide-code { font-size: 16px; font-family: monospace; letter-spacing: 2px; color: #64748b; }
    </style>
    <script>
        function toggleCode(cardId) {
            var e = document.getElementById("code-" + cardId);
            if(e.dataset.show === "0") {
                e.innerText = e.dataset.code;
                e.dataset.show = "1";
            } else {
                e.innerText = e.dataset.mask;
                e.dataset.show = "0";
            }
        }
    </script>
</head>
<body>
<div class="container">
    <h2>Danh sách tồn kho thẻ</h2>
    <div class="action">
        <a href="${pageContext.request.contextPath}/admin/carditems/add" class="btn">➕ Nhập thẻ mới</a>
    </div>
    <!-- SEARCH BAR -->
    <form class="search-bar" method="get" action="${pageContext.request.contextPath}/admin/carditems">
        <input type="text" name="searchSerial" placeholder="Tìm Serial" value="${searchSerial}">
        <input type="text" name="searchCode" placeholder="Tìm mã nạp" value="${searchCode == null ? '' : searchCode}">

        <select name="productId">
            <option value="">-- Tất cả sản phẩm --</option>
            <c:forEach var="prd" items="${products}">
                <option value="${prd.productId}" <c:if test="${productId != null and productId == prd.productId}">selected</c:if>>
                    ${prd.productName} (${prd.price}đ)
                </option>
            </c:forEach>
        </select>

        <select name="status">
            <option value="">-- Trạng thái --</option>
            <option value="AVAILABLE" <c:if test="${status == 'AVAILABLE'}">selected</c:if>>Chưa bán</option>
            <option value="SOLD" <c:if test="${status == 'SOLD'}">selected</c:if>>Đã bán</option>
            <option value="EXPIRED" <c:if test="${status == 'EXPIRED'}">selected</c:if>>Hết hạn</option>
            <option value="LOCKED" <c:if test="${status == 'LOCKED'}">selected</c:if>>Bị khóa</option>
        </select>

        <!-- NEW: select pageSize -->
        <label for="pageSize">Hiển thị:</label>
        <select name="pageSize" id="pageSize">
            <option value="10" <c:if test="${pageSize == 10}">selected</c:if>>10</option>
            <option value="20" <c:if test="${pageSize == 20}">selected</c:if>>20</option>
            <option value="50" <c:if test="${pageSize == 50}">selected</c:if>>50</option>
            <option value="100" <c:if test="${pageSize == 100}">selected</c:if>>100</option>
        </select>

        <button type="submit">Lọc/Tìm</button>
    </form>

    <c:choose>
        <c:when test="${empty cardList}">
            <div style="margin:50px auto; text-align:center;color:#a1a1aa;">Không có thẻ nào.</div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Serial</th>
                    <th>Mã nạp</th>
                    <th>Sản phẩm</th>
                    <th>Giá trị</th>
                    <th>Trạng thái</th>
                    <th>Ngày hết hạn</th>
                    <th>Ngày nhập</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="card" items="${cardList}" varStatus="stt">
                    <tr>
                        <!-- use dynamic pageSize for sequence number -->
                        <td>${(currentPage-1) * pageSize + stt.index + 1}</td>
                        <td><b><c:out value="${card.serialNumber}"/></b></td>
                        <td>
                            <span id="code-${card.cardItemId}"
                                 class="hide-code"
                                 data-code="${card.cardCode}"
                                 data-mask="••••••••••••"
                                 data-show="0">
                                 ••••••••••••
                            </span>
                            <a href="javascript:void(0)" onclick="toggleCode('${card.cardItemId}')" title="Hiện/Ẩn mã">👁️</a>
                        </td>

                        <!-- product display: if using productMap from servlet, prefer that; fallback to card.productName if present -->
                        <td>
                            <c:choose>
                                <c:when test="${not empty productMap and not empty productMap[card.productId]}">
                                    <c:out value="${productMap[card.productId].productName}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${card.productName}"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty productMap and not empty productMap[card.productId]}">
                                    <c:out value="${productMap[card.productId].price}"/> đ
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${card.price}"/> đ
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td class="status-${card.status}"><c:out value="${card.status}"/></td>
                        <td>
                            <c:if test="${not empty card.expirationDate}">
                                <c:out value="${card.expirationDate}"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty card.createdAt}">
                                <c:out value="${card.createdAt}"/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="paging">
                <c:if test="${totalPages > 1}">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <a class="pg ${i == currentPage ? 'selected' : ''}"
                           href="${pageContext.request.contextPath}/admin/carditems?page=${i}&pageSize=${pageSize}&searchSerial=${fn:escapeXml(searchSerial)}&searchCode=${fn:escapeXml(searchCode)}&productId=${productId}&status=${fn:escapeXml(status)}">
                            ${i}
                        </a>
                    </c:forEach>
                </c:if>
                <div style="margin-top:7px;font-size:13px;">
                    Tổng số thẻ: <b><c:out value="${totalItems}"/></b> | Trang <b><c:out value="${currentPage}"/></b> / <b><c:out value="${totalPages}"/></b>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>