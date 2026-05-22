package com.estacionamiento.report.client;

import com.estacionamiento.report.client.dto.VehicleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "vehicle")
public interface VehicleClient {

    @GetMapping("/api/v1/vehicles/{id}")
    VehicleResponseDto getVehicleById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/vehicles")
    List<VehicleResponseDto> getAllVehicles();

    @GetMapping("/api/v1/vehicles/plate/{plate}")
    VehicleResponseDto getVehicleByPlate(@PathVariable("plate") String plate);
}
