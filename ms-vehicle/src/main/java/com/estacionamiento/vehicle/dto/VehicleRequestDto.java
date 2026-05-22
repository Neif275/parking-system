package com.estacionamiento.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleRequestDto {

    Long id;
    @NotBlank(message = "La placa patente es obligatoria")
    String plate;
    String color;
    String year;
    @NotNull(message = "El id de modelo es obligatorio")
    Integer modelId;
    @NotNull(message = "El id de categoría de vehículo es obligatorio")
    Integer categoryId;
    Long ownerUserId;

}
