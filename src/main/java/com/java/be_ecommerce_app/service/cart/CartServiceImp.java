package com.java.be_ecommerce_app.service.cart;

import com.java.be_ecommerce_app.model.dto.request.cart.AddToCartDto;
import com.java.be_ecommerce_app.model.dto.request.cart.UpdateCartItemDto;
import com.java.be_ecommerce_app.model.dto.response.cart.CartItemResponseDto;
import com.java.be_ecommerce_app.model.dto.response.cart.CartResponseDto;
import com.java.be_ecommerce_app.model.dto.response.product.ProductResponseDto;
import com.java.be_ecommerce_app.model.entity.Cart;
import com.java.be_ecommerce_app.model.entity.CartItem;
import com.java.be_ecommerce_app.model.entity.Product;
import com.java.be_ecommerce_app.model.entity.User;
import com.java.be_ecommerce_app.repository.CartItemRepository;
import com.java.be_ecommerce_app.repository.CartRepository;
import com.java.be_ecommerce_app.repository.ProductRepository;
import com.java.be_ecommerce_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CartResponseDto getCart(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Cart cart = getOrCreateCart(user);

        List<CartItemResponseDto> cartItems = cart.getCartItems().stream()
                .map(this::convertToCartItemResponseDto)
                .collect(Collectors.toList());

        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = cart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        return CartResponseDto.builder()
                .cartId(cart.getCartId())
                .cartItems(cartItems)
                .totalItems(totalItems)
                .totalPrice(totalPrice)
                .build();
    }

    @Override
    @Transactional
    public CartItemResponseDto addToCart(Authentication authentication, AddToCartDto dto) {
        User user = userRepository.findByUsername(authentication.getName());
        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if product already exists in cart
        Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            // Update quantity (increase)
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
        } else {
            // Create new cart item
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(dto.getQuantity())
                    .checked(true) // Default to unchecked
                    .build();
        }

        cartItem = cartItemRepository.save(cartItem);
        return convertToCartItemResponseDto(cartItem);
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCartItem(Authentication authentication, Long cartItemId, UpdateCartItemDto dto) {
        User user = userRepository.findByUsername(authentication.getName());
        Cart cart = getOrCreateCart(user);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Verify cart item belongs to user's cart
        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            throw new RuntimeException("Cart item does not belong to user's cart");
        }

        // Update quantity if provided
        if (dto.getQuantity() != null) {
            cartItem.setQuantity(dto.getQuantity());
        }
        
        // Update checked status if provided
        if (dto.getChecked() != null) {
            cartItem.setChecked(dto.getChecked());
        }
        
        cartItem = cartItemRepository.save(cartItem);

        return convertToCartItemResponseDto(cartItem);
    }

    @Override
    @Transactional
    public void removeCartItem(Authentication authentication, Long cartItemId) {
        User user = userRepository.findByUsername(authentication.getName());
        Cart cart = getOrCreateCart(user);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Verify cart item belongs to user's cart
        if (!cartItem.getCart().getCartId().equals(cart.getCartId())) {
            throw new RuntimeException("Cart item does not belong to user's cart");
        }

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void clearCart(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        Cart cart = getOrCreateCart(user);

        cartItemRepository.deleteByCart(cart);
    }

    @Override
    @Transactional
    public Cart createCartForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if cart already exists
        Optional<Cart> existingCart = cartRepository.findByUser(user);
        if (existingCart.isPresent()) {
            return existingCart.get();
        }

        // Create new cart
        Cart cart = Cart.builder()
                .user(user)
                .build();

        return cartRepository.save(cart);
    }

    private Cart getOrCreateCart(User user) {
        Optional<Cart> cart = cartRepository.findByUser(user);
        return cart.orElseGet(() -> createCartForUser(user.getUserId()));
    }

    private CartItemResponseDto convertToCartItemResponseDto(CartItem cartItem) {
        Product product = cartItem.getProduct();
        ProductResponseDto productDto = ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();

        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return CartItemResponseDto.builder()
                .cartItemId(cartItem.getCartItemId())
                .product(productDto)
                .quantity(cartItem.getQuantity())
                .checked(cartItem.getChecked())
                .subtotal(subtotal)
                .createdAt(cartItem.getCreatedAt())
                .updatedAt(cartItem.getUpdatedAt())
                .build();
    }
}
