package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.FloorResponseDto;
import com.estacionamiento.parking.dto.ParkingSlotRequestDto;
import com.estacionamiento.parking.dto.ParkingSlotResponseDto;
import com.estacionamiento.parking.dto.SlotTypeResponseDto;
import com.estacionamiento.parking.dto.ZoneResponseDto;
import com.estacionamiento.parking.model.Floor;
import com.estacionamiento.parking.model.ParkingSlotModel;
import com.estacionamiento.parking.model.SlotTypeModel;
import com.estacionamiento.parking.model.Zone;
import com.estacionamiento.parking.repository.ParkingSlotRepository;
import com.estacionamiento.parking.repository.SlotTypeRepository;
import com.estacionamiento.parking.repository.ZoneRepository;
import com.estacionamiento.parking.service.ParkingSlotService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSlotServiceImpl implements ParkingSlotService {

    private static final Logger log = LoggerFactory.getLogger(ParkingSlotServiceImpl.class);

    private final ParkingSlotRepository parkingSlotRepository;
    private final ZoneRepository zoneRepository;
    private final SlotTypeRepository slotTypeRepository;

    private FloorResponseDto toDto(Floor floor) {
        return new FloorResponseDto(floor.getId(), floor.getNumber(), floor.getDescription());
    }

    private ZoneResponseDto toDto(Zone zone) {
        return new ZoneResponseDto(zone.getId(), zone.getName(), toDto(zone.getFloor()));
    }

    private SlotTypeResponseDto toDto(SlotTypeModel slotTypeModel) {
        return new SlotTypeResponseDto(slotTypeModel.getId(), slotTypeModel.getName(), slotTypeModel.getDescription());
    }

    private ParkingSlotResponseDto toDto(ParkingSlotModel entity) {
        return new ParkingSlotResponseDto(
                entity.getId(),
                entity.getSlotNumber(),
                toDto(entity.getZone()),
                toDto(entity.getSlotTypeModel()),
                entity.getIsAvailable()
        );
    }

    private ParkingSlotModel toEntity(ParkingSlotRequestDto dto) {
        Zone zone = zoneRepository.findById(dto.getZoneId()).orElseThrow();
        SlotTypeModel slotTypeModel = slotTypeRepository.findById(dto.getSlotTypeId()).orElseThrow();
        return new ParkingSlotModel(
                dto.getId(),
                dto.getSlotNumber(),
                zone,
                slotTypeModel,
                dto.getIsAvailable() != null ? dto.getIsAvailable() : true
        );
    }

    @Override
    public List<ParkingSlotResponseDto> findAll() {
        log.info("Consultando todos los espacios de estacionamiento");
        return parkingSlotRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public ParkingSlotResponseDto findById(Long id) {
        log.info("Buscando espacio de estacionamiento con id: {}", id);
        ParkingSlotResponseDto result = parkingSlotRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Espacio con id {} no encontrado", id);
        return result;
    }

    @Override
    public List<ParkingSlotResponseDto> findAvailable() {
        log.info("Consultando espacios disponibles");
        return parkingSlotRepository.findByIsAvailableTrue().stream().map(this::toDto).toList();
    }

    @Override
    public ParkingSlotResponseDto create(ParkingSlotRequestDto dto) {
        log.info("Creando espacio de estacionamiento numero: {}", dto.getSlotNumber());
        ParkingSlotResponseDto result = toDto(parkingSlotRepository.save(toEntity(dto)));
        log.info("Espacio creado con id: {}", result.getId());
        return result;
    }

    @Override
    public ParkingSlotResponseDto update(ParkingSlotRequestDto dto) {
        log.info("Actualizando espacio de estacionamiento con id: {}", dto.getId());
        return toDto(parkingSlotRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (parkingSlotRepository.existsById(id)) {
            parkingSlotRepository.deleteById(id);
            log.info("Espacio con id {} eliminado", id);
            return true;
        }
        log.warn("No se pudo eliminar: espacio con id {} no existe", id);
        return false;
    }
}
