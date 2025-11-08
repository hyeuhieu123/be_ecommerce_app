package com.java.be_ecommerce_app.model.dto.response.order;

import com.java.be_ecommerce_app.model.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private Long userId;
    private BigDecimal subtotal;
    private BigDecimal shippingFee;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    private String shippingName;
    private String shippingPhone;
    private String shippingAddress;
    private String shippingCity;
    private String shippingDistrict;
    private List<OrderItemResponseDto> orderItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}


