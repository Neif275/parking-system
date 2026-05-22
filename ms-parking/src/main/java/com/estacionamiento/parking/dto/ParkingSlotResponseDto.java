package com.estacionamiento.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotResponseDto {

    private Long id;
    private String slotNumber;
    private ZoneResponseDto zone;
    private SlotTypeResponseDto slotType;
    private Boolean isAvailable;
}
