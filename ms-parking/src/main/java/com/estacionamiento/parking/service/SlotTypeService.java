package com.estacionamiento.parking.service;

import com.estacionamiento.parking.dto.SlotTypeRequestDto;
import com.estacionamiento.parking.dto.SlotTypeResponseDto;

import java.util.List;

public interface SlotTypeService {
    List<SlotTypeResponseDto> findAll();
    SlotTypeResponseDto findById(Integer id);
    SlotTypeResponseDto create(SlotTypeRequestDto dto);
    SlotTypeResponseDto update(SlotTypeRequestDto dto);
    boolean deleteById(Integer id);
}
