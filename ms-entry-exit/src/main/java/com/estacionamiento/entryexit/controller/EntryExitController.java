package com.estacionamiento.entryexit.controller;

import com.estacionamiento.entryexit.dto.EntryExitRequestDto;
import com.estacionamiento.entryexit.dto.EntryExitResponseDto;
import com.estacionamiento.entryexit.service.EntryExitService;
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
@RequestMapping("/api/v1/entry-exit")
public class EntryExitController {

    private final EntryExitService entryExitService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<EntryExitResponseDto>> findAll() {
        logger.info("Buscando todos los registros de entrada/salida");
        return ResponseEntity.ok(entryExitService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryExitResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando registro por id");
        EntryExitResponseDto entryExit = entryExitService.findById(id);
        if (entryExit == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(entryExit);
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<List<EntryExitResponseDto>> findByPlate(@PathVariable String plate) {
        logger.info("Buscando registros por placa");
        return ResponseEntity.ok(entryExitService.findByPlate(plate));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EntryExitResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando registros por estado");
        return ResponseEntity.ok(entryExitService.findByStatus(status));
    }

    @GetMapping("/plate/{plate}/status/{status}")
    public ResponseEntity<EntryExitResponseDto> findByPlateAndStatus(
            @PathVariable String plate, @PathVariable String status) {
        logger.info("Buscando registro activo por placa");
        EntryExitResponseDto entryExit = entryExitService.findByPlateAndStatus(plate, status);
        if (entryExit == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(entryExit);
    }

    @GetMapping("/parking-space/{parkingSpaceId}")
    public ResponseEntity<List<EntryExitResponseDto>> findByParkingSpaceId(@PathVariable Long parkingSpaceId) {
        logger.info("Buscando registros por espacio de estacionamiento");
        return ResponseEntity.ok(entryExitService.findByParkingSpaceId(parkingSpaceId));
    }

    @PostMapping
    public ResponseEntity<EntryExitResponseDto> create(@Valid @RequestBody EntryExitRequestDto dto) {
        logger.info("Registrando entrada");
        return ResponseEntity.status(HttpStatus.CREATED).body(entryExitService.create(dto));
    }

    @PutMapping
    public ResponseEntity<EntryExitResponseDto> update(@Valid @RequestBody EntryExitRequestDto dto) {
        logger.info("Actualizando registro de entrada/salida");
        return ResponseEntity.ok(entryExitService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando registro");
        return entryExitService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
