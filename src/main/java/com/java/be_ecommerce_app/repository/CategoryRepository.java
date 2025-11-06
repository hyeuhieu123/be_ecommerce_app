package com.java.be_ecommerce_app.repository;

import com.java.be_ecommerce_app.model.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Optional<Category> findByCategoryId(Long categoryId);
    
    boolean existsByCategoryName(String categoryName);
    
    Optional<Category> findByCategoryName(String categoryName);
}

