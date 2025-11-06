package com.java.be_ecommerce_app.model.dto.request.category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    
    @NotBlank(message = "Category name cannot be empty")
    private String categoryName;
    
    private String description;
    
    private String imageUrl;
}

