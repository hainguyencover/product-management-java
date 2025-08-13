package com.example.productmanagementjava.filter;

import com.example.productmanagementjava.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // -- DEBUG --
        System.out.println(">>> AuthFilter: Đang xử lý đường dẫn: " + path);

        // ... (phần code cho phép /login, /css, /js giữ nguyên)
        if (path.startsWith("/login") || path.startsWith("/css/") || path.startsWith("/js/")) {
            chain.doFilter(request, response);
            return;
        }

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            // -- DEBUG --
            System.out.println(">>> AuthFilter: CHƯA ĐĂNG NHẬP. Chuyển hướng về /login...");
            ((HttpServletResponse) response).sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            // -- DEBUG --
            System.out.println(">>> AuthFilter: Đã đăng nhập với vai trò: " + user.getRole());
            if (path.startsWith("/admin") && !"ADMIN".equals(user.getRole())) {
                // -- DEBUG --
                System.out.println(">>> AuthFilter: LỖI PHÂN QUYỀN! User thường truy cập trang Admin.");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
            } else {
                // -- DEBUG --
                System.out.println(">>> AuthFilter: Hợp lệ. Cho phép đi tiếp...");
                chain.doFilter(request, response);
            }
        }
    }
}