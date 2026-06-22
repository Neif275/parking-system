package com.estacionamiento.reservation.controller;

import com.estacionamiento.reservation.dto.ReservationRequestDto;
import com.estacionamiento.reservation.dto.ReservationResponseDto;
import com.estacionamiento.reservation.service.ReservationService;
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

@Tag(name = "Reservaciones", description = "Gestion de reservas de espacios")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todas las reservas")
    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        logger.info("Buscando todas las reservas");
        return ResponseEntity.ok(reservationService.findAll());
    }

    @Operation(summary = "Obtener reserva por ID")
    @ApiResponse(responseCode = "200", description = "Reserva encontrada")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando reserva por id");
        ReservationResponseDto reservation = reservationService.findById(id);
        if (reservation == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reservation);
    }

    @Operation(summary = "Obtener reservas por patente de vehículo")
    @GetMapping("/plate/{plate}")
    public ResponseEntity<List<ReservationResponseDto>> findByPlate(@PathVariable String plate) {
        logger.info("Buscando reservas por patente");
        return ResponseEntity.ok(reservationService.findByPlate(plate));
    }

    @Operation(summary = "Obtener reservas por usuario")
    @GetMapping("/user/{ownerUserId}")
    public ResponseEntity<List<ReservationResponseDto>> findByOwnerUserId(@PathVariable Long ownerUserId) {
        logger.info("Buscando reservas por usuario");
        return ResponseEntity.ok(reservationService.findByOwnerUserId(ownerUserId));
    }

    @Operation(summary = "Obtener reservas por estado")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando reservas por estado");
        return ResponseEntity.ok(reservationService.findByStatus(status));
    }

    @Operation(summary = "Obtener reservas por espacio de estacionamiento")
    @GetMapping("/parking-space/{parkingSpaceId}")
    public ResponseEntity<List<ReservationResponseDto>> findByParkingSpaceId(@PathVariable Long parkingSpaceId) {
        logger.info("Buscando reservas por espacio");
        return ResponseEntity.ok(reservationService.findByParkingSpaceId(parkingSpaceId));
    }

    @Operation(summary = "Obtener reservas por patente y estado")
    @GetMapping("/plate/{plate}/status/{status}")
    public ResponseEntity<List<ReservationResponseDto>> findByPlateAndStatus(
            @PathVariable String plate, @PathVariable String status) {
        logger.info("Buscando reservas por patente y estado");
        return ResponseEntity.ok(reservationService.findByPlateAndStatus(plate, status));
    }

    @Operation(summary = "Crear nueva reserva")
    @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente")
    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody ReservationRequestDto dto) {
        logger.info("Creando reserva");
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(dto));
    }

    @Operation(summary = "Actualizar reserva")
    @ApiResponse(responseCode = "200", description = "Reserva actualizada")
    @PutMapping
    public ResponseEntity<ReservationResponseDto> update(@Valid @RequestBody ReservationRequestDto dto) {
        logger.info("Actualizando reserva");
        return ResponseEntity.ok(reservationService.update(dto));
    }

    @Operation(summary = "Eliminar reserva")
    @ApiResponse(responseCode = "204", description = "Reserva eliminada")
    @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando reserva");
        return reservationService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
