package com.java.be_ecommerce_app.model.dto.response.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponseDto {
    private Long cartId;
    private List<CartItemResponseDto> cartItems;
    private Integer totalItems;
    private BigDecimal totalPrice; // Sum of all cart items subtotal
}

