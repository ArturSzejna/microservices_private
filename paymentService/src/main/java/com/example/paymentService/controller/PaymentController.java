package com.example.paymentService.controller;

import com.example.paymentService.model.PaymentDetailsResponse;
import com.example.paymentService.model.PaymentRequest;
import com.example.paymentService.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.doPayment(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDetailsResponse> getPaymentDetailsByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentDetailsByOrderId(orderId));
    }
}
