package com.estacionamiento.entryexit.client;

import com.estacionamiento.entryexit.client.dto.ParkingSpaceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "parking")
public interface ParkingSpaceClient {

    @GetMapping("/api/v1/slots/{id}")
    ParkingSpaceResponseDto getParkingSpaceById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/slots/available")
    java.util.List<ParkingSpaceResponseDto> getAvailableParkingSpaces();
}
