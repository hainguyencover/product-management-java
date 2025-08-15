package com.example.productmanagementjava.controller;

import com.example.productmanagementjava.dao.UserDAO;
import com.example.productmanagementjava.model.User;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    /**
     * Xử lý yêu cầu GET: Hiển thị form đăng ký.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Xử lý yêu cầu POST: Lưu thông tin đăng ký vào DB.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Hash mật khẩu trước khi lưu
        String hashedPassword = UserDAO.hashPassword(password);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);

        boolean isInserted = userDAO.insert(newUser);

        if (isInserted) {
            System.out.println(">>> RegisterController: Đăng ký thành công cho user: "
                    + username + " với role: " + role);
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } else {
            request.setAttribute("error", "Đăng ký thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}