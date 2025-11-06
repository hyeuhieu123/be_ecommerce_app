package com.java.be_ecommerce_app.service.product;

import com.java.be_ecommerce_app.model.dto.request.product.ProductRequestDto;
import com.java.be_ecommerce_app.model.dto.response.product.ProductResponseDto;
import com.java.be_ecommerce_app.model.dto.response.product.ProductSearch;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto dto);
    ProductResponseDto getProductById(Long productId);
    ProductSearch getAllProducts(String name);
    List<ProductResponseDto> getProductsByCategoryId(Long categoryId);
    List<ProductResponseDto> searchProductsByName(String productName);
    ProductResponseDto updateProduct(Long productId, ProductRequestDto dto);
    void deleteProduct(Long productId);
}

