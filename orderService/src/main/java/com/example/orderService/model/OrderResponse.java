package com.example.orderService.model;

import com.example.orderService.external.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long orderId;
    private Instant orderDate;
    private String orderStatus;
    private Double amount;
    private ProductResponse productDetails;
    private PaymentDetailsResponse paymentDetails;
}
