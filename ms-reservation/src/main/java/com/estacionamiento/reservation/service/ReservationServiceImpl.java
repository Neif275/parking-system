package com.estacionamiento.reservation.service;

import com.estacionamiento.reservation.client.ParkingSpaceClient;
import com.estacionamiento.reservation.client.dto.ParkingSpaceResponseDto;
import com.estacionamiento.reservation.dto.ReservationRequestDto;
import com.estacionamiento.reservation.dto.ReservationResponseDto;
import com.estacionamiento.reservation.model.ReservationModel;
import com.estacionamiento.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final ParkingSpaceClient parkingSpaceClient;

    private ReservationResponseDto toDto(ReservationModel entity) {
        return new ReservationResponseDto(
                entity.getId(),
                entity.getPlate(),
                entity.getOwnerUserId(),
                entity.getParkingSpaceId(),
                entity.getReservationTime(),
                entity.getExpirationTime(),
                entity.getStatus()
        );
    }

    private ReservationModel toEntity(ReservationRequestDto dto) {
        return new ReservationModel(
                dto.getId(),
                dto.getPlate(),
                dto.getOwnerUserId(),
                dto.getParkingSpaceId(),
                dto.getReservationTime(),
                dto.getExpirationTime(),
                dto.getStatus()
        );
    }

    @Override
    public ReservationResponseDto findById(Long id) {
        log.info("Buscando reservacion con id: {}", id);
        ReservationResponseDto result = reservationRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Reservacion con id {} no encontrada", id);
        return result;
    }

    @Override
    public List<ReservationResponseDto> findAll() {
        log.info("Consultando todas las reservaciones");
        return reservationRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByPlate(String plate) {
        log.info("Buscando reservaciones por placa: {}", plate);
        return reservationRepository.findByPlate(plate).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByOwnerUserId(Long ownerUserId) {
        log.info("Buscando reservaciones por usuario id: {}", ownerUserId);
        return reservationRepository.findByOwnerUserId(ownerUserId).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByStatus(String status) {
        log.info("Buscando reservaciones por estado: {}", status);
        return reservationRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByParkingSpaceId(Long parkingSpaceId) {
        return reservationRepository.findByParkingSpaceId(parkingSpaceId).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByPlateAndStatus(String plate, String status) {
        return reservationRepository.findByPlateAndStatus(plate, status).stream().map(this::toDto).toList();
    }

    @Override
    public ReservationResponseDto create(ReservationRequestDto dto) {
        log.info("Creando reservacion para placa: {}, espacio: {}", dto.getPlate(), dto.getParkingSpaceId());
        ParkingSpaceResponseDto parkingSpace = parkingSpaceClient.getParkingSpaceById(dto.getParkingSpaceId());
        if (parkingSpace == null || Boolean.FALSE.equals(parkingSpace.getIsAvailable())) {
            log.error("Espacio {} no disponible para reservacion de placa: {}", dto.getParkingSpaceId(), dto.getPlate());
            throw new IllegalArgumentException("El espacio de estacionamiento no está disponible");
        }
        ReservationResponseDto result = toDto(reservationRepository.save(toEntity(dto)));
        log.info("Reservacion creada con id: {} para placa: {}", result.getId(), result.getPlate());
        return result;
    }

    @Override
    public ReservationResponseDto update(ReservationRequestDto dto) {
        log.info("Actualizando reservacion con id: {}", dto.getId());
        return toDto(reservationRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            log.info("Reservacion con id {} eliminada", id);
            return true;
        }
        log.warn("No se pudo eliminar: reservacion con id {} no existe", id);
        return false;
    }
}
