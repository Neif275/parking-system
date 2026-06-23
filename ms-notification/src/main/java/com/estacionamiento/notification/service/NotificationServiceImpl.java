package com.estacionamiento.notification.service;

import com.estacionamiento.notification.dto.NotificationRequestDto;
import com.estacionamiento.notification.dto.NotificationResponseDto;
import com.estacionamiento.notification.model.NotificationModel;
import com.estacionamiento.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final NotificationRepository notificationRepository;

    private NotificationResponseDto toDto(NotificationModel entity) {
        return new NotificationResponseDto(
                entity.getId(),
                entity.getUserId(),
                entity.getMessage(),
                entity.getType(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getSentAt()
        );
    }

    private NotificationModel toEntity(NotificationRequestDto dto) {
        return new NotificationModel(
                dto.getId(),
                dto.getUserId(),
                dto.getMessage(),
                dto.getType(),
                dto.getStatus(),
                dto.getCreatedAt(),
                dto.getSentAt()
        );
    }

    @Override
    public NotificationResponseDto findById(Long id) {
        log.info("Buscando notificacion con id: {}", id);
        NotificationResponseDto result = notificationRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Notificacion con id {} no encontrada", id);
        return result;
    }

    @Override
    public List<NotificationResponseDto> findAll() {
        log.info("Consultando todas las notificaciones");
        return notificationRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByUserId(Long userId) {
        log.info("Buscando notificaciones por usuario id: {}", userId);
        return notificationRepository.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByStatus(String status) {
        log.info("Buscando notificaciones por estado: {}", status);
        return notificationRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByType(String type) {
        log.info("Buscando notificaciones por tipo: {}", type);
        return notificationRepository.findByType(type).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByUserIdAndStatus(Long userId, String status) {
        return notificationRepository.findByUserIdAndStatus(userId, status).stream().map(this::toDto).toList();
    }

    @Override
    public NotificationResponseDto create(NotificationRequestDto dto) {
        log.info("Creando notificacion tipo: {} para usuario id: {}", dto.getType(), dto.getUserId());
        NotificationResponseDto result = toDto(notificationRepository.save(toEntity(dto)));
        log.info("Notificacion creada con id: {}", result.getId());
        return result;
    }

    @Override
    public NotificationResponseDto update(NotificationRequestDto dto) {
        log.info("Actualizando notificacion con id: {}", dto.getId());
        return toDto(notificationRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            log.info("Notificacion con id {} eliminada", id);
            return true;
        }
        log.warn("No se pudo eliminar: notificacion con id {} no existe", id);
        return false;
    }
}
