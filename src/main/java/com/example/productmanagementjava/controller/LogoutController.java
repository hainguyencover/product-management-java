package com.example.productmanagementjava.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        // SỬA LẠI DÒNG NÀY
        // Thay vì forward, hãy sendRedirect về URL của LoginController
        response.sendRedirect(request.getContextPath() + "/login");
    }
}