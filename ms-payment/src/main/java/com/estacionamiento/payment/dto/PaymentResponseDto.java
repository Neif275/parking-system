package com.estacionamiento.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResponseDto {
    private Long id;
    private Long entryExitId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String status;
}
