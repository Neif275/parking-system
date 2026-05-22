package com.estacionamiento.reservation.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceResponseDto {
    private Long id;
    private String slotNumber;
    private Boolean isAvailable;
}
