package com.estacionamiento.entryexit.controller;

import com.estacionamiento.entryexit.dto.EntryExitRequestDto;
import com.estacionamiento.entryexit.dto.EntryExitResponseDto;
import com.estacionamiento.entryexit.service.EntryExitService;
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

@Tag(name = "Entrada/Salida", description = "Registro de entradas y salidas del estacionamiento")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/entry-exit")
public class EntryExitController {

    private final EntryExitService entryExitService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todos los registros de entrada/salida")
    @GetMapping
    public ResponseEntity<List<EntryExitResponseDto>> findAll() {
        logger.info("Buscando todos los registros de entrada/salida");
        return ResponseEntity.ok(entryExitService.findAll());
    }

    @Operation(summary = "Obtener registro por ID")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<EntryExitResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando registro por id");
        EntryExitResponseDto entryExit = entryExitService.findById(id);
        if (entryExit == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(entryExit);
    }

    @Operation(summary = "Obtener registros por patente de vehiculo")
    @GetMapping("/plate/{plate}")
    public ResponseEntity<List<EntryExitResponseDto>> findByPlate(@PathVariable String plate) {
        logger.info("Buscando registros por patente");
        return ResponseEntity.ok(entryExitService.findByPlate(plate));
    }

    @Operation(summary = "Obtener registros por estados")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EntryExitResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando registros por estados");
        return ResponseEntity.ok(entryExitService.findByStatus(status));
    }

    @Operation(summary = "Obtener registro activo por patente y estado")
    @ApiResponse(responseCode = "200", description = "Registro encontrado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    @GetMapping("/plate/{plate}/status/{status}")
    public ResponseEntity<EntryExitResponseDto> findByPlateAndStatus(
            @PathVariable String plate, @PathVariable String status) {
        logger.info("Buscando registro activo por patente");
        EntryExitResponseDto entryExit = entryExitService.findByPlateAndStatus(plate, status);
        if (entryExit == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(entryExit);
    }

    @Operation(summary = "Obtener registros por espacio de estacionamiento")
    @GetMapping("/parking-space/{parkingSpaceId}")
    public ResponseEntity<List<EntryExitResponseDto>> findByParkingSpaceId(@PathVariable Long parkingSpaceId) {
        logger.info("Buscando registros por espacio de estacionamiento");
        return ResponseEntity.ok(entryExitService.findByParkingSpaceId(parkingSpaceId));
    }

    @Operation(summary = "Registrar entrada de vehiculo")
    @ApiResponse(responseCode = "201", description = "Entrada registrada exitosamente")
    @PostMapping
    public ResponseEntity<EntryExitResponseDto> create(@Valid @RequestBody EntryExitRequestDto dto) {
        logger.info("Registrando entrada");
        return ResponseEntity.status(HttpStatus.CREATED).body(entryExitService.create(dto));
    }

    @Operation(summary = "Actualizar registro de entrada/salida")
    @ApiResponse(responseCode = "200", description = "Registro actualizado")
    @PutMapping
    public ResponseEntity<EntryExitResponseDto> update(@Valid @RequestBody EntryExitRequestDto dto) {
        logger.info("Actualizando registro de entrada/salida");
        return ResponseEntity.ok(entryExitService.update(dto));
    }

    @Operation(summary = "Eliminar registro de entrada/salida")
    @ApiResponse(responseCode = "204", description = "Registro eliminado")
    @ApiResponse(responseCode = "404", description = "Registro no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando registro");
        return entryExitService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
