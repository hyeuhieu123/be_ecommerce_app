package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.Cart;
import com.java.be_ecommerce_app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
    Optional<Cart> findByUser_UserId(Long userId);
}

