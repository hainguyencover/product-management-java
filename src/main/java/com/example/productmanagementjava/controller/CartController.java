package com.example.productmanagementjava.controller;

import com.example.productmanagementjava.dao.ProductDAO;
import com.example.productmanagementjava.model.OrderItem;
import com.example.productmanagementjava.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart/*")
public class CartController extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // -- DEBUG --
        System.out.println("!!! CartController: ĐÃ NHẬN ĐƯỢC YÊU CẦU !!!");
        String pathInfo = request.getPathInfo();
        System.out.println(">>> CartController: Path Info là: " + pathInfo);
        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            action = "/view"; // Mặc định là xem giỏ hàng
        }

        switch (action) {
            case "/add":
                addItemToCart(request, response);
                break;
            case "/delete":
                deleteItemFromCart(request, response);
                break;
            default: // "/view"
                request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
                break;
        }
    }


    private void addItemToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<Integer, OrderItem> cart = (Map<Integer, OrderItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            Product product = productDAO.findById(productId);

            // Kiểm tra xem sản phẩm có tồn tại và còn hàng không
            if (product != null && product.getQuantity() > 0) {
                OrderItem item;
                if (cart.containsKey(productId)) {
                    // Nếu sản phẩm đã có trong giỏ, chỉ tăng số lượng
                    item = cart.get(productId);
                    item.setQuantity(item.getQuantity() + 1);
                } else {
                    // Nếu chưa có, tạo mới và LƯU TÊN SẢN PHẨM
                    item = new OrderItem();
                    item.setProductId(product.getId());
                    item.setPrice(product.getPrice());
                    item.setQuantity(1);
                    item.setProductName(product.getName()); // DÒNG QUAN TRỌNG NHẤT
                }
                cart.put(productId, item);
            }
            session.setAttribute("cart", cart);
            // Sau khi thêm, chuyển hướng về trang giỏ hàng để người dùng xem
            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (NumberFormatException e) {
            // Nếu id sản phẩm không hợp lệ, chuyển về trang chủ
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    private void deleteItemFromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Lấy session, không tạo mới

        // Kiểm tra xem session và giỏ hàng có tồn tại không
        if (session != null && session.getAttribute("cart") != null) {
            Map<Integer, OrderItem> cart = (Map<Integer, OrderItem>) session.getAttribute("cart");

            try {
                // Lấy ID sản phẩm cần xóa từ URL
                int productId = Integer.parseInt(request.getParameter("id"));

                // Xóa sản phẩm khỏi giỏ hàng (Map)
                cart.remove(productId);

                // Cập nhật lại giỏ hàng trong session
                session.setAttribute("cart", cart);

            } catch (NumberFormatException e) {
                // Bỏ qua nếu tham số "id" không phải là số
                System.err.println("Lỗi khi xóa sản phẩm khỏi giỏ hàng: ID không hợp lệ.");
            }
        }

        // Luôn chuyển hướng người dùng về lại trang giỏ hàng để xem kết quả
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}