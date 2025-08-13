package com.example.productmanagementjava.dao;


import com.example.productmanagementjava.model.Category;
import com.example.productmanagementjava.model.Product;
import com.example.productmanagementjava.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    /**
     * Lấy tất cả sản phẩm từ database, bao gồm cả thông tin về danh mục (Category).
     *
     * @return Danh sách các đối tượng Product.
     */
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        // Câu lệnh SQL JOIN để lấy cả tên danh mục
        String sql = "SELECT p.*, c.name as category_name FROM products p JOIN categories c ON p.category_id = c.id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));

                // Tạo và gán đối tượng Category cho sản phẩm
                Category category = new Category();
                category.setId(rs.getInt("category_id"));
                category.setName(rs.getString("category_name"));
                product.setCategory(category);

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi ra console để debug
        }
        return productList;
    }

    /**
     * Tìm một sản phẩm cụ thể bằng ID của nó.
     *
     * @param id ID của sản phẩm cần tìm.
     * @return Đối tượng Product nếu tìm thấy, ngược lại trả về null.
     */
    public Product findById(int id) {
        Product product = null;
        String sql = "SELECT p.*, c.name as category_name FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantity(rs.getInt("quantity"));

                    Category category = new Category();
                    category.setId(rs.getInt("category_id"));
                    category.setName(rs.getString("category_name"));
                    product.setCategory(category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Lưu một sản phẩm mới vào database.
     *
     * @param product Đối tượng Product chứa thông tin cần thêm mới.
     */
    public void save(Product product) {
        String sql = "INSERT INTO products (name, price, quantity, category_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setInt(4, product.getCategory().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật thông tin của một sản phẩm đã có.
     *
     * @param product Đối tượng Product chứa thông tin cần cập nhật (phải có ID).
     */
    public void update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, quantity = ?, category_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setInt(4, product.getCategory().getId());
            ps.setInt(5, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa một sản phẩm khỏi database dựa vào ID.
     *
     * @param id ID của sản phẩm cần xóa.
     */
    public void delete(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cập nhật (giảm) số lượng tồn kho của sản phẩm.
     * Thường được gọi sau khi một đơn hàng được tạo thành công.
     *
     * @param productId        ID của sản phẩm.
     * @param quantityToReduce Số lượng cần giảm.
     */
    public void updateStock(int productId, int quantityToReduce) {
        String sql = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantityToReduce);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM products";
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
}
