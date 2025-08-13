package com.example.productmanagementjava.dao;

import com.example.productmanagementjava.model.Order;
import com.example.productmanagementjava.model.OrderItem;
import com.example.productmanagementjava.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public boolean createOrder(Order order) {
        String insertOrderSQL = "INSERT INTO orders(user_id, total) VALUES(?, ?)";
        String insertOrderItemSQL = "INSERT INTO order_items(order_id, product_id, product_name, quantity, price) VALUES(?, ?, ?, ?, ?)";
        String updateStockSQL = "UPDATE products SET quantity = quantity - ? WHERE id = ? AND quantity >= ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            // BẮT ĐẦU TRANSACTION
            conn.setAutoCommit(false);

            // 1. Thêm vào bảng orders và lấy ID vừa tạo
            int orderId;
            try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSQL, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setInt(1, order.getUserId());
                psOrder.setDouble(2, order.getTotal());
                psOrder.executeUpdate();

                ResultSet rs = psOrder.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                } else {
                    throw new SQLException("Tạo đơn hàng thất bại, không lấy được ID.");
                }
            }

            // 2. Thêm các mục vào bảng order_items
            try (PreparedStatement psItem = conn.prepareStatement(insertOrderItemSQL)) {
                for (OrderItem item : order.getItems()) {
                    psItem.setInt(1, orderId);
                    psItem.setInt(2, item.getProductId());
                    psItem.setString(3, item.getProductName()); // Thêm tên sản phẩm
                    psItem.setInt(4, item.getQuantity());
                    psItem.setDouble(5, item.getPrice());
                    psItem.addBatch();
                }
                psItem.executeBatch();
            }

            // 3. Cập nhật số lượng tồn kho
            try (PreparedStatement psStock = conn.prepareStatement(updateStockSQL)) {
                for (OrderItem item : order.getItems()) {
                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getProductId());
                    psStock.setInt(3, item.getQuantity()); // Đảm bảo số lượng trong kho đủ để trừ
                    int rowsAffected = psStock.executeUpdate();
                    if (rowsAffected == 0) {
                        // Nếu không có hàng nào được cập nhật, nghĩa là hết hàng
                        throw new SQLException("Sản phẩm '" + item.getProductName() + "' không đủ số lượng trong kho.");
                    }
                }
            }

            // Nếu mọi thứ thành công, commit transaction
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo đơn hàng, đang rollback...");
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Nếu có lỗi, hủy bỏ mọi thay đổi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getTotalRevenue() {
        String sql = "SELECT SUM(total) FROM orders";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Phương thức này lấy danh sách tóm tắt của tất cả đơn hàng
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        // JOIN với bảng users để lấy username của người đặt
        String sql = "SELECT o.*, u.username FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotal(rs.getDouble("total"));
                order.setCustomerUsername(rs.getString("username")); // Gán username
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    // Phương thức này lấy thông tin chi tiết của MỘT đơn hàng, bao gồm cả các sản phẩm bên trong
    public Order findById(int orderId) {
        Order order = null;
        String orderSQL = "SELECT o.*, u.username FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";
        String itemsSQL = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            // Lấy thông tin cơ bản của đơn hàng
            try (PreparedStatement psOrder = conn.prepareStatement(orderSQL)) {
                psOrder.setInt(1, orderId);
                ResultSet rsOrder = psOrder.executeQuery();
                if (rsOrder.next()) {
                    order = new Order();
                    order.setId(rsOrder.getInt("id"));
                    order.setUserId(rsOrder.getInt("user_id"));
                    order.setOrderDate(rsOrder.getTimestamp("order_date"));
                    order.setTotal(rsOrder.getDouble("total"));
                    order.setCustomerUsername(rsOrder.getString("username"));
                }
            }

            // Nếu tìm thấy đơn hàng, lấy danh sách các sản phẩm của nó
            if (order != null) {
                List<OrderItem> items = new ArrayList<>();
                try (PreparedStatement psItems = conn.prepareStatement(itemsSQL)) {
                    psItems.setInt(1, orderId);
                    ResultSet rsItems = psItems.executeQuery();
                    while (rsItems.next()) {
                        OrderItem item = new OrderItem();
                        item.setId(rsItems.getInt("id"));
                        item.setProductId(rsItems.getInt("product_id"));
                        item.setProductName(rsItems.getString("product_name"));
                        item.setQuantity(rsItems.getInt("quantity"));
                        item.setPrice(rsItems.getDouble("price"));
                        items.add(item);
                    }
                }
                order.setItems(items);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
