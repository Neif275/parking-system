package com.estacionamiento.tariff.service;

import com.estacionamiento.tariff.dto.TariffRequestDto;
import com.estacionamiento.tariff.dto.TariffResponseDto;
import com.estacionamiento.tariff.model.TariffModel;
import com.estacionamiento.tariff.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {

    private static final Logger log = LoggerFactory.getLogger(TariffServiceImpl.class);

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
        log.info("Buscando tarifa con id: {}", id);
        TariffResponseDto result = tariffRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Tarifa con id {} no encontrada", id);
        return result;
    }

    @Override
    public List<TariffResponseDto> findAll() {
        log.info("Consultando todas las tarifas");
        return tariffRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<TariffResponseDto> findByVehicleType(String vehicleType) {
        log.info("Buscando tarifas por tipo de vehiculo: {}", vehicleType);
        return tariffRepository.findByVehicleType(vehicleType).stream().map(this::toDto).toList();
    }

    @Override
    public TariffResponseDto create(TariffRequestDto dto) {
        log.info("Creando tarifa: {} para tipo: {}", dto.getName(), dto.getVehicleType());
        TariffResponseDto result = toDto(tariffRepository.save(toEntity(dto)));
        log.info("Tarifa creada con id: {}", result.getId());
        return result;
    }

    @Override
    public TariffResponseDto update(TariffRequestDto dto) {
        log.info("Actualizando tarifa con id: {}", dto.getId());
        return toDto(tariffRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (tariffRepository.existsById(id)) {
            tariffRepository.deleteById(id);
            log.info("Tarifa con id {} eliminada", id);
            return true;
        }
        log.warn("No se pudo eliminar: tarifa con id {} no existe", id);
        return false;
    }
}
