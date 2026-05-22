package com.estacionamiento.report.client;

import com.estacionamiento.report.client.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "payment")
public interface PaymentClient {

    @GetMapping("/api/v1/payments/{id}")
    PaymentResponseDto getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/payments")
    List<PaymentResponseDto> getAllPayments();

    @GetMapping("/api/v1/payments/status/{status}")
    List<PaymentResponseDto> getPaymentsByStatus(@PathVariable("status") String status);
}
