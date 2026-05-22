package com.estacionamiento.report.client;

import com.estacionamiento.report.client.dto.EntryExitResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "entry-exit")
public interface EntryExitClient {

    @GetMapping("/api/v1/entry-exit/{id}")
    EntryExitResponseDto getEntryExitById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/entry-exit")
    List<EntryExitResponseDto> getAllEntryExits();

    @GetMapping("/api/v1/entry-exit/status/{status}")
    List<EntryExitResponseDto> getEntryExitsByStatus(@PathVariable("status") String status);
}
