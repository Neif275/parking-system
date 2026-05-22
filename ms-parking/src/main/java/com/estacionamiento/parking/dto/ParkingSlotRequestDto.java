package com.estacionamiento.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotRequestDto {

    private Long id;

    @NotBlank(message = "El numero del espacio es obligatorio")
    private String slotNumber;

    @NotNull(message = "La zona es obligatoria")
    private Integer zoneId;

    @NotNull(message = "El tipo de estacionamiento es obligatorio")
    private Integer slotTypeId;
    private Boolean isAvailable = true;
}
