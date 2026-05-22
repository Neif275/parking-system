package com.estacionamiento.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportResponseDto {
    private Long id;
    private String title;
    private String type;
    private Long generatedBy;
    private LocalDateTime generatedAt;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
