package com.estacionamiento.vehicle.service;

import com.estacionamiento.vehicle.dto.*;
import com.estacionamiento.vehicle.model.BrandModel;
import com.estacionamiento.vehicle.model.ModelModel;
import com.estacionamiento.vehicle.model.VehicleCategoryModel;
import com.estacionamiento.vehicle.model.VehicleModel;
import com.estacionamiento.vehicle.repository.VehicleCategoryRepository;
import com.estacionamiento.vehicle.repository.VehicleModelRepository;
import com.estacionamiento.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    private final VehicleRepository vehicleRepository;
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleCategoryRepository vehicleCategoryRepository;

    private BrandResponseDto toDto(BrandModel entity) {
        return new BrandResponseDto(
                entity.getId(),
                entity.getName()
        );
    }

    private ModelResponseDto toDto(ModelModel entity) {
        return new ModelResponseDto(
                entity.getId(),
                entity.getName(),
                toDto(entity.getBrand())
        );
    }

    private VehicleCategoryResponseDto toDto(VehicleCategoryModel entity) {
        return new VehicleCategoryResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }

    private VehicleResponseDto toDto(VehicleModel entity) {
        return new VehicleResponseDto(
                entity.getId(),
                entity.getPlate(),
                entity.getColor(),
                entity.getYear(),
                toDto(entity.getModel()),
                toDto(entity.getCategory()),
                entity.getOwnerUserId()
        );
    }

    private VehicleModel toEntity(VehicleRequestDto dto) {
        ModelModel model = vehicleModelRepository
                .findById(dto.getModelId())
                .orElseThrow();
        VehicleCategoryModel category = vehicleCategoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow();
        return new VehicleModel(
                dto.getId(),
                dto.getPlate(),
                dto.getColor(),
                dto.getYear(),
                model,
                category,
                dto.getOwnerUserId()
        );
    }

    @Override
    public VehicleResponseDto findById(Long id) {
        log.info("Buscando vehiculo con id: {}", id);
        VehicleResponseDto result = vehicleRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Vehiculo con id {} no encontrado", id);
        return result;
    }

    @Override
    public VehicleResponseDto findByPlate(String plate) {
        log.info("Buscando vehiculo con placa: {}", plate);
        VehicleResponseDto result = vehicleRepository.findByPlate(plate).map(this::toDto).orElse(null);
        if (result == null) log.warn("Vehiculo con placa {} no encontrado", plate);
        return result;
    }

    @Override
    public List<VehicleResponseDto> findAll() {
        log.info("Consultando todos los vehiculos");
        return vehicleRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VehicleResponseDto create(VehicleRequestDto dto) {
        log.info("Registrando vehiculo con placa: {}", dto.getPlate());
        VehicleResponseDto result = toDto(vehicleRepository.save(toEntity(dto)));
        log.info("Vehiculo creado con id: {}", result.getId());
        return result;
    }

    @Override
    public VehicleResponseDto update(VehicleRequestDto dto) {
        log.info("Actualizando vehiculo con id: {}", dto.getId());
        return toDto(vehicleRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            log.info("Vehiculo con id {} eliminado", id);
            return true;
        }
        log.warn("No se pudo eliminar: vehiculo con id {} no existe", id);
        return false;
    }

}
