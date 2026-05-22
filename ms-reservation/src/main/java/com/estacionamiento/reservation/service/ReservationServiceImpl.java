package com.estacionamiento.reservation.service;

import com.estacionamiento.reservation.client.ParkingSpaceClient;
import com.estacionamiento.reservation.client.dto.ParkingSpaceResponseDto;
import com.estacionamiento.reservation.dto.ReservationRequestDto;
import com.estacionamiento.reservation.dto.ReservationResponseDto;
import com.estacionamiento.reservation.model.ReservationModel;
import com.estacionamiento.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

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
        return reservationRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<ReservationResponseDto> findAll() {
        return reservationRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByPlate(String plate) {
        return reservationRepository.findByPlate(plate).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByOwnerUserId(Long ownerUserId) {
        return reservationRepository.findByOwnerUserId(ownerUserId).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReservationResponseDto> findByStatus(String status) {
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
        ParkingSpaceResponseDto parkingSpace = parkingSpaceClient.getParkingSpaceById(dto.getParkingSpaceId());
        if (parkingSpace == null || Boolean.FALSE.equals(parkingSpace.getIsAvailable())) {
            throw new IllegalArgumentException("El espacio de estacionamiento no está disponible");
        }
        return toDto(reservationRepository.save(toEntity(dto)));
    }

    @Override
    public ReservationResponseDto update(ReservationRequestDto dto) {
        return toDto(reservationRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
