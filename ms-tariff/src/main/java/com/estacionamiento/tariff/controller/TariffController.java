package com.estacionamiento.tariff.controller;

import com.estacionamiento.tariff.dto.TariffRequestDto;
import com.estacionamiento.tariff.dto.TariffResponseDto;
import com.estacionamiento.tariff.service.TariffService;
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

@Tag(name = "Tarifas", description = "Gestion de tarifas por tipo de vehiculo")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tariffs")
public class TariffController {

    private final TariffService tariffService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todas las tarifas")
    @GetMapping
    public ResponseEntity<List<TariffResponseDto>> findAll() {
        logger.info("Buscando todas las tarifas registradas");
        return ResponseEntity.ok(tariffService.findAll());
    }

    @Operation(summary = "Obtener tarifa por ID")
    @ApiResponse(responseCode = "200", description = "Tarifa encontrada")
    @ApiResponse(responseCode = "404", description = "Tarifa no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<TariffResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando tarifa por id");
        TariffResponseDto tariff = tariffService.findById(id);
        if (tariff == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tariff);
    }

    @Operation(summary = "Obtener tarifas por tipo de vehiculo")
    @GetMapping("/vehicle-type/{vehicleType}")
    public ResponseEntity<List<TariffResponseDto>> findByVehicleType(@PathVariable String vehicleType) {
        logger.info("Buscando tarifa por tipo de vehiculo");
        return ResponseEntity.ok(tariffService.findByVehicleType(vehicleType));
    }

    @Operation(summary = "Crear nueva tarifa")
    @ApiResponse(responseCode = "201", description = "Tarifa creada exitosamente")
    @PostMapping
    public ResponseEntity<TariffResponseDto> create(@Valid @RequestBody TariffRequestDto dto) {
        logger.info("Creando registro de tarifa");
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffService.create(dto));
    }

    @Operation(summary = "Actualizar tarifa")
    @ApiResponse(responseCode = "200", description = "Tarifa actualizada")
    @PutMapping
    public ResponseEntity<TariffResponseDto> update(@Valid @RequestBody TariffRequestDto dto) {
        logger.info("Actualizando registro de tarifa");
        return ResponseEntity.ok(tariffService.update(dto));
    }

    @Operation(summary = "Eliminar tarifa")
    @ApiResponse(responseCode = "204", description = "Tarifa eliminada")
    @ApiResponse(responseCode = "404", description = "Tarifa no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando tarifa");
        return tariffService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
