package com.java.be_ecommerce_app.controller;

import com.java.be_ecommerce_app.model.dto.request.cart.AddToCartDto;
import com.java.be_ecommerce_app.model.dto.request.cart.UpdateCartItemDto;
import com.java.be_ecommerce_app.model.dto.response.ApiResponse;
import com.java.be_ecommerce_app.model.dto.response.cart.CartItemResponseDto;
import com.java.be_ecommerce_app.model.dto.response.cart.CartResponseDto;
import com.java.be_ecommerce_app.service.cart.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/items")
    public ResponseEntity<ApiResponse<CartResponseDto>> getCartItems(Authentication authentication) {
        CartResponseDto cart = cartService.getCart(authentication);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Cart items retrieved successfully", cart, null),
                HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addToCart(
            Authentication authentication,
            @Valid @RequestBody AddToCartDto addToCartDto) {
        CartItemResponseDto cartItem = cartService.addToCart(authentication, addToCartDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Product added to cart successfully", cartItem, null),
                HttpStatus.CREATED);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemDto updateCartItemDto) {
        CartItemResponseDto cartItem = cartService.updateCartItem(authentication, cartItemId, updateCartItemDto);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Cart item updated successfully", cartItem, null),
                HttpStatus.OK);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
            Authentication authentication,
            @PathVariable Long cartItemId) {
        cartService.removeCartItem(authentication, cartItemId);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Cart item removed successfully", null, null),
                HttpStatus.OK);
    }

    @DeleteMapping("/items")
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication) {
        cartService.clearCart(authentication);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Cart cleared successfully", null, null),
                HttpStatus.OK);
    }
}
