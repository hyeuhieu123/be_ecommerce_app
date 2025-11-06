package com.java.be_ecommerce_app.controller;

import com.java.be_ecommerce_app.model.dto.request.category.CategoryRequestDto;
import com.java.be_ecommerce_app.model.dto.response.ApiResponse;
import com.java.be_ecommerce_app.model.dto.response.category.CategoryResponseDto;
import com.java.be_ecommerce_app.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDto>> createCategory(
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
       
            CategoryResponseDto category = categoryService.createCategory(categoryRequestDto);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Category created successfully", category, null),
                    HttpStatus.CREATED);
       
    }
    
    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> getCategoryById(
            @PathVariable Long categoryId) {
            CategoryResponseDto category = categoryService.getCategoryById(categoryId);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Category retrieved successfully", category, null),
                    HttpStatus.OK);
       
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(
                new ApiResponse<>(true, "Categories retrieved successfully", categories, null),
                HttpStatus.OK);
    }
    
    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequestDto categoryRequestDto) {
     
            CategoryResponseDto category = categoryService.updateCategory(categoryId, categoryRequestDto);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Category updated successfully", category, null),
                    HttpStatus.OK);
       
    }
    
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {
      
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(
                    new ApiResponse<>(true, "Category deleted successfully", null, null),
                    HttpStatus.OK);
       
    }
}

