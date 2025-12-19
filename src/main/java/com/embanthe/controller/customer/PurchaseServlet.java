package com.embanthe.controller.customer;
import com.embanthe.dao.UserDAO;
import com.embanthe.model.Users;
import com.embanthe.service.PurchaseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {

    private final PurchaseService purchaseService = new PurchaseService();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // 1. Kiểm tra đăng nhập
        HttpSession session = req.getSession();
        Users user = (Users) session.getAttribute("user"); // Giả sử key session của bạn là "user"

        if (user == null) {
            String message = "Vui lòng đăng nhập để mua hàng";
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

            resp.sendRedirect(req.getContextPath() + "/login?message=" + encodedMessage);
            return;
        }

        try {
            // 2. Lấy dữ liệu từ Form (service.jsp)
            String productIdStr = req.getParameter("productId");
            String quantityStr = req.getParameter("quantity");

            // Validate dữ liệu đầu vào
            if (productIdStr == null || quantityStr == null || productIdStr.isEmpty() || quantityStr.isEmpty()) {
                forwardWithError(req, resp, "Dữ liệu không hợp lệ.");
                return;
            }

            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            if (quantity <= 0 || quantity > 100) {
                forwardWithError(req, resp, "Số lượng phải từ 1 đến 100.");
                return;
            }

            // 3. GỌI SERVICE XỬ LÝ GIAO DỊCH
            // Kết quả trả về dạng: "SUCCESS|15" hoặc "Số dư không đủ..."
            String result = purchaseService.processPurchase(user.getUserId(), productId, quantity);

            if (result.startsWith("SUCCESS")) {
                // --- MUA THÀNH CÔNG ---

                // Lấy Order ID từ kết quả
                String orderId = result.split("\\|")[1];

                // QUAN TRỌNG: Cập nhật lại số dư mới vào Session để hiển thị trên Header
                // (Lấy user mới nhất từ DB lên)
                Users updatedUser = userDAO.getUserById(user.getUserId());
                session.setAttribute("user", updatedUser);

                // Chuyển hướng sang trang chi tiết đơn hàng (Redirect để tránh resubmit form khi F5)
                // Lưu ý: Bạn cần có trang order-detail.jsp hoặc OrderDetailServlet để hứng cái này
                resp.sendRedirect(req.getContextPath() + "/order-detail?id=" + orderId);

            } else {
                // --- MUA THẤT BẠI ---
                forwardWithError(req, resp, result); // Hiển thị lỗi Service trả về (ví dụ: "Kho hết hàng")
            }

        } catch (NumberFormatException e) {
            forwardWithError(req, resp, "Lỗi định dạng dữ liệu.");
        } catch (Exception e) {
            e.printStackTrace();
            forwardWithError(req, resp, "Đã xảy ra lỗi hệ thống.");
        }
    }

    // Hàm phụ trợ để hiển thị lỗi và quay lại trang Service
    private void forwardWithError(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws ServletException, IOException {

        req.setAttribute("errorMessage", errorMessage);

        // Forward về ServiceServlet để nó load lại danh sách sản phẩm rồi mới hiện JSP
        req.getRequestDispatcher("/service").forward(req, resp);
    }
}