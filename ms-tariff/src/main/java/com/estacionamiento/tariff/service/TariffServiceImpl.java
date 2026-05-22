package com.estacionamiento.tariff.service;

import com.estacionamiento.tariff.dto.TariffRequestDto;
import com.estacionamiento.tariff.dto.TariffResponseDto;
import com.estacionamiento.tariff.model.TariffModel;
import com.estacionamiento.tariff.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    private TariffResponseDto toDto(TariffModel entity) {
        return new TariffResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPricePerMinute(),
                entity.getVehicleType()
        );
    }

    private TariffModel toEntity(TariffRequestDto dto) {
        return new TariffModel(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getPricePerMinute(),
                dto.getVehicleType()
        );
    }

    @Override
    public TariffResponseDto findById(Long id) {
        return tariffRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<TariffResponseDto> findAll() {
        return tariffRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<TariffResponseDto> findByVehicleType(String vehicleType) {
        return tariffRepository.findByVehicleType(vehicleType).stream().map(this::toDto).toList();
    }

    @Override
    public TariffResponseDto create(TariffRequestDto dto) {
        return toDto(tariffRepository.save(toEntity(dto)));
    }

    @Override
    public TariffResponseDto update(TariffRequestDto dto) {
        return toDto(tariffRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (tariffRepository.existsById(id)) {
            tariffRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
