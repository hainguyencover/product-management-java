package com.example.productmanagementjava.model;

public class OrderItem {
    private int id;
    private int productId;
    private int quantity;
    private double price;
    private String productName;

    public OrderItem() {
    }

    public OrderItem(int productId, int quantity, double price, String productName) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
    }

    public OrderItem(int id, int productId, int quantity, double price, String productName) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.productName = productName;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
