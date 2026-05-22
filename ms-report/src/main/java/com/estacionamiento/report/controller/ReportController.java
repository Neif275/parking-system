package com.estacionamiento.report.controller;

import com.estacionamiento.report.dto.ReportRequestDto;
import com.estacionamiento.report.dto.ReportResponseDto;
import com.estacionamiento.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> findAll() {
        logger.info("Buscando todos los reportes");
        return ResponseEntity.ok(reportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando reporte por id");
        ReportResponseDto report = reportService.findById(id);
        if (report == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ReportResponseDto>> findByType(@PathVariable String type) {
        logger.info("Buscando reportes por tipo");
        return ResponseEntity.ok(reportService.findByType(type));
    }

    @GetMapping("/user/{generatedBy}")
    public ResponseEntity<List<ReportResponseDto>> findByGeneratedBy(@PathVariable Long generatedBy) {
        logger.info("Buscando reportes por usuario");
        return ResponseEntity.ok(reportService.findByGeneratedBy(generatedBy));
    }

    @PostMapping
    public ResponseEntity<ReportResponseDto> create(@Valid @RequestBody ReportRequestDto dto) {
        logger.info("Generando reporte");
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.create(dto));
    }

    @PutMapping
    public ResponseEntity<ReportResponseDto> update(@Valid @RequestBody ReportRequestDto dto) {
        logger.info("Actualizando reporte");
        return ResponseEntity.ok(reportService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando reporte");
        return reportService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
