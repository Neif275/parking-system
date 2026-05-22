package com.estacionamiento.notification.controller;

import com.estacionamiento.notification.dto.NotificationRequestDto;
import com.estacionamiento.notification.dto.NotificationResponseDto;
import com.estacionamiento.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> findAll() {
        logger.info("Buscando todas las notificaciones");
        return ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> findById(@PathVariable Long id) {
        logger.info("Buscando notificación por id");
        NotificationResponseDto notification = notificationService.findById(id);
        if (notification == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDto>> findByUserId(@PathVariable Long userId) {
        logger.info("Buscando notificaciones por usuario");
        return ResponseEntity.ok(notificationService.findByUserId(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> findByStatus(@PathVariable String status) {
        logger.info("Buscando notificaciones por estado");
        return ResponseEntity.ok(notificationService.findByStatus(status));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationResponseDto>> findByType(@PathVariable String type) {
        logger.info("Buscando notificaciones por tipo");
        return ResponseEntity.ok(notificationService.findByType(type));
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<NotificationResponseDto>> findByUserIdAndStatus(
            @PathVariable Long userId, @PathVariable String status) {
        logger.info("Buscando notificaciones por usuario y estado");
        return ResponseEntity.ok(notificationService.findByUserIdAndStatus(userId, status));
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDto> create(@Valid @RequestBody NotificationRequestDto dto) {
        logger.info("Creando notificación");
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.create(dto));
    }

    @PutMapping
    public ResponseEntity<NotificationResponseDto> update(@Valid @RequestBody NotificationRequestDto dto) {
        logger.info("Actualizando notificación");
        return ResponseEntity.ok(notificationService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logger.info("Eliminando notificación");
        return notificationService.deleteById(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}