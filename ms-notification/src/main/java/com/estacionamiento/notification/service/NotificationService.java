package com.estacionamiento.notification.service;

import com.estacionamiento.notification.dto.NotificationRequestDto;
import com.estacionamiento.notification.dto.NotificationResponseDto;
import java.util.List;

public interface NotificationService {
    NotificationResponseDto findById(Long id);
    List<NotificationResponseDto> findAll();
    List<NotificationResponseDto> findByUserId(Long userId);
    List<NotificationResponseDto> findByStatus(String status);
    List<NotificationResponseDto> findByType(String type);
    List<NotificationResponseDto> findByUserIdAndStatus(Long userId, String status);
    NotificationResponseDto create(NotificationRequestDto dto);
    NotificationResponseDto update(NotificationRequestDto dto);
    boolean deleteById(Long id);
}
