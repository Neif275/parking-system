package com.estacionamiento.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationRequestDto {
    private Long id;
    @NotBlank(message = "La placa patente es obligatoria")
    private String plate;
    @NotNull(message = "El id del usuario es obligatorio")
    private Long ownerUserId;
    @NotNull(message = "El id del espacio de estacionamiento es obligatorio")
    private Long parkingSpaceId;
    @NotNull(message = "La hora de reserva es obligatoria")
    private LocalDateTime reservationTime;
    @NotNull(message = "La hora de expiración es obligatoria")
    private LocalDateTime expirationTime;
    @NotBlank(message = "El estado es obligatorio")
    private String status;
}
