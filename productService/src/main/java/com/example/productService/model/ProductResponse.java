package com.example.productService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String productName;
    private Double price;
    private Long quantity;
}
