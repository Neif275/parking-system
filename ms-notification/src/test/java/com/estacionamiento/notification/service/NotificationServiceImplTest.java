package com.estacionamiento.notification.service;

import com.estacionamiento.notification.dto.NotificationRequestDto;
import com.estacionamiento.notification.dto.NotificationResponseDto;
import com.estacionamiento.notification.model.NotificationModel;
import com.estacionamiento.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private NotificationModel entity(long id) {
        return new NotificationModel(id, 5L, "Su reserva expira pronto", "RESERVATION", "PENDING", LocalDateTime.now(), null);
    }

    private NotificationRequestDto requestDto(Long id) {
        return new NotificationRequestDto(id, 5L, "Su reserva expira pronto", "RESERVATION", "PENDING", LocalDateTime.now(), null);
    }

    @Test
    void findById_existingNotification_returnsMappedDto() {
        given(notificationRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        NotificationResponseDto result = notificationService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("RESERVATION");
    }

    @Test
    void findById_nonExistingNotification_returnsNull() {
        given(notificationRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(notificationService.findById(99L)).isNull();
    }

    @Test
    void findAll_returnsAllMappedNotifications() {
        given(notificationRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        assertThat(notificationService.findAll()).hasSize(2);
    }

    @Test
    void findByUserId_returnsMatchingNotifications() {
        given(notificationRepository.findByUserId(5L)).willReturn(List.of(entity(1L)));

        assertThat(notificationService.findByUserId(5L)).hasSize(1);
    }

    @Test
    void findByStatus_returnsMatchingNotifications() {
        given(notificationRepository.findByStatus("PENDING")).willReturn(List.of(entity(1L)));

        assertThat(notificationService.findByStatus("PENDING")).hasSize(1);
    }

    @Test
    void findByType_returnsMatchingNotifications() {
        given(notificationRepository.findByType("RESERVATION")).willReturn(List.of(entity(1L)));

        assertThat(notificationService.findByType("RESERVATION")).hasSize(1);
    }

    @Test
    void findByUserIdAndStatus_returnsMatchingNotifications() {
        given(notificationRepository.findByUserIdAndStatus(5L, "PENDING")).willReturn(List.of(entity(1L)));

        assertThat(notificationService.findByUserIdAndStatus(5L, "PENDING")).hasSize(1);
    }

    @Test
    void create_validRequest_savesAndReturnsDto() {
        given(notificationRepository.save(any(NotificationModel.class))).willReturn(entity(1L));

        NotificationResponseDto result = notificationService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(notificationRepository, times(1)).save(any(NotificationModel.class));
    }

    @Test
    void update_existingNotification_savesAndReturnsUpdatedDto() {
        NotificationModel updated = entity(1L);
        updated.setStatus("SENT");
        given(notificationRepository.save(any(NotificationModel.class))).willReturn(updated);

        NotificationResponseDto result = notificationService.update(requestDto(1L));

        assertThat(result.getStatus()).isEqualTo("SENT");
    }

    @Test
    void deleteById_existingNotification_deletesAndReturnsTrue() {
        given(notificationRepository.existsById(1L)).willReturn(true);

        assertThat(notificationService.deleteById(1L)).isTrue();
        verify(notificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingNotification_returnsFalse() {
        given(notificationRepository.existsById(99L)).willReturn(false);

        assertThat(notificationService.deleteById(99L)).isFalse();
        verify(notificationRepository, never()).deleteById(99L);
    }
}
