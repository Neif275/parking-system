package com.estacionamiento.parking.service;

import com.estacionamiento.parking.dto.ZoneRequestDto;
import com.estacionamiento.parking.dto.ZoneResponseDto;

import java.util.List;

public interface ZoneService {
    List<ZoneResponseDto> findAll();
    ZoneResponseDto findById(Integer id);
    ZoneResponseDto create(ZoneRequestDto dto);
    ZoneResponseDto update(ZoneRequestDto dto);
    boolean deleteById(Integer id);
}
