package com.example.paymentService.service;

import com.example.paymentService.entity.TransactionDetails;
import com.example.paymentService.model.PaymentRequest;
import com.example.paymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentService {

    private final TransactionDetailsRepository transactionDetailsRepository;

    public PaymentService(TransactionDetailsRepository transactionDetailsRepository) {
        this.transactionDetailsRepository = transactionDetailsRepository;
    }

    public Long doPayment(PaymentRequest request) {
        log.info("Recording Payment Details: {}", request);

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(request.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(request.getOrderId())
                .referenceNumber(request.getReferenceNumber())
                .amount(request.getAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed with Id: {}", transactionDetails.getId());
        return transactionDetails.getId();
    }
}
