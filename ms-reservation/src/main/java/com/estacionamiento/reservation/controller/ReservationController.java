package com.estacionamiento.reservation.controller;

import com.estacionamiento.reservation.dto.ReservationRequestDto;
import com.estacionamiento.reservation.dto.ReservationResponseDto;
import com.estacionamiento.reservation.service.ReservationService;
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
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> findAll() {
        logger.info("Buscando todas las reservas");
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando reserva por id");
        ReservationResponseDto reservation = reservationService.findById(id);
        if (reservation == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/plate/{plate}")
    public ResponseEntity<List<ReservationResponseDto>> findByPlate(@PathVariable String plate) {
        logger.info("Buscando reservas por placa");
        return ResponseEntity.ok(reservationService.findByPlate(plate));
    }

    @GetMapping("/user/{ownerUserId}")
    public ResponseEntity<List<ReservationResponseDto>> findByOwnerUserId(@PathVariable Long ownerUserId) {
        logger.info("Buscando reservas por usuario");
        return ResponseEntity.ok(reservationService.findByOwnerUserId(ownerUserId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReservationResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando reservas por estado");
        return ResponseEntity.ok(reservationService.findByStatus(status));
    }

    @GetMapping("/parking-space/{parkingSpaceId}")
    public ResponseEntity<List<ReservationResponseDto>> findByParkingSpaceId(@PathVariable Long parkingSpaceId) {
        logger.info("Buscando reservas por espacio");
        return ResponseEntity.ok(reservationService.findByParkingSpaceId(parkingSpaceId));
    }

    @GetMapping("/plate/{plate}/status/{status}")
    public ResponseEntity<List<ReservationResponseDto>> findByPlateAndStatus(
            @PathVariable String plate, @PathVariable String status) {
        logger.info("Buscando reservas por placa y estado");
        return ResponseEntity.ok(reservationService.findByPlateAndStatus(plate, status));
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody ReservationRequestDto dto) {
        logger.info("Creando reserva");
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(dto));
    }

    @PutMapping
    public ResponseEntity<ReservationResponseDto> update(@Valid @RequestBody ReservationRequestDto dto) {
        logger.info("Actualizando reserva");
        return ResponseEntity.ok(reservationService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando reserva");
        return reservationService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
