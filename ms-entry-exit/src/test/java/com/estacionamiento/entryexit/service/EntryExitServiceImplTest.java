package com.estacionamiento.entryexit.service;

import com.estacionamiento.entryexit.client.ParkingSpaceClient;
import com.estacionamiento.entryexit.client.TariffClient;
import com.estacionamiento.entryexit.client.VehicleClient;
import com.estacionamiento.entryexit.client.dto.ParkingSpaceResponseDto;
import com.estacionamiento.entryexit.client.dto.TariffResponseDto;
import com.estacionamiento.entryexit.client.dto.VehicleResponseDto;
import com.estacionamiento.entryexit.dto.EntryExitRequestDto;
import com.estacionamiento.entryexit.dto.EntryExitResponseDto;
import com.estacionamiento.entryexit.model.EntryExitModel;
import com.estacionamiento.entryexit.repository.EntryExitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EntryExitServiceImplTest {

    @Mock
    private EntryExitRepository entryExitRepository;
    @Mock
    private ParkingSpaceClient parkingSpaceClient;
    @Mock
    private TariffClient tariffClient;
    @Mock
    private VehicleClient vehicleClient;

    @InjectMocks
    private EntryExitServiceImpl entryExitService;

    private EntryExitModel entity(long id) {
        return new EntryExitModel(id, "ABCD12", LocalDateTime.now(), null, 1L, 1L, "ACTIVE");
    }

    private EntryExitRequestDto requestDto(Long id) {
        return new EntryExitRequestDto(id, "ABCD12", LocalDateTime.now(), null, 1L, 1L, "ACTIVE");
    }

    private ParkingSpaceResponseDto parkingSpace(boolean available) {
        return new ParkingSpaceResponseDto(1L, "A-1", available);
    }

    private VehicleResponseDto vehicle() {
        return new VehicleResponseDto(1L, "ABCD12", "Rojo", "2020", 5L);
    }

    private TariffResponseDto tariff() {
        return new TariffResponseDto(1L, "Tarifa auto", "AUTO", new BigDecimal("100.00"), "Tarifa estandar");
    }

    @Test
    void findById_existingRecord_returnsMappedDto() {
        given(entryExitRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        EntryExitResponseDto result = entryExitService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getPlate()).isEqualTo("ABCD12");
    }

    @Test
    void findById_nonExistingRecord_returnsNull() {
        given(entryExitRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(entryExitService.findById(99L)).isNull();
    }

    @Test
    void findAll_returnsAllMappedRecords() {
        given(entryExitRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        assertThat(entryExitService.findAll()).hasSize(2);
    }

    @Test
    void findByPlate_returnsMatchingRecords() {
        given(entryExitRepository.findByPlate("ABCD12")).willReturn(List.of(entity(1L)));

        assertThat(entryExitService.findByPlate("ABCD12")).hasSize(1);
    }

    @Test
    void findByStatus_returnsMatchingRecords() {
        given(entryExitRepository.findByStatus("ACTIVE")).willReturn(List.of(entity(1L)));

        assertThat(entryExitService.findByStatus("ACTIVE")).hasSize(1);
    }

    @Test
    void findByPlateAndStatus_existingRecord_returnsMappedDto() {
        given(entryExitRepository.findByPlateAndStatus("ABCD12", "ACTIVE")).willReturn(Optional.of(entity(1L)));

        assertThat(entryExitService.findByPlateAndStatus("ABCD12", "ACTIVE")).isNotNull();
    }

    @Test
    void findByPlateAndStatus_noMatch_returnsNull() {
        given(entryExitRepository.findByPlateAndStatus("ZZZZZZ", "ACTIVE")).willReturn(Optional.empty());

        assertThat(entryExitService.findByPlateAndStatus("ZZZZZZ", "ACTIVE")).isNull();
    }

    @Test
    void findByParkingSpaceId_returnsMatchingRecords() {
        given(entryExitRepository.findByParkingSpaceId(1L)).willReturn(List.of(entity(1L)));

        assertThat(entryExitService.findByParkingSpaceId(1L)).hasSize(1);
    }

    @Test
    void create_allReferencesValid_savesAndReturnsDto() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(parkingSpace(true));
        given(vehicleClient.getVehicleByPlate("ABCD12")).willReturn(vehicle());
        given(tariffClient.getTariffById(1L)).willReturn(tariff());
        given(entryExitRepository.save(any(EntryExitModel.class))).willReturn(entity(1L));

        EntryExitResponseDto result = entryExitService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(entryExitRepository, times(1)).save(any(EntryExitModel.class));
    }

    @Test
    void create_parkingSpaceNotFound_throwsIllegalArgumentException() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(null);

        assertThatThrownBy(() -> entryExitService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no está disponible");
        verify(entryExitRepository, never()).save(any(EntryExitModel.class));
    }

    @Test
    void create_parkingSpaceUnavailable_throwsIllegalArgumentException() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(parkingSpace(false));

        assertThatThrownBy(() -> entryExitService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no está disponible");
        verify(entryExitRepository, never()).save(any(EntryExitModel.class));
    }

    @Test
    void create_vehicleNotFound_throwsIllegalArgumentException() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(parkingSpace(true));
        given(vehicleClient.getVehicleByPlate("ABCD12")).willReturn(null);

        assertThatThrownBy(() -> entryExitService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No existe un vehículo registrado");
        verify(entryExitRepository, never()).save(any(EntryExitModel.class));
    }

    @Test
    void create_tariffNotFound_throwsIllegalArgumentException() {
        given(parkingSpaceClient.getParkingSpaceById(1L)).willReturn(parkingSpace(true));
        given(vehicleClient.getVehicleByPlate("ABCD12")).willReturn(vehicle());
        given(tariffClient.getTariffById(1L)).willReturn(null);

        assertThatThrownBy(() -> entryExitService.create(requestDto(null)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("no existe");
        verify(entryExitRepository, never()).save(any(EntryExitModel.class));
    }

    @Test
    void update_existingRecord_savesAndReturnsUpdatedDto() {
        EntryExitModel updated = entity(1L);
        updated.setStatus("CLOSED");
        given(entryExitRepository.save(any(EntryExitModel.class))).willReturn(updated);

        EntryExitResponseDto result = entryExitService.update(requestDto(1L));

        assertThat(result.getStatus()).isEqualTo("CLOSED");
    }

    @Test
    void deleteById_existingRecord_deletesAndReturnsTrue() {
        given(entryExitRepository.existsById(1L)).willReturn(true);

        assertThat(entryExitService.deleteById(1L)).isTrue();
        verify(entryExitRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingRecord_returnsFalse() {
        given(entryExitRepository.existsById(99L)).willReturn(false);

        assertThat(entryExitService.deleteById(99L)).isFalse();
        verify(entryExitRepository, never()).deleteById(anyLong());
    }
}
