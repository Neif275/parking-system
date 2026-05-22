package com.estacionamiento.reservation.service;

import com.estacionamiento.reservation.dto.ReservationRequestDto;
import com.estacionamiento.reservation.dto.ReservationResponseDto;
import java.util.List;

public interface ReservationService {
    ReservationResponseDto findById(Long id);
    List<ReservationResponseDto> findAll();
    List<ReservationResponseDto> findByPlate(String plate);
    List<ReservationResponseDto> findByOwnerUserId(Long ownerUserId);
    List<ReservationResponseDto> findByStatus(String status);
    List<ReservationResponseDto> findByParkingSpaceId(Long parkingSpaceId);
    List<ReservationResponseDto> findByPlateAndStatus(String plate, String status);
    ReservationResponseDto create(ReservationRequestDto dto);
    ReservationResponseDto update(ReservationRequestDto dto);
    boolean deleteById(Long id);
}
