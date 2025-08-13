package com.example.productmanagementjava.controller;

import com.example.productmanagementjava.dao.ProductDAO;
import com.example.productmanagementjava.model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // -- DEBUG --
        System.out.println("!!! HomeController: ĐÃ NHẬN ĐƯỢC YÊU CẦU !!!");

        // Lấy toàn bộ sản phẩm từ DAO
        List<Product> productList = productDAO.findAll();

        // Đặt danh sách sản phẩm vào request attribute để JSP có thể truy cập
        request.setAttribute("productList", productList);

        // Chuyển tiếp yêu cầu đến trang JSP để hiển thị
        request.getRequestDispatcher("/WEB-INF/views/user/home.jsp").forward(request, response);
    }
}