package com.estacionamiento.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorResponseDto {

    private Integer id;
    private Integer number;
    private String description;
}
