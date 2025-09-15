package com.Gosima.Sprout.OrderPackage;

import com.Gosima.Sprout.Carts.Cart;
import com.Gosima.Sprout.Carts.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderHistoryRepository historyRepository;

    @Autowired
    private CartService cartService;

    public Order createOrder(Long userId) {
        Cart cart = cartService.getOrCreateCartForUser(userId);
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty!");
        }

        Order order = new Order(cart);
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(userId);
        return savedOrder;
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus currentStatus = order.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new IllegalArgumentException("Invalid status transition: " + currentStatus + " → " + newStatus);
        }

        String updatedBy = SecurityContextHolder.getContext().getAuthentication().getName();

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        OrderHistory history = new OrderHistory(order, currentStatus, newStatus, updatedBy);
        historyRepository.save(history);

        return updatedOrder;
    }

    public List<OrderHistory> getOrderHistory(Long orderId) {
        return historyRepository.findByOrderId(orderId);
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case PENDING -> next == OrderStatus.PROCESSING || next == OrderStatus.CANCELLED;
            case PROCESSING -> next == OrderStatus.SHIPPED || next == OrderStatus.CANCELLED;
            case SHIPPED -> next == OrderStatus.DELIVERED;
            case DELIVERED, CANCELLED -> false;
        };
    }

    // Map to DTO
    public OrderResponseDTO mapToOrderResponseDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()
                ))
                .toList();

        List<OrderHistoryDTO> historyDTOs = historyRepository.findByOrderId(order.getId()).stream()
                .map(h -> new OrderHistoryDTO(
                        h.getOldStatus().name(),
                        h.getNewStatus().name(),
                        h.getUpdatedBy(),
                        h.getUpdatedAt().toString()
                ))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getUser().getName(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt().toString(),
                itemDTOs,
                historyDTOs
        );
    }
}
