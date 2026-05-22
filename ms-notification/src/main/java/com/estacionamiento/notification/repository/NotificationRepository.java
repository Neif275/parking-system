package com.estacionamiento.notification.repository;

import com.estacionamiento.notification.model.NotificationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
    List<NotificationModel> findByUserId(Long userId);
    List<NotificationModel> findByStatus(String status);
    List<NotificationModel> findByType(String type);
    List<NotificationModel> findByUserIdAndStatus(Long userId, String status);
}
