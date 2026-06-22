package com.estacionamiento.payment.controller;

import com.estacionamiento.payment.dto.PaymentRequestDto;
import com.estacionamiento.payment.dto.PaymentResponseDto;
import com.estacionamiento.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Pagos", description = "Gestion de pagos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los pagos")
    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> findAll() {
        logger.info("Buscando todos los pagos");
        return ResponseEntity.ok(paymentService.findAll());
    }

    @Operation(summary = "Obtener pago por ID")
    @ApiResponse(responseCode = "200", description = "Pago encontrado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando pago por id");
        PaymentResponseDto payment = paymentService.findById(id);
        if (payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Obtener pagos por entrada/salida")
    @GetMapping("/entry-exit/{entryExitId}")
    public ResponseEntity<List<PaymentResponseDto>> findByEntryExitId(@PathVariable Long entryExitId) {
        logger.info("Buscando pagos por entrada/salida");
        return ResponseEntity.ok(paymentService.findByEntryExitId(entryExitId));
    }

    @Operation(summary = "Obtener pagos por estado")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PaymentResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando pagos por estado");
        return ResponseEntity.ok(paymentService.findByStatus(status));
    }

    @Operation(summary = "Obtener pago por entrada/salida y estado")
    @ApiResponse(responseCode = "200", description = "Pago encontrado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @GetMapping("/entry-exit/{entryExitId}/status/{status}")
    public ResponseEntity<PaymentResponseDto> findByEntryExitIdAndStatus(
            @PathVariable Long entryExitId, @PathVariable String status) {
        logger.info("Buscando pago por entrada/salida y estado");
        PaymentResponseDto payment = paymentService.findByEntryExitIdAndStatus(entryExitId, status);
        if (payment == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "Registrar nuevo pago")
    @ApiResponse(responseCode = "201", description = "Pago registrado exitosamente")
    @PostMapping
    public ResponseEntity<PaymentResponseDto> create(@Valid @RequestBody PaymentRequestDto dto) {
        logger.info("Registrando pago");
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.create(dto));
    }

    @Operation(summary = "Actualizar pago")
    @ApiResponse(responseCode = "200", description = "Pago actualizado")
    @PutMapping
    public ResponseEntity<PaymentResponseDto> update(@Valid @RequestBody PaymentRequestDto dto) {
        logger.info("Actualizando pago");
        return ResponseEntity.ok(paymentService.update(dto));
    }

    @Operation(summary = "Eliminar pago")
    @ApiResponse(responseCode = "204", description = "Pago eliminado")
    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando pago");
        return paymentService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}