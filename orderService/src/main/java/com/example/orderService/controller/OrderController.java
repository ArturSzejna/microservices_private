package com.example.orderService.controller;

import com.example.orderService.model.OrderRequest;
import com.example.orderService.model.OrderResponse;
import com.example.orderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest request) {
        Long orderId = orderService.placeOrder(request);
        log.info("Order Id: {}", orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderId);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }
}
