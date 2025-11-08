package com.java.be_ecommerce_app.model.dto.response.cart;

import com.java.be_ecommerce_app.model.dto.response.product.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponseDto {
    private Long cartItemId;
    private ProductResponseDto product;
    private Integer quantity;
    private Boolean checked;
    private BigDecimal subtotal; // product.price * quantity
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

