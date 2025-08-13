package com.example.productmanagementjava.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Set the character encoding for the request
        request.setCharacterEncoding("UTF-8");

        // Set the character encoding for the response
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Pass the request along the filter chain
        chain.doFilter(request, response);
    }
}
