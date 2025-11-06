package com.java.be_ecommerce_app.service.category;

import com.java.be_ecommerce_app.model.dto.request.category.CategoryRequestDto;
import com.java.be_ecommerce_app.model.dto.response.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto dto);
    CategoryResponseDto getCategoryById(Long categoryId);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto dto);
    void deleteCategory(Long categoryId);
}

