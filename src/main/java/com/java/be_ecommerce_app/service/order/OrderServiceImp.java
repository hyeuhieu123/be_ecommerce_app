package com.java.be_ecommerce_app.service.order;

import com.java.be_ecommerce_app.model.dto.request.order.CreateOrderDto;
import com.java.be_ecommerce_app.model.dto.response.order.OrderItemResponseDto;
import com.java.be_ecommerce_app.model.dto.response.order.OrderResponseDto;
import com.java.be_ecommerce_app.model.entity.*;
import com.java.be_ecommerce_app.repository.CartItemRepository;
import com.java.be_ecommerce_app.repository.CartRepository;
import com.java.be_ecommerce_app.repository.OrderItemRepository;
import com.java.be_ecommerce_app.repository.OrderRepository;
import com.java.be_ecommerce_app.repository.ProductRepository;
import com.java.be_ecommerce_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    @Transactional
    public OrderResponseDto createOrder(Authentication authentication, CreateOrderDto dto) {
        User user = userRepository.findByUsername(authentication.getName());

        // Get checked cart items
        List<CartItem> cartItems = cartItemRepository.findAllById(dto.getCartItemIds());
        
        // Verify all cart items belong to user's cart
        Cart userCart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("User cart not found"));

        for (CartItem cartItem : cartItems) {
            if (!cartItem.getCart().getCartId().equals(userCart.getCartId())) {
                throw new RuntimeException("Cart item does not belong to user's cart");
            }
            if (!cartItem.getChecked()) {
                throw new RuntimeException("Only checked cart items can be ordered");
            }
        }

        if (cartItems.isEmpty()) {
            throw new RuntimeException("No cart items to order");
        }

        // Calculate subtotal from cart items
        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calculate total price
        BigDecimal totalPrice = subtotal.add(dto.getShippingFee());

        // Create order
        Order order = Order.builder()
                .user(user)
                .subtotal(subtotal)
                .shippingFee(dto.getShippingFee())
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.PENDING)
                .shippingName(dto.getShippingName())
                .shippingPhone(dto.getShippingPhone())
                .shippingAddress(dto.getShippingAddress())
                .shippingCity(dto.getShippingCity())
                .shippingDistrict(dto.getShippingDistrict())
                .build();

        order = orderRepository.save(order);

        // Create order items from cart items
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            BigDecimal itemPrice = product.getPrice();
            BigDecimal itemSubtotal = itemPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .productName(product.getProductName())
                    .quantity(cartItem.getQuantity())
                    .price(itemPrice)
                    .subtotal(itemSubtotal)
                    .build();

            orderItemRepository.save(orderItem);
        }

        // Remove checked cart items from cart
        cartItemRepository.deleteAll(cartItems);

        return convertToOrderResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> getOrders(Authentication authentication, OrderStatus status, Pageable pageable) {
        User user = userRepository.findByUsername(authentication.getName());

        Specification<Order> spec = (root, query, cb) -> 
            cb.equal(root.get("user"), user);

        if (status != null) {
            Specification<Order> statusSpec = (root, query, cb) -> 
                cb.equal(root.get("orderStatus"), status);
            spec = spec.and(statusSpec);
        }

        return orderRepository.findAll(spec, pageable)
                .map(this::convertToOrderResponseDto);
    }

    @Override
    public OrderResponseDto getOrderById(Authentication authentication, Long orderId) {
        User user = userRepository.findByUsername(authentication.getName());
        Order order = orderRepository.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return convertToOrderResponseDto(order);
    }

    @Override
    @Transactional
    public OrderResponseDto cancelOrder(Authentication authentication, Long orderId) {
        User user = userRepository.findByUsername(authentication.getName());
        Order order = orderRepository.findByOrderIdAndUser(orderId, user)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        order = orderRepository.save(order);

        return convertToOrderResponseDto(order);
    }

    private OrderResponseDto convertToOrderResponseDto(Order order) {
        List<OrderItemResponseDto> orderItems = order.getOrderItems().stream()
                .map(this::convertToOrderItemResponseDto)
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .subtotal(order.getSubtotal())
                .shippingFee(order.getShippingFee())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .shippingName(order.getShippingName())
                .shippingPhone(order.getShippingPhone())
                .shippingAddress(order.getShippingAddress())
                .shippingCity(order.getShippingCity())
                .shippingDistrict(order.getShippingDistrict())
                .orderItems(orderItems)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private OrderItemResponseDto convertToOrderItemResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .productId(orderItem.getProduct().getProductId())
                .productName(orderItem.getProductName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .subtotal(orderItem.getSubtotal())
                .build();
    }
}

