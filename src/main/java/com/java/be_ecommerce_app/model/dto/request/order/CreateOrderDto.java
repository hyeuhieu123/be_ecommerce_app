package com.java.be_ecommerce_app.model.dto.request.order;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDto {
    @NotNull(message = "Cart item IDs cannot be null")
    @NotEmpty(message = "Cart item IDs cannot be empty")
    private List<Long> cartItemIds;

    @NotNull(message = "Shipping fee cannot be null")
    @DecimalMin(value = "0.0", message = "Shipping fee must be greater than or equal to 0")
    private BigDecimal shippingFee;

    // Shipping Info
    @NotBlank(message = "Shipping name cannot be blank")
    @Size(max = 255, message = "Shipping name must not exceed 255 characters")
    private String shippingName;

    @NotBlank(message = "Shipping phone cannot be blank")
    @Size(max = 20, message = "Shipping phone must not exceed 20 characters")
    private String shippingPhone;

    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;

    @Size(max = 100, message = "Shipping city must not exceed 100 characters")
    private String shippingCity;

    @Size(max = 100, message = "Shipping district must not exceed 100 characters")
    private String shippingDistrict;

    // Payment method (default: CASH)
    private String paymentMethod = "CASH";
}


