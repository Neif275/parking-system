package com.estacionamiento.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneRequestDto {

    private Integer id;

    @NotBlank(message = "El nombre de la zona es obligatorio")
    private String name;

    @NotNull(message = "El piso es obligatorio")
    private Integer floorId;
}
