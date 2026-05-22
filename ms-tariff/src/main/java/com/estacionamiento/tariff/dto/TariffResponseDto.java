package com.estacionamiento.tariff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TariffResponseDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal pricePerMinute;
    private String vehicleType;
}
