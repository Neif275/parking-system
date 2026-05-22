package com.estacionamiento.entryexit.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TariffResponseDto {
    private Long id;
    private String name;
    private String vehicleType;
    private BigDecimal pricePerMinute;
    private String description;
}
