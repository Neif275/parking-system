package com.estacionamiento.payment.service;

import com.estacionamiento.payment.client.EntryExitClient;
import com.estacionamiento.payment.client.dto.EntryExitResponseDto;
import com.estacionamiento.payment.dto.PaymentRequestDto;
import com.estacionamiento.payment.dto.PaymentResponseDto;
import com.estacionamiento.payment.model.PaymentModel;
import com.estacionamiento.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

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
        log.info("Buscando pago con id: {}", id);
        PaymentResponseDto result = paymentRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Pago con id {} no encontrado", id);
        return result;
    }

    @Override
    public List<PaymentResponseDto> findAll() {
        log.info("Consultando todos los pagos");
        return paymentRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<PaymentResponseDto> findByEntryExitId(Long entryExitId) {
        log.info("Buscando pagos por registro entrada/salida id: {}", entryExitId);
        return paymentRepository.findByEntryExitId(entryExitId).stream().map(this::toDto).toList();
    }

    @Override
    public List<PaymentResponseDto> findByStatus(String status) {
        log.info("Buscando pagos por estado: {}", status);
        return paymentRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public PaymentResponseDto findByEntryExitIdAndStatus(Long entryExitId, String status) {
        return paymentRepository.findByEntryExitIdAndStatus(entryExitId, status).map(this::toDto).orElse(null);
    }

    @Override
    public PaymentResponseDto create(PaymentRequestDto dto) {
        log.info("Procesando pago para registro entrada/salida id: {}, metodo: {}", dto.getEntryExitId(), dto.getPaymentMethod());
        EntryExitResponseDto entryExit = entryExitClient.getEntryExitById(dto.getEntryExitId());
        if (entryExit == null) {
            log.error("Registro entrada/salida con id {} no encontrado al procesar pago", dto.getEntryExitId());
            throw new IllegalArgumentException("No existe un registro de entrada/salida con id: " + dto.getEntryExitId());
        }
        PaymentResponseDto result = toDto(paymentRepository.save(toEntity(dto)));
        log.info("Pago registrado con id: {}, monto: {}", result.getId(), result.getAmount());
        return result;
    }

    @Override
    public PaymentResponseDto update(PaymentRequestDto dto) {
        log.info("Actualizando pago con id: {}", dto.getId());
        return toDto(paymentRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            log.info("Pago con id {} eliminado", id);
            return true;
        }
        log.warn("No se pudo eliminar: pago con id {} no existe", id);
        return false;
    }
}
