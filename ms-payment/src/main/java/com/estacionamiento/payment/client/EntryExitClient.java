package com.estacionamiento.payment.client;

import com.estacionamiento.payment.client.dto.EntryExitResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entry-exit")
public interface EntryExitClient {

    @GetMapping("/api/v1/entry-exit/{id}")
    EntryExitResponseDto getEntryExitById(@PathVariable("id") Long id);
}
