package com.java.be_ecommerce_app.model.dto.response.product;

import com.java.be_ecommerce_app.model.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearch {
    private int count;
    private List<Product> productList;
}
