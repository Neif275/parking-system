package com.estacionamiento.report.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long id;
    private Long entryExitId;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private String status;
}
