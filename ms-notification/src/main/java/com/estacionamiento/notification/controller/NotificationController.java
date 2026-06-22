package com.estacionamiento.notification.controller;

import com.estacionamiento.notification.dto.NotificationRequestDto;
import com.estacionamiento.notification.dto.NotificationResponseDto;
import com.estacionamiento.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Notificaciones", description = "Gestion de notificaciones a usuarios")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Operation(summary = "Obtener todas las notificaciones")
    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> findAll() {
        logger.info("Buscando todas las notificaciones");
        return ResponseEntity.ok(notificationService.findAll());
    }

    @Operation(summary = "Obtener notificación por ID")
    @ApiResponse(responseCode = "200", description = "Notificación encontrada")
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando notificación por id");
        NotificationResponseDto notification = notificationService.findById(id);
        if (notification == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notification);
    }

    @Operation(summary = "Obtener notificaciones por usuario")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDto>> findByUserId(@PathVariable Long userId) {
        logger.info("Buscando notificaciones por usuario");
        return ResponseEntity.ok(notificationService.findByUserId(userId));
    }

    @Operation(summary = "Obtener notificaciones por estado")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando notificaciones por estado");
        return ResponseEntity.ok(notificationService.findByStatus(status));
    }

    @Operation(summary = "Obtener notificaciones por tipo")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationResponseDto>> findByType(@PathVariable String type) {
        logger.info("Buscando notificaciones por tipo");
        return ResponseEntity.ok(notificationService.findByType(type));
    }

    @Operation(summary = "Obtener notificaciones por usuario y estado")
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> findByUserIdAndStatus(
            @PathVariable Long userId, @PathVariable String status) {
        logger.info("Buscando notificaciones por usuario y estado");
        return ResponseEntity.ok(notificationService.findByUserIdAndStatus(userId, status));
    }

    @Operation(summary = "Crear nueva notificacion")
    @ApiResponse(responseCode = "201", description = "Notificación creada exitosamente")
    @PostMapping
    public ResponseEntity<NotificationResponseDto> create(@Valid @RequestBody NotificationRequestDto dto) {
        logger.info("Creando notificacion");
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(dto));
    }

    @Operation(summary = "Actualizar notificacion")
    @ApiResponse(responseCode = "200", description = "Notificacion actualizada")
    @PutMapping
    public ResponseEntity<NotificationResponseDto> update(@Valid @RequestBody NotificationRequestDto dto) {
        logger.info("Actualizando notificacion");
        return ResponseEntity.ok(notificationService.update(dto));
    }

    @Operation(summary = "Eliminar notificacion")
    @ApiResponse(responseCode = "204", description = "Notificacion eliminada")
    @ApiResponse(responseCode = "404", description = "Notificacion no encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando notificacion");
        return notificationService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}