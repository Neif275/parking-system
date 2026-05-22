package com.estacionamiento.vehicle.service;

import com.estacionamiento.vehicle.dto.VehicleRequestDto;
import com.estacionamiento.vehicle.dto.VehicleResponseDto;

import java.util.List;

public interface VehicleService {
    VehicleResponseDto findById(Long id);
    VehicleResponseDto findByPlate(String plate);
    List<VehicleResponseDto> findAll();
    VehicleResponseDto create(VehicleRequestDto dto);
    VehicleResponseDto update(VehicleRequestDto dto);
    boolean deleteById(Long id);


}
