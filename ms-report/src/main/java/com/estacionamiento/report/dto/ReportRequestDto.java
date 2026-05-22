package com.estacionamiento.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportRequestDto {
    private Long id;
    @NotBlank(message = "El titulo es obligatorio")
    private String title;
    @NotBlank(message = "El tipo de reporte es obligatorio")
    private String type;
    @NotNull(message = "El id del usuario generador es obligatorio")
    private Long generatedBy;
    @NotNull(message = "La fecha de generacion es obligatoria")
    private LocalDateTime generatedAt;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime dateFrom;
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime dateTo;
}
