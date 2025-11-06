package com.java.be_ecommerce_app.service.product;

import com.java.be_ecommerce_app.model.dto.request.product.ProductRequestDto;
import com.java.be_ecommerce_app.model.dto.response.category.CategoryResponseDto;
import com.java.be_ecommerce_app.model.dto.response.product.ProductResponseDto;
import com.java.be_ecommerce_app.model.dto.response.product.ProductSearch;
import com.java.be_ecommerce_app.model.entity.Category;
import com.java.be_ecommerce_app.model.entity.Product;
import com.java.be_ecommerce_app.repository.CategoryRepository;
import com.java.be_ecommerce_app.repository.ProductRepository;
import com.java.be_ecommerce_app.repository.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductServiceImp implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
        }
        
        Product product = Product.builder()
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0)
                .category(category)
                .imageUrl(dto.getImageUrl())
                .build();
        
        Product savedProduct = productRepository.save(product);
        return mapToResponseDto(savedProduct);
    }
    
    @Override
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return mapToResponseDto(product);
    }
    
    @Override
    public ProductSearch getAllProducts(String name) {
        Specification<Product> specification = Specification.allOf(
                ProductSpecification.byName(name)
        );

        List<Product> products = productRepository.findAll( specification);
        int count =products.size();
            return new ProductSearch(count, products);
    }
    
    @Override
    public List<ProductResponseDto> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId);
        return products.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductResponseDto> searchProductsByName(String productName) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);
        return products.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategoryId()));
        }
        
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : product.getStockQuantity());
        product.setCategory(category);
        product.setImageUrl(dto.getImageUrl());
        
        Product updatedProduct = productRepository.save(product);
        return mapToResponseDto(updatedProduct);
    }
    
    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        productRepository.delete(product);
    }
    
    private ProductResponseDto mapToResponseDto(Product product) {
        CategoryResponseDto categoryDto = null;
        if (product.getCategory() != null) {
            categoryDto = CategoryResponseDto.builder()
                    .categoryId(product.getCategory().getCategoryId())
                    .categoryName(product.getCategory().getCategoryName())
                    .description(product.getCategory().getDescription())
                    .imageUrl(product.getCategory().getImageUrl())
                    .createdAt(product.getCategory().getCreatedAt())
                    .updatedAt(product.getCategory().getUpdatedAt())
                    .build();
        }
        
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(categoryDto)
                .imageUrl(product.getImageUrl())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}

