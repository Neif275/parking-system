package com.estacionamiento.report.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String type;
    @Column(name = "generated_by")
    private Long generatedBy;
    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
    @Column(name = "date_from")
    private LocalDateTime dateFrom;
    @Column(name = "date_to")
    private LocalDateTime dateTo;
}
