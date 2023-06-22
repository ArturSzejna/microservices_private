package com.example.paymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDetailsResponse {

    private String referenceNumber;
    private Instant paymentDate;
    private String paymentStatus;
    private Double amount;
}
