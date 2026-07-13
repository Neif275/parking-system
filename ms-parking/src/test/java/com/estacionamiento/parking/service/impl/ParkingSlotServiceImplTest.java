package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.ParkingSlotRequestDto;
import com.estacionamiento.parking.dto.ParkingSlotResponseDto;
import com.estacionamiento.parking.model.Floor;
import com.estacionamiento.parking.model.ParkingSlotModel;
import com.estacionamiento.parking.model.SlotTypeModel;
import com.estacionamiento.parking.model.Zone;
import com.estacionamiento.parking.repository.ParkingSlotRepository;
import com.estacionamiento.parking.repository.SlotTypeRepository;
import com.estacionamiento.parking.repository.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParkingSlotServiceImplTest {

    @Mock
    private ParkingSlotRepository parkingSlotRepository;
    @Mock
    private ZoneRepository zoneRepository;
    @Mock
    private SlotTypeRepository slotTypeRepository;

    @InjectMocks
    private ParkingSlotServiceImpl parkingSlotService;

    private Zone zone() {
        return new Zone(1, "Zona A", new Floor(1, 1, "Piso principal"));
    }

    private SlotTypeModel slotType() {
        return new SlotTypeModel(1, "Estandar", "Espacio estandar");
    }

    private ParkingSlotModel entity(long id, boolean available) {
        return new ParkingSlotModel(id, "A-1", zone(), slotType(), available);
    }

    private ParkingSlotRequestDto requestDto(Long id, Boolean isAvailable) {
        return new ParkingSlotRequestDto(id, "A-1", 1, 1, isAvailable);
    }

    @Test
    void findAll_returnsAllMappedSlots() {
        given(parkingSlotRepository.findAll()).willReturn(List.of(entity(1L, true), entity(2L, false)));

        List<ParkingSlotResponseDto> result = parkingSlotService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_existingSlot_returnsMappedDto() {
        given(parkingSlotRepository.findById(1L)).willReturn(Optional.of(entity(1L, true)));

        ParkingSlotResponseDto result = parkingSlotService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getIsAvailable()).isTrue();
    }

    @Test
    void findById_nonExistingSlot_returnsNull() {
        given(parkingSlotRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(parkingSlotService.findById(99L)).isNull();
    }

    @Test
    void findAvailable_returnsOnlyAvailableSlots() {
        given(parkingSlotRepository.findByIsAvailableTrue()).willReturn(List.of(entity(1L, true)));

        List<ParkingSlotResponseDto> result = parkingSlotService.findAvailable();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getIsAvailable()).isTrue();
    }

    @Test
    void create_validReferencesAndExplicitAvailability_savesAndReturnsDto() {
        given(zoneRepository.findById(1)).willReturn(Optional.of(zone()));
        given(slotTypeRepository.findById(1)).willReturn(Optional.of(slotType()));
        given(parkingSlotRepository.save(any(ParkingSlotModel.class))).willReturn(entity(1L, false));

        ParkingSlotResponseDto result = parkingSlotService.create(requestDto(null, false));

        assertThat(result.getIsAvailable()).isFalse();
    }

    @Test
    void create_nullAvailability_defaultsToTrue() {
        given(zoneRepository.findById(1)).willReturn(Optional.of(zone()));
        given(slotTypeRepository.findById(1)).willReturn(Optional.of(slotType()));
        ArgumentCaptor<ParkingSlotModel> captor = ArgumentCaptor.forClass(ParkingSlotModel.class);
        given(parkingSlotRepository.save(captor.capture())).willReturn(entity(1L, true));

        parkingSlotService.create(requestDto(null, null));

        assertThat(captor.getValue().getIsAvailable()).isTrue();
    }

    @Test
    void create_missingZone_throwsNoSuchElementException() {
        given(zoneRepository.findById(1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> parkingSlotService.create(requestDto(null, true)))
                .isInstanceOf(NoSuchElementException.class);
        verify(parkingSlotRepository, never()).save(any(ParkingSlotModel.class));
    }

    @Test
    void create_missingSlotType_throwsNoSuchElementException() {
        given(zoneRepository.findById(1)).willReturn(Optional.of(zone()));
        given(slotTypeRepository.findById(1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> parkingSlotService.create(requestDto(null, true)))
                .isInstanceOf(NoSuchElementException.class);
        verify(parkingSlotRepository, never()).save(any(ParkingSlotModel.class));
    }

    @Test
    void deleteById_existingSlot_deletesAndReturnsTrue() {
        given(parkingSlotRepository.existsById(1L)).willReturn(true);

        assertThat(parkingSlotService.deleteById(1L)).isTrue();
        verify(parkingSlotRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingSlot_returnsFalse() {
        given(parkingSlotRepository.existsById(99L)).willReturn(false);

        assertThat(parkingSlotService.deleteById(99L)).isFalse();
        verify(parkingSlotRepository, never()).deleteById(99L);
    }
}
