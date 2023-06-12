package com.example.paymentService.controller;

import com.example.paymentService.model.PaymentRequest;
import com.example.paymentService.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.doPayment(request));
    }
}
