package com.example.orderService.external.client;

import com.example.orderService.exception.CustomException;
import com.example.orderService.external.request.PaymentRequest;
import com.example.orderService.model.PaymentDetailsResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {

    @PostMapping
    @CircuitBreaker(name = "external", fallbackMethod = "doPaymentFallback")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request);

    @GetMapping("/{orderId}")
    @CircuitBreaker(name = "external", fallbackMethod = "getPaymentDetailsByOrderIdFallback")
    public ResponseEntity<PaymentDetailsResponse> getPaymentDetailsByOrderId(@PathVariable Long orderId);

    default ResponseEntity<Long> doPaymentFallback(Exception e){
        throw new CustomException("Payment Service is not available", "UNAVAILABLE", 500);
    }

    default ResponseEntity<PaymentDetailsResponse> getPaymentDetailsByOrderIdFallback(Exception e){
        throw new CustomException("Payment Service is not available", "UNAVAILABLE", 500);
    }
}
