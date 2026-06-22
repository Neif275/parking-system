package com.estacionamiento.vehicle.controller;

import com.estacionamiento.vehicle.dto.VehicleRequestDto;
import com.estacionamiento.vehicle.dto.VehicleResponseDto;
import com.estacionamiento.vehicle.service.VehicleService;
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

@Tag(name = "Vehiculos", description = "Gestión de vehiculos")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los vehiculos")
    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> findAll() {
        logger.info("Buscando todos los vehiculos");
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @Operation(summary = "Obtener vehiculo por ID")
    @ApiResponse(responseCode = "200", description = "Vehiculo encontrado")
    @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando vehiculo por id");
        try {
            VehicleResponseDto vehicle = vehicleService.findById(id);
            if (vehicle == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener vehiculo por patente")
    @ApiResponse(responseCode = "200", description = "Vehiculo encontrado")
    @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    @GetMapping("/plate/{plate}")
    public ResponseEntity<VehicleResponseDto> findByPlate(@PathVariable String plate) {
        logger.info("Buscando vehiculo por patente");
        VehicleResponseDto vehicle = vehicleService.findByPlate(plate);
        if (vehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle);
    }

    @Operation(summary = "Crear nuevo vehículo")
    @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente")
    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@Valid @RequestBody VehicleRequestDto dto) {
        logger.info("Creando vehiculo");
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(dto));
    }

    @Operation(summary = "Actualizar vehiculo")
    @ApiResponse(responseCode = "200", description = "Vehiculo actualizado")
    @PutMapping
    public ResponseEntity<VehicleResponseDto> update(@Valid @RequestBody VehicleRequestDto dto) {
        logger.info("Actualizando vehiculo");
        return ResponseEntity.ok(vehicleService.update(dto));
    }

    @Operation(summary = "Eliminar vehiculo")
    @ApiResponse(responseCode = "204", description = "Vehiculo eliminado")
    @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando vehiculo");
        return vehicleService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
