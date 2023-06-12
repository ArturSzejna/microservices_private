package com.example.orderService.service;

import com.example.orderService.entity.Order;
import com.example.orderService.external.client.ProductService;
import com.example.orderService.model.OrderRequest;
import com.example.orderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Long placeOrder(OrderRequest request) {

        //TODO Product Service - Block Product (Reduce the Quantity)
        //TODO Payment Service - Payments -> Success -> COMPLETE, else CANCELLED
        log.info("Placing Order Request: {}", request);

        productService.reduceQuantity(request.getProductId(), request.getQuantity());
        log.info("Creating Order with status CREATED");

        Order order = Order.builder()
                .amount(request.getTotalAmount())
                .orderStatus("CREATED")
                .productId(request.getProductId())
                .orderDate(Instant.now())
                .quantity(request.getQuantity())
                .build();

        orderRepository.save(order);
        log.info("Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }
}
