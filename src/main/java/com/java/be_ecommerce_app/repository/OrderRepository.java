package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.Order;
import com.java.be_ecommerce_app.model.entity.OrderStatus;
import com.java.be_ecommerce_app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    List<Order> findByUser(User user);
    List<Order> findByUserAndOrderStatus(User user, OrderStatus orderStatus);
    Optional<Order> findByOrderIdAndUser(Long orderId, User user);
}


