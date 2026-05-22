package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.FloorRequestDto;
import com.estacionamiento.parking.dto.FloorResponseDto;
import com.estacionamiento.parking.model.Floor;
import com.estacionamiento.parking.repository.FloorRepository;
import com.estacionamiento.parking.service.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FloorServiceImpl implements FloorService {

    private final FloorRepository floorRepository;

    private FloorResponseDto toDto(Floor entity) {
        return new FloorResponseDto(entity.getId(), entity.getNumber(), entity.getDescription());
    }

    private Floor toEntity(FloorRequestDto dto) {
        return new Floor(dto.getId(), dto.getNumber(), dto.getDescription());
    }

    @Override
    public List<FloorResponseDto> findAll() {
        return floorRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public FloorResponseDto findById(Integer id) {
        return floorRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public FloorResponseDto create(FloorRequestDto dto) {
        return toDto(floorRepository.save(toEntity(dto)));
    }

    @Override
    public FloorResponseDto update(FloorRequestDto dto) {
        return toDto(floorRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Integer id) {
        if (floorRepository.existsById(id)) {
            floorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
