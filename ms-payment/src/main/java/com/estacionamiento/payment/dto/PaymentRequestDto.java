package com.estacionamiento.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentRequestDto {
    private Long id;
    @NotNull(message = "El id de entrada / salida es obligatorio")
    private Long entryExitId;
    @NotNull(message = "El precio a pagar es obligatorio")
    private BigDecimal amount;
    @NotBlank(message = "El metodo de pago es obligatorio")
    private String paymentMethod;
    @NotNull(message = "La hora de pago es obligatoria")
    private LocalDateTime paymentTime;
    @NotBlank(message = "El estado es obligatorio")
    private String status;
}
