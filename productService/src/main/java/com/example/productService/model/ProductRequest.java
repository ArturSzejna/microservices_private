package com.example.productService.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private Double price;
    private Long quantity;
}
