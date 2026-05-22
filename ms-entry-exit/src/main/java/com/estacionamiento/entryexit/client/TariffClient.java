package com.estacionamiento.entryexit.client;

import com.estacionamiento.entryexit.client.dto.TariffResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tariff")
public interface TariffClient {

    @GetMapping("/api/v1/tariffs/{id}")
    TariffResponseDto getTariffById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/tariffs/vehicle-type/{vehicleType}")
    java.util.List<TariffResponseDto> getTariffByVehicleType(@PathVariable("vehicleType") String vehicleType);
}
