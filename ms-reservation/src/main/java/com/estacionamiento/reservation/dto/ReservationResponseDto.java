package com.estacionamiento.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationResponseDto {
    private Long id;
    private String plate;
    private Long ownerUserId;
    private Long parkingSpaceId;
    private LocalDateTime reservationTime;
    private LocalDateTime expirationTime;
    private String status;
}
