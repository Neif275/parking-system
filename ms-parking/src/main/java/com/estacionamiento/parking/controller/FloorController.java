package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.FloorRequestDto;
import com.estacionamiento.parking.dto.FloorResponseDto;
import com.estacionamiento.parking.service.FloorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/floors")
@RequiredArgsConstructor
public class FloorController {

    private final FloorService floorService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<FloorResponseDto>> findAll() {
        logger.info("Buscando todos los pisos");
        return ResponseEntity.ok(floorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorResponseDto> findById(@PathVariable Integer id) {
        logger.info("Buscando piso por id");
        FloorResponseDto floor = floorService.findById(id);
        if (floor == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(floor);
    }

    @PostMapping
    public ResponseEntity<FloorResponseDto> create(@Valid @RequestBody FloorRequestDto dto) {
        logger.info("Creando piso");
        return ResponseEntity.status(HttpStatus.CREATED).body(floorService.create(dto));
    }

    @PutMapping
    public ResponseEntity<FloorResponseDto> update(@Valid @RequestBody FloorRequestDto dto) {
        logger.info("Actualizando piso");
        return ResponseEntity.ok(floorService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Eliminando piso");
        return floorService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
