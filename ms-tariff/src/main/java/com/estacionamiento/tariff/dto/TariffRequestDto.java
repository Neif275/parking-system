package com.estacionamiento.tariff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TariffRequestDto {
    private Long id;
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String description;
    @NotNull(message = "El precio por minuto es obligatorio")
    private BigDecimal pricePerMinute;
    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String vehicleType;
}
