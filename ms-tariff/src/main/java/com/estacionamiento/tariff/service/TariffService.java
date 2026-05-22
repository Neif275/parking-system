package com.estacionamiento.tariff.service;

import com.estacionamiento.tariff.dto.TariffRequestDto;
import com.estacionamiento.tariff.dto.TariffResponseDto;
import java.util.List;

public interface TariffService {
    TariffResponseDto findById(Long id);
    List<TariffResponseDto> findAll();
    List<TariffResponseDto> findByVehicleType(String vehicleType);
    TariffResponseDto create(TariffRequestDto dto);
    TariffResponseDto update(TariffRequestDto dto);
    boolean deleteById(Long id);
}
