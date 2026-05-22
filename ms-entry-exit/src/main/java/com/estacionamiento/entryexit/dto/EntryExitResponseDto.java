package com.estacionamiento.entryexit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntryExitResponseDto {
    private Long id;
    private String plate;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long parkingSpaceId;
    private Long tariffId;
    private String status;
}
