package com.example.orderService.external.client;

import com.example.orderService.external.request.PaymentRequest;
import com.example.orderService.model.PaymentDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDetailsResponse> getPaymentDetailsByOrderId(@PathVariable Long orderId);
}
