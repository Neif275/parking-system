package com.estacionamiento.report.controller;

import com.estacionamiento.report.dto.ReportRequestDto;
import com.estacionamiento.report.dto.ReportResponseDto;
import com.estacionamiento.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Reportes", description = "Generación y gestion de reportes")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los reportes")
    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> findAll() {
        logger.info("Buscando todos los reportes");
        return ResponseEntity.ok(reportService.findAll());
    }

    @Operation(summary = "Obtener reporte por ID")
    @ApiResponse(responseCode = "200", description = "Reporte encontrado")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ReportResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando reporte por id");
        ReportResponseDto report = reportService.findById(id);
        if (report == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Obtener reportes por tipo")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ReportResponseDto>> findByType(@PathVariable String type) {
        logger.info("Buscando reportes por tipo");
        return ResponseEntity.ok(reportService.findByType(type));
    }

    @Operation(summary = "Obtener reportes por usuario")
    @GetMapping("/user/{generatedBy}")
    public ResponseEntity<List<ReportResponseDto>> findByGeneratedBy(@PathVariable Long generatedBy) {
        logger.info("Buscando reportes por usuario");
        return ResponseEntity.ok(reportService.findByGeneratedBy(generatedBy));
    }

    @Operation(summary = "Generar nuevo reporte")
    @ApiResponse(responseCode = "201", description = "Reporte generado exitosamente")
    @PostMapping
    public ResponseEntity<ReportResponseDto> create(@Valid @RequestBody ReportRequestDto dto) {
        logger.info("Generando reporte");
        return ResponseEntity.status(HttpStatus.CREATED).body(reportService.create(dto));
    }

    @Operation(summary = "Actualizar reporte")
    @ApiResponse(responseCode = "200", description = "Reporte actualizado")
    @PutMapping
    public ResponseEntity<ReportResponseDto> update(@Valid @RequestBody ReportRequestDto dto) {
        logger.info("Actualizando reporte");
        return ResponseEntity.ok(reportService.update(dto));
    }

    @Operation(summary = "Eliminar reporte")
    @ApiResponse(responseCode = "204", description = "Reporte eliminado")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando reporte");
        return reportService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
