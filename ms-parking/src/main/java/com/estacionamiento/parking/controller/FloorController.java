package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.FloorRequestDto;
import com.estacionamiento.parking.dto.FloorResponseDto;
import com.estacionamiento.parking.service.FloorService;
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

@Tag(name = "Pisos", description = "Gestión de pisos del estacionamiento")
@RestController
@RequestMapping("/api/v1/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los pisos")
    @GetMapping
    public ResponseEntity<List<FloorResponseDto>> findAll() {
        logger.info("Buscando todos los pisos");
        return ResponseEntity.ok(floorService.findAll());
    }

    @Operation(summary = "Obtener piso por ID")
    @ApiResponse(responseCode = "200", description = "Piso encontrado")
    @ApiResponse(responseCode = "404", description = "Piso no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<FloorResponseDto> findById(@PathVariable Integer id) {
        logger.info("Buscando piso por id");
        FloorResponseDto floor = floorService.findById(id);
        if (floor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(floor);
    }

    @Operation(summary = "Crear nuevo piso")
    @ApiResponse(responseCode = "201", description = "Piso creado exitosamente")
    @PostMapping
    public ResponseEntity<FloorResponseDto> create(@Valid @RequestBody FloorRequestDto dto) {
        logger.info("Creando piso");
        return ResponseEntity.status(HttpStatus.CREATED).body(floorService.create(dto));
    }

    @Operation(summary = "Actualizar piso")
    @ApiResponse(responseCode = "200", description = "Piso actualizado")
    @PutMapping
    public ResponseEntity<FloorResponseDto> update(@Valid @RequestBody FloorRequestDto dto) {
        logger.info("Actualizando piso");
        return ResponseEntity.ok(floorService.update(dto));
    }

    @Operation(summary = "Eliminar piso")
    @ApiResponse(responseCode = "204", description = "Piso eliminado")
    @ApiResponse(responseCode = "404", description = "Piso no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Eliminando piso");
        return floorService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
