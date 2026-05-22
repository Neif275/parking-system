package com.estacionamiento.entryexit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntryExitRequestDto {
    private Long id;
    @NotBlank(message = "La placa patente es obligatoria")
    private String plate;
    @NotNull(message = "La hora de entrada es obligatoria")
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    @NotNull(message = "El id del espacio de estacionamiento es obligatorio")
    private Long parkingSpaceId;
    @NotNull(message = "El id de la tarifa es obligatorio")
    private Long tariffId;
    @NotBlank(message = "El estado es obligatorio")
    private String status;
}
