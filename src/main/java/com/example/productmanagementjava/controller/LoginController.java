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
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    /**
     * Xử lý yêu cầu GET: Hiển thị trang đăng nhập.
     * AuthFilter sẽ chuyển hướng người dùng chưa đăng nhập về đây.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu người dùng đã đăng nhập rồi mà vẫn vào trang login, chuyển họ về trang chủ tương ứng
        if (request.getSession().getAttribute("user") != null) {
            User user = (User) request.getSession().getAttribute("user");
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
            return;
        }

        // Nếu chưa đăng nhập, hiển thị form login
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu POST: Xác thực thông tin đăng nhập từ form.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User authUser = userDAO.findByUsernameAndPassword(username, UserDAO.hashPassword(password));

        if (authUser != null) {
            request.getSession().setAttribute("user", authUser);
            System.out.println(">>> LoginController: Đăng nhập thành công cho user: "
                    + authUser.getUsername() + " với vai trò: " + authUser.getRole());

            if ("ADMIN".equalsIgnoreCase(authUser.getRole())) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
        } else {
            request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}