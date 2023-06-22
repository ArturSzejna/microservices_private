package com.example.orderService.external.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class ProductResponse {
    private Long id;
    private String productName;
    private Double price;
    private Long quantity;
}
