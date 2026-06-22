package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.ZoneRequestDto;
import com.estacionamiento.parking.dto.ZoneResponseDto;
import com.estacionamiento.parking.service.ZoneService;
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

@Tag(name = "Zonas", description = "Gestión de zonas del estacionamiento")
@RestController
@RequestMapping("/api/v1/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todas las zonas")
    @GetMapping
    public ResponseEntity<List<ZoneResponseDto>> findAll() {
        logger.info("Buscando todas las zonas");
        return ResponseEntity.ok(zoneService.findAll());
    }

    @Operation(summary = "Obtener zona por ID")
    @ApiResponse(responseCode = "200", description = "Zona encontrada")
    @ApiResponse(responseCode = "404", description = "Zona no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponseDto> findById(@PathVariable Integer id) {
        logger.info("Buscando zona por id");
        ZoneResponseDto zone = zoneService.findById(id);
        if (zone == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(zone);
    }

    @Operation(summary = "Crear nueva zona")
    @ApiResponse(responseCode = "201", description = "Zona creada exitosamente")
    @PostMapping
    public ResponseEntity<ZoneResponseDto> create(@Valid @RequestBody ZoneRequestDto dto) {
        logger.info("Creando zona");
        return ResponseEntity.status(HttpStatus.CREATED).body(zoneService.create(dto));
    }

    @Operation(summary = "Actualizar zona")
    @ApiResponse(responseCode = "200", description = "Zona actualizada")
    @PutMapping
    public ResponseEntity<ZoneResponseDto> update(@Valid @RequestBody ZoneRequestDto dto) {
        logger.info("Actualizando zona");
        return ResponseEntity.ok(zoneService.update(dto));
    }

    @Operation(summary = "Eliminar zona")
    @ApiResponse(responseCode = "204", description = "Zona eliminada")
    @ApiResponse(responseCode = "404", description = "Zona no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Eliminando zona");
        return zoneService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
