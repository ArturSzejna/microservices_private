package com.example.orderService.service;

import com.example.orderService.entity.Order;
import com.example.orderService.exception.CustomException;
import com.example.orderService.external.client.PaymentService;
import com.example.orderService.external.client.ProductService;
import com.example.orderService.external.request.PaymentRequest;
import com.example.orderService.external.response.ProductResponse;
import com.example.orderService.model.OrderRequest;
import com.example.orderService.model.OrderResponse;
import com.example.orderService.model.PaymentDetailsResponse;
import com.example.orderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
@Log4j2
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService,
                        PaymentService paymentService,
                        RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.paymentService = paymentService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Long placeOrder(OrderRequest request) {

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

        log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(request.getPaymentMode())
                .amount(request.getTotalAmount())
                .build();

        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the order status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.info("Error occured in payment. Changing the order status to PAYMENT_FAILED");
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }

    public OrderResponse getOrderDetails(Long orderId) {
        log.info("Get order details for Order Id : {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order not found for the order Id: " + orderId, "NOT_FOUND", 404));

        ProductResponse productResponse = productService.getProductById(order.getProductId()).getBody();
        log.info("Invoking Product service to fetch the product for id: {}", order.getProductId());

        PaymentDetailsResponse paymentDetailsResponse = paymentService.getPaymentDetailsByOrderId(orderId).getBody();
        log.info("Invoking Payment service to fetch the payment for order id: {}", order.getId());

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productResponse)
                .paymentDetails(paymentDetailsResponse)
                .build();
    }
}
