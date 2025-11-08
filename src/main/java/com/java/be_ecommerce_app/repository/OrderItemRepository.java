package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.Order;
import com.java.be_ecommerce_app.model.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}


