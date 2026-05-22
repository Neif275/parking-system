package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.ZoneRequestDto;
import com.estacionamiento.parking.dto.ZoneResponseDto;
import com.estacionamiento.parking.service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<ZoneResponseDto>> findAll() {
        logger.info("Buscando todas las zonas");
        return ResponseEntity.ok(zoneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponseDto> findById(@PathVariable Integer id) {
        logger.info("Buscando zona por id");
        ZoneResponseDto zone = zoneService.findById(id);
        if (zone == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(zone);
    }

    @PostMapping
    public ResponseEntity<ZoneResponseDto> create(@Valid @RequestBody ZoneRequestDto dto) {
        logger.info("Creando zona");
        return ResponseEntity.status(HttpStatus.CREATED).body(zoneService.create(dto));
    }

    @PutMapping
    public ResponseEntity<ZoneResponseDto> update(@Valid @RequestBody ZoneRequestDto dto) {
        logger.info("Actualizando zona");
        return ResponseEntity.ok(zoneService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Eliminando zona");
        return zoneService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
