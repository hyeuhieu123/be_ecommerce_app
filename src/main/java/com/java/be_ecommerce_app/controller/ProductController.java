package com.java.be_ecommerce_app.controller;

import com.java.be_ecommerce_app.model.dto.request.product.ProductRequestDto;
import com.java.be_ecommerce_app.model.dto.response.ApiResponse;
import com.java.be_ecommerce_app.model.dto.response.product.ProductResponseDto;
import com.java.be_ecommerce_app.model.dto.response.product.ProductSearch;
import com.java.be_ecommerce_app.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto productRequestDto) {
            ProductResponseDto product = productService.createProduct(productRequestDto);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Product created successfully", product, null),
                    HttpStatus.CREATED);
      
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(
            @PathVariable Long productId) {
            ProductResponseDto product = productService.getProductById(productId);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Product retrieved successfully", product, null),
                    HttpStatus.OK);
            }
       
    
    @GetMapping
    public ResponseEntity<ApiResponse<ProductSearch>> getAllProducts(@RequestParam(required = false) String name ) {
        ProductSearch products = productService.getAllProducts( name);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Products retrieved successfully", products, null),
                HttpStatus.OK);
    }
    
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProductsByCategoryId(
            @PathVariable Long categoryId) {
        List<ProductResponseDto> products = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Products retrieved successfully", products, null),
                HttpStatus.OK);
    }
    
//    @GetMapping("/search")
//    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> searchProductsByName(
//            @RequestParam String productName) {
//        List<ProductResponseDto> products = productService.searchProductsByName(productName);
//        return new ResponseEntity<>(
//                new ApiResponse<>(true, "Products retrieved successfully", products, null),
//                HttpStatus.OK);
//    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequestDto productRequestDto) {
            ProductResponseDto product = productService.updateProduct(productId, productRequestDto);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Product updated successfully", product, null),
                    HttpStatus.OK);
      
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Product deleted successfully", null, null),
                    HttpStatus.OK);
      
    }
}

