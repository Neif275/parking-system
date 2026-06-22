package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.ParkingSlotRequestDto;
import com.estacionamiento.parking.dto.ParkingSlotResponseDto;
import com.estacionamiento.parking.service.ParkingSlotService;
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

@Tag(name = "Espacios de Estacionamiento", description = "Gestión de espacios de estacionamiento")
@RestController
@RequestMapping("/api/v1/slots")
@RequiredArgsConstructor
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los espacios de estacionamiento")
    @GetMapping
    public ResponseEntity<List<ParkingSlotResponseDto>> findAll() {
        logger.info("Buscando todos los estacionamientos");
        return ResponseEntity.ok(parkingSlotService.findAll());
    }

    @Operation(summary = "Obtener espacio de estacionamiento por ID")
    @ApiResponse(responseCode = "200", description = "Espacio encontrado")
    @ApiResponse(responseCode = "404", description = "Espacio no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlotResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando estacionamiento por id");
        ParkingSlotResponseDto slot = parkingSlotService.findById(id);
        if (slot == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(slot);
    }

    @Operation(summary = "Obtener espacios de estacionamiento disponibles")
    @GetMapping("/available")
    public ResponseEntity<List<ParkingSlotResponseDto>> findAvailable() {
        logger.info("Buscando estacionamientos disponibles");
        return ResponseEntity.ok(parkingSlotService.findAvailable());
    }

    @Operation(summary = "Crear nuevo espacio de estacionamiento")
    @ApiResponse(responseCode = "201", description = "Espacio creado exitosamente")
    @PostMapping
    public ResponseEntity<ParkingSlotResponseDto> create(@Valid @RequestBody ParkingSlotRequestDto dto) {
        logger.info("Creando estacionamiento");
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSlotService.create(dto));
    }

    @Operation(summary = "Actualizar espacio de estacionamiento")
    @ApiResponse(responseCode = "200", description = "Espacio actualizado")
    @PutMapping
    public ResponseEntity<ParkingSlotResponseDto> update(@Valid @RequestBody ParkingSlotRequestDto dto) {
        logger.info("Actualizando estacionamiento");
        return ResponseEntity.ok(parkingSlotService.update(dto));
    }

    @Operation(summary = "Eliminar espacio de estacionamiento")
    @ApiResponse(responseCode = "204", description = "Espacio eliminado")
    @ApiResponse(responseCode = "404", description = "Espacio no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando estacionamiento");
        return parkingSlotService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
