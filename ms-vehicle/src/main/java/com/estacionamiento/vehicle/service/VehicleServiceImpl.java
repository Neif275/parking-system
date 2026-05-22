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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

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
        return vehicleRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public VehicleResponseDto findByPlate(String plate) {
        return vehicleRepository.findByPlate(plate)
                .map(this::toDto)
                .orElse(null);
    }

    @Override
    public List<VehicleResponseDto> findAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public VehicleResponseDto create(VehicleRequestDto dto) {
        return toDto(vehicleRepository.save(toEntity(dto)));
    }

    @Override
    public VehicleResponseDto update(VehicleRequestDto dto) {
        return toDto(vehicleRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
