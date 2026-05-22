package com.estacionamiento.notification.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationRequestDto {
    private Long id;
    @NotNull(message = "El id del usuario es obligatorio")
    private Long userId;
    @NotBlank(message = "El mensaje es obligatorio")
    private String message;
    @NotBlank(message = "El tipo de notificacion es obligatorio")
    private String type;
    @NotBlank(message = "El estado es obligatorio")
    private String status;
    @NotNull(message = "La fecha de creacion es obligatoria")
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
