package com.estacionamiento.tariff.controller;

import com.estacionamiento.tariff.dto.TariffRequestDto;
import com.estacionamiento.tariff.dto.TariffResponseDto;
import com.estacionamiento.tariff.service.TariffService;
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
@RequestMapping("/api/v1/tariffs")
public class TariffController {

    private final TariffService tariffService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<TariffResponseDto>> findAll() {
        logger.info("Buscando todas las tarifas registradas");
        return ResponseEntity.ok(tariffService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando tarifa por id");
        TariffResponseDto tariff = tariffService.findById(id);
        if (tariff == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(tariff);
    }

    @GetMapping("/vehicle-type/{vehicleType}")
    public ResponseEntity<List<TariffResponseDto>> findByVehicleType(@PathVariable String vehicleType) {
        logger.info("Buscando tarifa por tipo de vehículo");
        return ResponseEntity.ok(tariffService.findByVehicleType(vehicleType));
    }

    @PostMapping
    public ResponseEntity<TariffResponseDto> create(@Valid @RequestBody TariffRequestDto dto) {
        logger.info("Creando registro de tarifa");
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffService.create(dto));
    }

    @PutMapping
    public ResponseEntity<TariffResponseDto> update(@Valid @RequestBody TariffRequestDto dto) {
        logger.info("Actualizando registro de tarifa");
        return ResponseEntity.ok(tariffService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando tarifa");
        return tariffService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
