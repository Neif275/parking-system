package com.estacionamiento.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationResponseDto {
    private Long id;
    private Long userId;
    private String message;
    private String type;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
