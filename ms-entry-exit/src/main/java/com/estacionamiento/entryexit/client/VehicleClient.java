package com.estacionamiento.entryexit.client;

import com.estacionamiento.entryexit.client.dto.VehicleResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "vehicle")
public interface VehicleClient {

    @GetMapping("/api/v1/vehicles/plate/{plate}")
    VehicleResponseDto getVehicleByPlate(@PathVariable("plate") String plate);
}
