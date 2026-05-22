package com.estacionamiento.notification.service;

import com.estacionamiento.notification.dto.NotificationRequestDto;
import com.estacionamiento.notification.dto.NotificationResponseDto;
import com.estacionamiento.notification.model.NotificationModel;
import com.estacionamiento.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

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
        return notificationRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<NotificationResponseDto> findAll() {
        return notificationRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByUserId(Long userId) {
        return notificationRepository.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByStatus(String status) {
        return notificationRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByType(String type) {
        return notificationRepository.findByType(type).stream().map(this::toDto).toList();
    }

    @Override
    public List<NotificationResponseDto> findByUserIdAndStatus(Long userId, String status) {
        return notificationRepository.findByUserIdAndStatus(userId, status).stream().map(this::toDto).toList();
    }

    @Override
    public NotificationResponseDto create(NotificationRequestDto dto) {
        return toDto(notificationRepository.save(toEntity(dto)));
    }

    @Override
    public NotificationResponseDto update(NotificationRequestDto dto) {
        return toDto(notificationRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
