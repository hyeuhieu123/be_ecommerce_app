package com.java.be_ecommerce_app.repository.specification;

import com.java.be_ecommerce_app.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {
    public static Specification<Product> byName(String name) {
        return (root, query, criteriaBuilder) -> {
            if(name==null || name.isEmpty()){
                return criteriaBuilder.conjunction();}


            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("productName")),
                    "%" + name.toLowerCase() + "%"
            );

        };
    }
}
