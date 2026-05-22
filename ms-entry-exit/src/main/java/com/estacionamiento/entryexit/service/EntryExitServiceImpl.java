package com.estacionamiento.entryexit.service;

import com.estacionamiento.entryexit.client.ParkingSpaceClient;
import com.estacionamiento.entryexit.client.TariffClient;
import com.estacionamiento.entryexit.client.VehicleClient;
import com.estacionamiento.entryexit.client.dto.ParkingSpaceResponseDto;
import com.estacionamiento.entryexit.client.dto.TariffResponseDto;
import com.estacionamiento.entryexit.client.dto.VehicleResponseDto;
import com.estacionamiento.entryexit.dto.EntryExitRequestDto;
import com.estacionamiento.entryexit.dto.EntryExitResponseDto;
import com.estacionamiento.entryexit.model.EntryExitModel;
import com.estacionamiento.entryexit.repository.EntryExitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryExitServiceImpl implements EntryExitService {

    private final EntryExitRepository entryExitRepository;
    private final ParkingSpaceClient parkingSpaceClient;
    private final TariffClient tariffClient;
    private final VehicleClient vehicleClient;

    private EntryExitResponseDto toDto(EntryExitModel entity) {
        return new EntryExitResponseDto(
                entity.getId(),
                entity.getPlate(),
                entity.getEntryTime(),
                entity.getExitTime(),
                entity.getParkingSpaceId(),
                entity.getTariffId(),
                entity.getStatus()
        );
    }

    private EntryExitModel toEntity(EntryExitRequestDto dto) {
        return new EntryExitModel(
                dto.getId(),
                dto.getPlate(),
                dto.getEntryTime(),
                dto.getExitTime(),
                dto.getParkingSpaceId(),
                dto.getTariffId(),
                dto.getStatus()
        );
    }

    @Override
    public EntryExitResponseDto findById(Long id) {
        return entryExitRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<EntryExitResponseDto> findAll() {
        return entryExitRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<EntryExitResponseDto> findByPlate(String plate) {
        return entryExitRepository.findByPlate(plate).stream().map(this::toDto).toList();
    }

    @Override
    public List<EntryExitResponseDto> findByStatus(String status) {
        return entryExitRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public EntryExitResponseDto findByPlateAndStatus(String plate, String status) {
        return entryExitRepository.findByPlateAndStatus(plate, status)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<EntryExitResponseDto> findByParkingSpaceId(Long parkingSpaceId) {
        return entryExitRepository.findByParkingSpaceId(parkingSpaceId).stream().map(this::toDto).toList();
    }

    @Override
    public EntryExitResponseDto create(EntryExitRequestDto dto) {
        ParkingSpaceResponseDto parkingSpace = parkingSpaceClient.getParkingSpaceById(dto.getParkingSpaceId());
        if (parkingSpace == null || Boolean.FALSE.equals(parkingSpace.getIsAvailable())) {
            throw new IllegalArgumentException("El espacio de estacionamiento no está disponible");
        }
        VehicleResponseDto vehicle = vehicleClient.getVehicleByPlate(dto.getPlate());
        if (vehicle == null) {
            throw new IllegalArgumentException("No existe un vehículo registrado con la placa: " + dto.getPlate());
        }
        TariffResponseDto tariff = tariffClient.getTariffById(dto.getTariffId());
        if (tariff == null) {
            throw new IllegalArgumentException("La tarifa con id " + dto.getTariffId() + " no existe");
        }
        return toDto(entryExitRepository.save(toEntity(dto)));
    }

    @Override
    public EntryExitResponseDto update(EntryExitRequestDto dto) {
        return toDto(entryExitRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (entryExitRepository.existsById(id)) {
            entryExitRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
