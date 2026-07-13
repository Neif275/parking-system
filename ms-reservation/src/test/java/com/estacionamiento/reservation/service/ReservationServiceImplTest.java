package com.estacionamiento.reservation.service;

import com.estacionamiento.reservation.client.ParkingSpaceClient;
import com.estacionamiento.reservation.client.dto.ParkingSpaceResponseDto;
import com.estacionamiento.reservation.dto.ReservationRequestDto;
import com.estacionamiento.reservation.dto.ReservationResponseDto;
import com.estacionamiento.reservation.model.ReservationModel;
import com.estacionamiento.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ParkingSpaceClient parkingSpaceClient;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private ReservationModel entity(long id) {
        return new ReservationModel(id, "ABCD12", 5L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "ACTIVE");
    }

    private ReservationRequestDto requestDto(Long id) {
        return new ReservationRequestDto(id, "ABCD12", 5L, 1L, LocalDateTime.now(), LocalDateTime.now().plusHours(2), "ACTIVE");
    }

    private ParkingSpaceResponseDto parkingSpace(boolean available) {
        return new ParkingSpaceResponseDto(1L, "A-1", available);
    }

    @Test
    void findById_existingReservation_returnsMappedDto() {
        given(reservationRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        ReservationResponseDto result = reservationService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getPlate()).isEqualTo("ABCD12");
    }

    @Test
    void findById_nonExistingReservation_returnsNull() {
        given(reservationRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(reservationService.findById(99L)).isNull();
    }

    @Test
    void findAll_returnsAllMappedReservations() {
        given(reservationRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        assertThat(reservationService.findAll()).hasSize(2);
    }

    @Test
    void findByPlate_returnsMatchingReservations() {
        given(reservationRepository.findByPlate("ABCD12")).willReturn(List.of(entity(1L)));

        assertThat(reservationService.findByPlate("ABCD12")).hasSize(1);
    }

    @Test
    void findByOwnerUserId_returnsMatchingReservations() {
        given(reservationRepository.findByOwnerUserId(5L)).willReturn(List.of(entity(1L)));

        assertThat(reservationService.findByOwnerUserId(5L)).hasSize(1);
    }

    @Test
    void findByStatus_returnsMatchingReservations() {
        given(reservationRepository.findByStatus("ACTIVE")).willReturn(List.of(entity(1L)));

        assertThat(reservationService.findByStatus("ACTIVE")).hasSize(1);
    }

    @Test
    void findByParkingSpaceId_returnsMatchingReservations() {
        given(reservationRepository.findByParkingSpaceId(1L)).willReturn(List.of(entity(1L)));

        assertThat(reservationService.findByParkingSpaceId(1L)).hasSize(1);
    }

    @Test
    void findByPlateAndStatus_returnsMatchingReservations() {
        given(reservationRepository.findByPlateAndStatus("ABCD12", "ACTIVE")).willReturn(List.of(entity(1L)));

        assertThat(reservationService.findByPlateAndStatus("ABCD12", "ACTIVE")).hasSize(1);
    }

    @Test
    void create_availableParkingSpace_savesAndReturnsDto() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(parkingSpace(true));
        given(reservationRepository.save(any(ReservationModel.class))).willReturn(entity(1L));

        ReservationResponseDto result = reservationService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(reservationRepository, times(1)).save(any(ReservationModel.class));
    }

    @Test
    void create_parkingSpaceNotFound_throwsIllegalArgumentException() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(null);

        assertThatThrownBy(() -> reservationService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no está disponible");
        verify(reservationRepository, never()).save(any(ReservationModel.class));
    }

    @Test
    void create_parkingSpaceUnavailable_throwsIllegalArgumentException() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(parkingSpace(false));

        assertThatThrownBy(() -> reservationService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no está disponible");
        verify(reservationRepository, never()).save(any(ReservationModel.class));
    }

    @Test
    void update_existingReservation_savesAndReturnsUpdatedDto() {
        ReservationModel updated = entity(1L);
        updated.setStatus("CANCELLED");
        given(reservationRepository.save(any(ReservationModel.class))).willReturn(updated);

        ReservationResponseDto result = reservationService.update(requestDto(1L));

        assertThat(result.getStatus()).isEqualTo("CANCELLED");
    }

    @Test
    void deleteById_existingReservation_deletesAndReturnsTrue() {
        given(reservationRepository.existsById(1L)).willReturn(true);

        assertThat(reservationService.deleteById(1L)).isTrue();
        verify(reservationRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingReservation_returnsFalse() {
        given(reservationRepository.existsById(99L)).willReturn(false);

        assertThat(reservationService.deleteById(99L)).isFalse();
        verify(reservationRepository, never()).deleteById(99L);
    }
}
