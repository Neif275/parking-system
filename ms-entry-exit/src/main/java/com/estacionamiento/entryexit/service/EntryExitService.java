package com.estacionamiento.entryexit.service;

import com.estacionamiento.entryexit.dto.EntryExitRequestDto;
import com.estacionamiento.entryexit.dto.EntryExitResponseDto;
import java.util.List;

public interface EntryExitService {
    EntryExitResponseDto findById(Long id);
    List<EntryExitResponseDto> findAll();
    List<EntryExitResponseDto> findByPlate(String plate);
    List<EntryExitResponseDto> findByStatus(String status);
    EntryExitResponseDto findByPlateAndStatus(String plate, String status);
    List<EntryExitResponseDto> findByParkingSpaceId(Long parkingSpaceId);
    EntryExitResponseDto create(EntryExitRequestDto dto);
    EntryExitResponseDto update(EntryExitRequestDto dto);
    boolean deleteById(Long id);
}
