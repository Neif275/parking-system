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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryExitServiceImpl implements EntryExitService {

    private static final Logger log = LoggerFactory.getLogger(EntryExitServiceImpl.class);

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
        log.info("Buscando registro de entrada/salida con id: {}", id);
        EntryExitResponseDto result = entryExitRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Registro con id {} no encontrado", id);
        return result;
    }

    @Override
    public List<EntryExitResponseDto> findAll() {
        log.info("Consultando todos los registros de entrada/salida");
        return entryExitRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<EntryExitResponseDto> findByPlate(String plate) {
        log.info("Buscando registros por placa: {}", plate);
        return entryExitRepository.findByPlate(plate).stream().map(this::toDto).toList();
    }

    @Override
    public List<EntryExitResponseDto> findByStatus(String status) {
        log.info("Buscando registros por estado: {}", status);
        return entryExitRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public EntryExitResponseDto findByPlateAndStatus(String plate, String status) {
        return entryExitRepository.findByPlateAndStatus(plate, status).map(this::toDto).orElse(null);
    }

    @Override
    public List<EntryExitResponseDto> findByParkingSpaceId(Long parkingSpaceId) {
        return entryExitRepository.findByParkingSpaceId(parkingSpaceId).stream().map(this::toDto).toList();
    }

    @Override
    public EntryExitResponseDto create(EntryExitRequestDto dto) {
        log.info("Registrando entrada para placa: {}, espacio: {}", dto.getPlate(), dto.getParkingSpaceId());
        ParkingSpaceResponseDto parkingSpace = parkingSpaceClient.getParkingSpaceById(dto.getParkingSpaceId());
        if (parkingSpace == null || Boolean.FALSE.equals(parkingSpace.getIsAvailable())) {
            log.error("Espacio {} no disponible para placa: {}", dto.getParkingSpaceId(), dto.getPlate());
            throw new IllegalArgumentException("El espacio de estacionamiento no está disponible");
        }
        VehicleResponseDto vehicle = vehicleClient.getVehicleByPlate(dto.getPlate());
        if (vehicle == null) {
            log.error("Vehiculo con placa {} no encontrado", dto.getPlate());
            throw new IllegalArgumentException("No existe un vehículo registrado con la placa: " + dto.getPlate());
        }
        TariffResponseDto tariff = tariffClient.getTariffById(dto.getTariffId());
        if (tariff == null) {
            log.error("Tarifa con id {} no encontrada", dto.getTariffId());
            throw new IllegalArgumentException("La tarifa con id " + dto.getTariffId() + " no existe");
        }
        EntryExitResponseDto result = toDto(entryExitRepository.save(toEntity(dto)));
        log.info("Entrada registrada con id: {} para placa: {}", result.getId(), result.getPlate());
        return result;
    }

    @Override
    public EntryExitResponseDto update(EntryExitRequestDto dto) {
        log.info("Actualizando registro de entrada/salida con id: {}", dto.getId());
        return toDto(entryExitRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (entryExitRepository.existsById(id)) {
            entryExitRepository.deleteById(id);
            log.info("Registro con id {} eliminado", id);
            return true;
        }
        log.warn("No se pudo eliminar: registro con id {} no existe", id);
        return false;
    }
}
