package com.example.productmanagementjava.controller;

import com.example.productmanagementjava.dao.OrderDAO;
import com.example.productmanagementjava.model.Order;
import com.example.productmanagementjava.model.OrderItem;
import com.example.productmanagementjava.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@WebServlet("/checkout")

public class CheckoutController extends HttpServlet {
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");
        Map<Integer, OrderItem> cart = (Map<Integer, OrderItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Tạo đối tượng Order từ thông tin trong session
        Order order = new Order();
        order.setUserId(user.getId());
        order.setItems(new ArrayList<>(cart.values()));

        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotal(total);

        // Gọi DAO để lưu đơn hàng vào database
        boolean success = orderDAO.createOrder(order);

        if (success) {
            // Xóa giỏ hàng khỏi session
            session.removeAttribute("cart");
            // Chuyển hướng đến trang cảm ơn
            response.sendRedirect(request.getContextPath() + "/order-success");
        } else {
            // Xử lý lỗi (ví dụ: sản phẩm hết hàng)
            request.setAttribute("error", "Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
        }
    }
}