package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.Cart;
import com.java.be_ecommerce_app.model.entity.CartItem;
import com.java.be_ecommerce_app.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    void deleteByCart(Cart cart);
}

