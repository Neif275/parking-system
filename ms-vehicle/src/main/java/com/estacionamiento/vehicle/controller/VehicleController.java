package com.estacionamiento.vehicle.controller;

import com.estacionamiento.vehicle.dto.VehicleRequestDto;
import com.estacionamiento.vehicle.dto.VehicleResponseDto;
import com.estacionamiento.vehicle.service.VehicleService;
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
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> findAll() {
        logger.info("Buscando todos los vehiculos");
        return ResponseEntity.ok(vehicleService.findAll());
    }

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

    @GetMapping("/plate/{plate}")
    public ResponseEntity<VehicleResponseDto> findByPlate(@PathVariable String plate) {
        logger.info("Buscando vehiculo por placa patente");
        VehicleResponseDto vehicle = vehicleService.findByPlate(plate);
        if (vehicle == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDto> create(@Valid @RequestBody VehicleRequestDto dto) {
        logger.info("Creando vehiculo");
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(dto));
    }

    @PutMapping
    public ResponseEntity<VehicleResponseDto> update(@Valid @RequestBody VehicleRequestDto dto) {
        logger.info("Actualizando vehiculo");
        return ResponseEntity.ok(vehicleService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando vehiculo");
        return vehicleService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
