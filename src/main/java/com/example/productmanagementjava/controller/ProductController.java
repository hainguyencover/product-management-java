package com.example.productmanagementjava.controller;

import com.example.productmanagementjava.dao.CategoryDAO;
import com.example.productmanagementjava.dao.ProductDAO;
import com.example.productmanagementjava.model.Category;
import com.example.productmanagementjava.model.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/product")
public class ProductController extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                request.setAttribute("categories", categoryDAO.findAll());
                request.getRequestDispatcher("product-form.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productDAO.findById(id);
                request.setAttribute("product", product);
                request.setAttribute("categories", categoryDAO.findAll());
                request.getRequestDispatcher("product-form.jsp").forward(request, response);
                break;
            case "delete":
                productDAO.delete(Integer.parseInt(request.getParameter("id")));
                response.sendRedirect("product");
                break;
            default:
                List<Product> list = productDAO.findAll();
                request.setAttribute("products", list);
                request.getRequestDispatcher("products.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("category_id"));
        Category category = categoryDAO.findById(categoryId);

        String idStr = request.getParameter("id");
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        product.setCategory(category);

        if (idStr == null || idStr.isEmpty()) {
            productDAO.save(product);
        } else {
            product.setId(Integer.parseInt(idStr));
            productDAO.update(product);
        }

        response.sendRedirect("product");
    }
}
