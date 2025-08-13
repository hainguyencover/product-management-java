package com.example.productmanagementjava.controller;

import com.example.productmanagementjava.dao.CategoryDAO;
import com.example.productmanagementjava.dao.OrderDAO;
import com.example.productmanagementjava.dao.ProductDAO;
import com.example.productmanagementjava.dao.UserDAO;
import com.example.productmanagementjava.model.Category;
import com.example.productmanagementjava.model.Order;
import com.example.productmanagementjava.model.Product;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Đây là Front Controller DUY NHẤT cho toàn bộ khu vực Admin.
 * Nó lắng nghe tất cả các URL bắt đầu bằng /admin/ và điều phối các hành động.
 */
@WebServlet("/admin/*")
public class AdminController extends HttpServlet {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        categoryDAO = new CategoryDAO();
        userDAO = new UserDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // -- DEBUG --
        System.out.println("!!! AdminController: ĐÃ NHẬN ĐƯỢC YÊU CẦU !!!");
        String pathInfo = request.getPathInfo();
        System.out.println(">>> AdminController: Path Info là: " + pathInfo);

//        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            pathInfo = "/dashboard"; // Mặc định là trang dashboard
        }
        routeRequest(pathInfo, request, response);
    }

    private void routeRequest(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] parts = path.split("/");
        String resource = (parts.length > 1) ? parts[1] : "";

        switch (resource) {
            case "dashboard":
                showDashboard(request, response);
                break;
            case "products":
                handleProductActions(request, response);
                break;
            case "categories":
                handleCategoryActions(request, response);
                break;
            case "orders": // THÊM CASE NÀY
                handleOrderActions(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void handleProductActions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(); // pathInfo sẽ là /products, /products/create,...
        String action = "list"; // Mặc định
        if (pathInfo.endsWith("/create")) action = "create";
        else if (pathInfo.endsWith("/edit")) action = "edit";
        else if (pathInfo.endsWith("/delete")) action = "delete";

        switch (action) {
            case "create":
            case "edit":
                if (request.getMethod().equalsIgnoreCase("GET")) showProductForm(request, response);
                else saveProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default: // list
                listProducts(request, response);
                break;
        }
    }

    private void handleCategoryActions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String action = "list";
        if (pathInfo.endsWith("/create")) action = "create";
        else if (pathInfo.endsWith("/edit")) action = "edit";
        else if (pathInfo.endsWith("/delete")) action = "delete";

        switch (action) {
            case "create":
            case "edit":
                if (request.getMethod().equalsIgnoreCase("GET")) showCategoryForm(request, response);
                else saveCategory(request, response);
                break;
            case "delete":
                deleteCategory(request, response);
                break;
            default: // list
                listCategories(request, response);
                break;
        }
    }

    // 2. Thêm một phương thức handle mới cho đơn hàng
    private void handleOrderActions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String action = "list"; // Mặc định là xem danh sách
        if (pathInfo.endsWith("/view")) {
            action = "view";
        }

        switch (action) {
            case "view":
                viewOrderDetail(request, response);
                break;
            default: // list
                listOrders(request, response);
                break;
        }
    }

    // --- CÁC PHƯƠNG THỨC LOGIC ---

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("totalProducts", productDAO.countAll());
        request.setAttribute("totalUsers", userDAO.countAll());
        request.setAttribute("totalOrders", orderDAO.countAll());
        request.setAttribute("totalRevenue", orderDAO.getTotalRevenue());
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/views/admin/products.jsp").forward(request, response);
    }

    private void showProductForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) { // Chế độ Edit
            int id = Integer.parseInt(idParam);
            request.setAttribute("product", productDAO.findById(id));
        }
        request.setAttribute("categories", categoryDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/views/admin/product-form.jsp").forward(request, response);
    }

    private void saveProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
        Category category = new Category();
        category.setId(Integer.parseInt(request.getParameter("categoryId")));
        product.setCategory(category);

        if (idParam == null || idParam.isEmpty()) { // Create
            productDAO.save(product);
        } else { // Edit
            product.setId(Integer.parseInt(idParam));
            productDAO.update(product);
        }
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/products");
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categories", categoryDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
    }

    private void showCategoryForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            request.setAttribute("category", categoryDAO.findById(Integer.parseInt(idParam)));
        }
        request.getRequestDispatcher("/WEB-INF/views/admin/category-form.jsp").forward(request, response);
    }

    private void saveCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        if (idParam == null || idParam.isEmpty()) {
            categoryDAO.save(new Category(name));
        } else {
            categoryDAO.update(new Category(Integer.parseInt(idParam), name));
        }
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoryDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }

    // 3. Thêm 2 phương thức logic xử lý mới
    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orderList = orderDAO.findAll();
        request.setAttribute("orders", orderList);
        request.getRequestDispatcher("/WEB-INF/views/admin/orders.jsp").forward(request, response);
    }

    private void viewOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            Order order = orderDAO.findById(orderId);
            if (order != null) {
                request.setAttribute("order", order);
                request.getRequestDispatcher("/WEB-INF/views/admin/order-detail.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng.");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID đơn hàng không hợp lệ.");
        }
    }
}