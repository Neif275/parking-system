package com.estacionamiento.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleResponseDto {
    private Long  id;
    private String plate;
    private String color;
    private String year;
    private ModelResponseDto model;
    private VehicleCategoryResponseDto category;
    private Long ownerUserId;
}
