package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.ParkingSlotRequestDto;
import com.estacionamiento.parking.dto.ParkingSlotResponseDto;
import com.estacionamiento.parking.service.ParkingSlotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slots")
@RequiredArgsConstructor
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<ParkingSlotResponseDto>> findAll() {
        logger.info("Buscando todos los estacionamientos");
        return ResponseEntity.ok(parkingSlotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSlotResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando estacionamiento por id");
        ParkingSlotResponseDto slot = parkingSlotService.findById(id);
        if (slot == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(slot);
    }

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSlotResponseDto>> findAvailable() {
        logger.info("Buscando estacionamientos disponibles");
        return ResponseEntity.ok(parkingSlotService.findAvailable());
    }

    @PostMapping
    public ResponseEntity<ParkingSlotResponseDto> create(@Valid @RequestBody ParkingSlotRequestDto dto) {
        logger.info("Creando estacionamiento");
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSlotService.create(dto));
    }

    @PutMapping
    public ResponseEntity<ParkingSlotResponseDto> update(@Valid @RequestBody ParkingSlotRequestDto dto) {
        logger.info("Actualizando estacionamiento");
        return ResponseEntity.ok(parkingSlotService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando estacionamiento");
        return parkingSlotService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
