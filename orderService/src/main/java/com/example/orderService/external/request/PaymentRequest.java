package com.example.orderService.external.request;

import com.example.orderService.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;
    private Double amount;
    private String referenceNumber;
    private PaymentMode paymentMode;

}
