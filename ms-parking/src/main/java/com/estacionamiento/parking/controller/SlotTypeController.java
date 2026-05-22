package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.SlotTypeRequestDto;
import com.estacionamiento.parking.dto.SlotTypeResponseDto;
import com.estacionamiento.parking.service.SlotTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/slot-types")
@RequiredArgsConstructor
public class SlotTypeController {

    private final SlotTypeService slotTypeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<SlotTypeResponseDto>> findAll() {
        logger.info("Buscando todos espacios de estacionamiento.");
        return ResponseEntity.ok(slotTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlotTypeResponseDto> findById(@PathVariable Integer id) {
        logger.info("Buscando tipo de espacio de estacionamiento por id");
        SlotTypeResponseDto slotType = slotTypeService.findById(id);
        if (slotType == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(slotType);
    }

    @PostMapping
    public ResponseEntity<SlotTypeResponseDto> create(@Valid @RequestBody SlotTypeRequestDto dto) {
        logger.info("Creando tipo de espacio de estacionamiento");
        return ResponseEntity.status(HttpStatus.CREATED).body(slotTypeService.create(dto));
    }

    @PutMapping
    public ResponseEntity<SlotTypeResponseDto> update(@Valid @RequestBody SlotTypeRequestDto dto) {
        logger.info("Actualizando tipo de espacio de estacionamiento");
        return ResponseEntity.ok(slotTypeService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Eliminando tipo de espacio de estacionamiento");
        return slotTypeService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
