package com.java.be_ecommerce_app.service.order;

import com.java.be_ecommerce_app.model.dto.request.order.CreateOrderDto;
import com.java.be_ecommerce_app.model.dto.response.order.OrderResponseDto;
import com.java.be_ecommerce_app.model.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface OrderService {
    OrderResponseDto createOrder(Authentication authentication, CreateOrderDto dto);
    Page<OrderResponseDto> getOrders(Authentication authentication, OrderStatus status, Pageable pageable);
    OrderResponseDto getOrderById(Authentication authentication, Long orderId);
    OrderResponseDto cancelOrder(Authentication authentication, Long orderId);
}


