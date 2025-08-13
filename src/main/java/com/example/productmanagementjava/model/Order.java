package com.example.productmanagementjava.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private double total;
    private List<OrderItem> items;
    private String customerUsername;

    public Order() {
    }

    public Order(int userId, Date orderDate, double total, List<OrderItem> items) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.total = total;
        this.items = items;
    }


    public Order(int id, int userId, Date orderDate, double total, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.total = total;
        this.items = items;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotal() {
        return total;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }
}
