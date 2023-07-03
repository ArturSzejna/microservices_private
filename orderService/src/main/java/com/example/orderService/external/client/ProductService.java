package com.example.orderService.external.client;

import com.example.orderService.exception.CustomException;
import com.example.orderService.external.response.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {

    @PutMapping("/reduceQuantity/{id}")
    @CircuitBreaker(name = "external", fallbackMethod = "reduceQuantityFallback")
    public ResponseEntity<Void> reduceQuantity(@PathVariable("id") Long productId, @RequestParam Long quantity);

    @GetMapping("/{id}")
    @CircuitBreaker(name = "external", fallbackMethod = "getProductByIdFallback")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id);

    default ResponseEntity<Void> reduceQuantityFallback(Exception e){
        throw new CustomException("Product Service is not available", "UNAVAILABLE", 500);
    }

    default ResponseEntity<ProductResponse> getProductByIdFallback(Exception e){
        throw new CustomException("Product Service is not available", "UNAVAILABLE", 500);
    }
}
