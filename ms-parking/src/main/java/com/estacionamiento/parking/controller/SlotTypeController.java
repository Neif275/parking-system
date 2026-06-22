package com.estacionamiento.parking.controller;

import com.estacionamiento.parking.dto.SlotTypeRequestDto;
import com.estacionamiento.parking.dto.SlotTypeResponseDto;
import com.estacionamiento.parking.service.SlotTypeService;
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

@Tag(name = "Tipos de Espacios", description = "Gestión de tipos de espacios de estacionamiento")
@RestController
@RequestMapping("/api/v1/slot-types")
@RequiredArgsConstructor
public class SlotTypeController {

    private final SlotTypeService slotTypeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los tipos de espacios")
    @GetMapping
    public ResponseEntity<List<SlotTypeResponseDto>> findAll() {
        logger.info("Buscando todos espacios de estacionamiento.");
        return ResponseEntity.ok(slotTypeService.findAll());
    }

    @Operation(summary = "Obtener tipo de espacio por ID")
    @ApiResponse(responseCode = "200", description = "Tipo encontrado")
    @ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<SlotTypeResponseDto> findById(@PathVariable Integer id) {
        logger.info("Buscando tipo de espacio de estacionamiento por id");
        SlotTypeResponseDto slotType = slotTypeService.findById(id);
        if (slotType == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(slotType);
    }

    @Operation(summary = "Crear nuevo tipo de espacio")
    @ApiResponse(responseCode = "201", description = "Tipo creado exitosamente")
    @PostMapping
    public ResponseEntity<SlotTypeResponseDto> create(@Valid @RequestBody SlotTypeRequestDto dto) {
        logger.info("Creando tipo de espacio de estacionamiento");
        return ResponseEntity.status(HttpStatus.CREATED).body(slotTypeService.create(dto));
    }

    @Operation(summary = "Actualizar tipo de espacio")
    @ApiResponse(responseCode = "200", description = "Tipo actualizado")
    @PutMapping
    public ResponseEntity<SlotTypeResponseDto> update(@Valid @RequestBody SlotTypeRequestDto dto) {
        logger.info("Actualizando tipo de espacio de estacionamiento");
        return ResponseEntity.ok(slotTypeService.update(dto));
    }

    @Operation(summary = "Eliminar tipo de espacio")
    @ApiResponse(responseCode = "204", description = "Tipo eliminado")
    @ApiResponse(responseCode = "404", description = "Tipo no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        logger.info("Eliminando tipo de espacio de estacionamiento");
        return slotTypeService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
