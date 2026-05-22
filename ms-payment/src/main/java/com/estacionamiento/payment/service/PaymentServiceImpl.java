package com.estacionamiento.payment.service;

import com.estacionamiento.payment.client.EntryExitClient;
import com.estacionamiento.payment.client.dto.EntryExitResponseDto;
import com.estacionamiento.payment.dto.PaymentRequestDto;
import com.estacionamiento.payment.dto.PaymentResponseDto;
import com.estacionamiento.payment.model.PaymentModel;
import com.estacionamiento.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final EntryExitClient entryExitClient;

    private PaymentResponseDto toDto(PaymentModel entity) {
        return new PaymentResponseDto(
                entity.getId(),
                entity.getEntryExitId(),
                entity.getAmount(),
                entity.getPaymentMethod(),
                entity.getPaymentTime(),
                entity.getStatus()
        );
    }

    private PaymentModel toEntity(PaymentRequestDto dto) {
        return new PaymentModel(
                dto.getId(),
                dto.getEntryExitId(),
                dto.getAmount(),
                dto.getPaymentMethod(),
                dto.getPaymentTime(),
                dto.getStatus()
        );
    }

    @Override
    public PaymentResponseDto findById(Long id) {
        return paymentRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<PaymentResponseDto> findAll() {
        return paymentRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<PaymentResponseDto> findByEntryExitId(Long entryExitId) {
        return paymentRepository.findByEntryExitId(entryExitId).stream().map(this::toDto).toList();
    }

    @Override
    public List<PaymentResponseDto> findByStatus(String status) {
        return paymentRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public PaymentResponseDto findByEntryExitIdAndStatus(Long entryExitId, String status) {
        return paymentRepository.findByEntryExitIdAndStatus(entryExitId, status)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public PaymentResponseDto create(PaymentRequestDto dto) {
        EntryExitResponseDto entryExit = entryExitClient.getEntryExitById(dto.getEntryExitId());
        if (entryExit == null) {
            throw new IllegalArgumentException("No existe un registro de entrada/salida con id: " + dto.getEntryExitId());
        }
        return toDto(paymentRepository.save(toEntity(dto)));
    }

    @Override
    public PaymentResponseDto update(PaymentRequestDto dto) {
        return toDto(paymentRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
