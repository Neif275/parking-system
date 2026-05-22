package com.estacionamiento.report.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDto {
    private Long id;
    private String plate;
    private String color;
    private String year;
    private Long ownerUserId;
}
