package com.estacionamiento.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Administración", description = "Endpoints de administración")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Operation(summary = "Endpoint de bienvenida para admin")
    @GetMapping
    public ResponseEntity<Map<String, String>> admin() {
        return ResponseEntity.ok().body(Map.of("message", "Bienvendio admin"));
    }
}
