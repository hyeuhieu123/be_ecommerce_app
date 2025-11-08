package com.java.be_ecommerce_app.service.cart;

import com.java.be_ecommerce_app.model.dto.request.cart.AddToCartDto;
import com.java.be_ecommerce_app.model.dto.request.cart.UpdateCartItemDto;
import com.java.be_ecommerce_app.model.dto.response.cart.CartItemResponseDto;
import com.java.be_ecommerce_app.model.dto.response.cart.CartResponseDto;
import com.java.be_ecommerce_app.model.entity.Cart;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CartService {
    CartResponseDto getCart(Authentication authentication);
    CartItemResponseDto addToCart(Authentication authentication, AddToCartDto dto);
    CartItemResponseDto updateCartItem(Authentication authentication, Long cartItemId, UpdateCartItemDto dto);
    void removeCartItem(Authentication authentication, Long cartItemId);
    void clearCart(Authentication authentication);
    Cart createCartForUser(Long userId);
}

