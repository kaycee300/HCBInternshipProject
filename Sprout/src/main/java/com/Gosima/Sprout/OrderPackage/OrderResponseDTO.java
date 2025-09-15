package com.Gosima.Sprout.OrderPackage;

import java.util.List;

public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private double totalPrice;
    private String status;
    private String createdAt;
    private List<OrderItemDTO> items;
    private List<OrderHistoryDTO> history;

    public OrderResponseDTO(Long orderId, String customerName, double totalPrice, String status,
                            String createdAt, List<OrderItemDTO> items, List<OrderHistoryDTO> history) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
        this.history = history;
    }
    // Getters & Setters


    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public List<OrderHistoryDTO> getHistory() {
        return history;
    }

    public void setHistory(List<OrderHistoryDTO> history) {
        this.history = history;
    }
}
