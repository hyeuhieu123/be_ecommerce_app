package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByProductId(Long productId);
    
    List<Product> findByCategory_CategoryId(Long categoryId);
    
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}

