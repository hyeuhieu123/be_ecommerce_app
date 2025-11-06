package com.java.be_ecommerce_app.service.category;

import com.java.be_ecommerce_app.model.dto.request.category.CategoryRequestDto;
import com.java.be_ecommerce_app.model.dto.response.category.CategoryResponseDto;
import com.java.be_ecommerce_app.model.entity.Category;
import com.java.be_ecommerce_app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImp implements CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto dto) {
        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new RuntimeException("Category name already exists");
        }
        
        Category category = Category.builder()
                .categoryName(dto.getCategoryName())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
        
        Category savedCategory = categoryRepository.save(category);
        return mapToResponseDto(savedCategory);
    }
    
    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        return mapToResponseDto(category);
    }
    
    @Override
    public List<CategoryResponseDto> getAllCategories() {
        Iterable<Category> categories = categoryRepository.findAll();
        return StreamSupport.stream(categories.spliterator(), false)
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto dto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        
        // Check if new category name already exists (excluding current category)
        if (!category.getCategoryName().equals(dto.getCategoryName()) 
                && categoryRepository.existsByCategoryName(dto.getCategoryName())) {
            throw new RuntimeException("Category name already exists");
        }
        
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        
        Category updatedCategory = categoryRepository.save(category);
        return mapToResponseDto(updatedCategory);
    }
    
    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        categoryRepository.delete(category);
    }
    
    private CategoryResponseDto mapToResponseDto(Category category) {
        return CategoryResponseDto.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}

