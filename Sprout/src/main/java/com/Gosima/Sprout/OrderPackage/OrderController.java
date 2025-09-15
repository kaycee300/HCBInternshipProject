package com.Gosima.Sprout.OrderPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@RequestParam Long userId) {
        Order order = orderService.createOrder(userId);
        return ResponseEntity.ok(ApiResponse.success("Order created successfully", orderService.mapToOrderResponseDTO(order)));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getMyOrders(@RequestParam Long userId) {
        List<OrderResponseDTO> orders = orderService.getOrdersByUser(userId).stream()
                .map(orderService::mapToOrderResponseDTO)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Orders retrieved successfully", orders));
    }

    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders().stream()
                .map(orderService::mapToOrderResponseDTO)
                .toList();
        return ResponseEntity.ok(ApiResponse.success("All orders retrieved successfully", orders));
    }

    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrderStatus(@PathVariable Long orderId,
                                                                           @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(ApiResponse.success("Order status updated successfully", orderService.mapToOrderResponseDTO(updatedOrder)));
    }

    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @GetMapping("/{orderId}/history")
    public ResponseEntity<ApiResponse<List<OrderHistoryDTO>>> getOrderHistory(@PathVariable Long orderId) {
        List<OrderHistoryDTO> history = orderService.getOrderHistory(orderId).stream()
                .map(h -> new OrderHistoryDTO(
                        h.getOldStatus().name(),
                        h.getNewStatus().name(),
                        h.getUpdatedBy(),
                        h.getUpdatedAt().toString()
                ))
                .toList();
        return ResponseEntity.ok(ApiResponse.success("Order history retrieved successfully", history));
    }
}
