package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.FloorResponseDto;
import com.estacionamiento.parking.dto.ZoneRequestDto;
import com.estacionamiento.parking.dto.ZoneResponseDto;
import com.estacionamiento.parking.model.Floor;
import com.estacionamiento.parking.model.Zone;
import com.estacionamiento.parking.repository.FloorRepository;
import com.estacionamiento.parking.repository.ZoneRepository;
import com.estacionamiento.parking.service.ZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final FloorRepository floorRepository;

    private FloorResponseDto toDto(Floor floor) {
        return new FloorResponseDto(floor.getId(), floor.getNumber(), floor.getDescription());
    }

    private ZoneResponseDto toDto(Zone entity) {
        return new ZoneResponseDto(entity.getId(), entity.getName(), toDto(entity.getFloor()));
    }

    private Zone toEntity(ZoneRequestDto dto) {
        Floor floor = floorRepository.findById(dto.getFloorId()).orElseThrow();
        return new Zone(dto.getId(), dto.getName(), floor);
    }

    @Override
    public List<ZoneResponseDto> findAll() {
        return zoneRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ZoneResponseDto findById(Integer id) {
        return zoneRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public ZoneResponseDto create(ZoneRequestDto dto) {
        return toDto(zoneRepository.save(toEntity(dto)));
    }

    @Override
    public ZoneResponseDto update(ZoneRequestDto dto) {
        return toDto(zoneRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Integer id) {
        if (zoneRepository.existsById(id)) {
            zoneRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
