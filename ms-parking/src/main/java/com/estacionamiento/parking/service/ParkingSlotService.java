package com.estacionamiento.parking.service;

import com.estacionamiento.parking.dto.ParkingSlotRequestDto;
import com.estacionamiento.parking.dto.ParkingSlotResponseDto;

import java.util.List;

public interface ParkingSlotService {
    List<ParkingSlotResponseDto> findAll();
    ParkingSlotResponseDto findById(Long id);
    List<ParkingSlotResponseDto> findAvailable();
    ParkingSlotResponseDto create(ParkingSlotRequestDto dto);
    ParkingSlotResponseDto update(ParkingSlotRequestDto dto);
    boolean deleteById(Long id);
}
