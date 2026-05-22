package com.estacionamiento.parking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlotTypeRequestDto {

    private Integer id;

    @NotBlank(message = "El nombre del tipo de cajón es obligatorio")
    private String name;
    private String description;
}
