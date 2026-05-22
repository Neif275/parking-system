package com.estacionamiento.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModelResponseDto {
    private Integer id;
    private String name;
    private BrandResponseDto brand;
}
