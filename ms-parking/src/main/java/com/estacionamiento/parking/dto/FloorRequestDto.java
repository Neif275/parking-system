package com.estacionamiento.parking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorRequestDto {

    private Integer id;

    @NotNull(message = "El numero del piso es obligatorio")
    private Integer number;
    private String description;
}
