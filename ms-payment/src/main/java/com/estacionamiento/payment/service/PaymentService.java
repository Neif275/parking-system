package com.estacionamiento.payment.service;

import com.estacionamiento.payment.dto.PaymentRequestDto;
import com.estacionamiento.payment.dto.PaymentResponseDto;
import java.util.List;

public interface PaymentService {
    PaymentResponseDto findById(Long id);
    List<PaymentResponseDto> findAll();
    List<PaymentResponseDto> findByEntryExitId(Long entryExitId);
    List<PaymentResponseDto> findByStatus(String status);
    PaymentResponseDto findByEntryExitIdAndStatus(Long entryExitId, String status);
    PaymentResponseDto create(PaymentRequestDto dto);
    PaymentResponseDto update(PaymentRequestDto dto);
    boolean deleteById(Long id);
}
