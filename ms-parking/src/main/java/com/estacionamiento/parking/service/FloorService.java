package com.estacionamiento.parking.service;

import com.estacionamiento.parking.dto.FloorRequestDto;
import com.estacionamiento.parking.dto.FloorResponseDto;

import java.util.List;

public interface FloorService {
    List<FloorResponseDto> findAll();
    FloorResponseDto findById(Integer id);
    FloorResponseDto create(FloorRequestDto dto);
    FloorResponseDto update(FloorRequestDto dto);
    boolean deleteById(Integer id);
}
