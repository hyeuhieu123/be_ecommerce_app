package com.java.be_ecommerce_app.controller;

import com.java.be_ecommerce_app.model.dto.request.order.CreateOrderDto;
import com.java.be_ecommerce_app.model.dto.response.ApiResponse;
import com.java.be_ecommerce_app.model.dto.response.order.OrderResponseDto;
import com.java.be_ecommerce_app.model.entity.OrderStatus;
import com.java.be_ecommerce_app.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(
            Authentication authentication,
            @Valid @RequestBody CreateOrderDto createOrderDto) {
        OrderResponseDto order = orderService.createOrder(authentication, createOrderDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Order created successfully", order, null),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OrderResponseDto>>> getOrders(
            Authentication authentication,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderResponseDto> orders = orderService.getOrders(authentication, status, pageable);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Orders retrieved successfully", orders, null),
                HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(
            Authentication authentication,
            @PathVariable Long orderId) {
        OrderResponseDto order = orderService.getOrderById(authentication, orderId);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Order retrieved successfully", order, null),
                HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(
            Authentication authentication,
            @PathVariable Long orderId) {
        OrderResponseDto order = orderService.cancelOrder(authentication, orderId);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Order cancelled successfully", order, null),
                HttpStatus.OK);
    }
}


