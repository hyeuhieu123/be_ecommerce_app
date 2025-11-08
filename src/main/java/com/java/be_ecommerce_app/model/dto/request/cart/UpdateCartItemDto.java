package com.java.be_ecommerce_app.model.dto.request.cart;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartItemDto {
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity; // Optional: only required when updating quantity
    
    private Boolean checked; // Optional: for toggling checked status
}

