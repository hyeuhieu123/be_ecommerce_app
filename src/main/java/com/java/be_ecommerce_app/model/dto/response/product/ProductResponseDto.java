package com.java.be_ecommerce_app.model.dto.response.product;

import com.java.be_ecommerce_app.model.dto.response.category.CategoryResponseDto;
import com.java.be_ecommerce_app.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private CategoryResponseDto category;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

