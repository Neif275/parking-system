package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.ZoneRequestDto;
import com.estacionamiento.parking.dto.ZoneResponseDto;
import com.estacionamiento.parking.model.Floor;
import com.estacionamiento.parking.model.Zone;
import com.estacionamiento.parking.repository.FloorRepository;
import com.estacionamiento.parking.repository.ZoneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class ZoneServiceImplTest {

    @Mock
    private ZoneRepository zoneRepository;
    @Mock
    private FloorRepository floorRepository;

    @InjectMocks
    private ZoneServiceImpl zoneService;

    private Floor floor() {
        return new Floor(1, 1, "Piso principal");
    }

    private Zone entity(int id) {
        return new Zone(id, "Zona A", floor());
    }

    private ZoneRequestDto requestDto(Integer id) {
        return new ZoneRequestDto(id, "Zona A", 1);
    }

    @Test
    void findAll_returnsAllMappedZones() {
        given(zoneRepository.findAll()).willReturn(List.of(entity(1), entity(2)));

        List<ZoneResponseDto> result = zoneService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_existingZone_returnsMappedDto() {
        given(zoneRepository.findById(1)).willReturn(Optional.of(entity(1)));

        ZoneResponseDto result = zoneService.findById(1);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Zona A");
        assertThat(result.getFloor().getId()).isEqualTo(1);
    }

    @Test
    void findById_nonExistingZone_returnsNull() {
        given(zoneRepository.findById(99)).willReturn(Optional.empty());

        assertThat(zoneService.findById(99)).isNull();
    }

    @Test
    void create_validFloorReference_savesAndReturnsDto() {
        given(floorRepository.findById(1)).willReturn(Optional.of(floor()));
        given(zoneRepository.save(any(Zone.class))).willReturn(entity(1));

        ZoneResponseDto result = zoneService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1);
        verify(zoneRepository, times(1)).save(any(Zone.class));
    }

    @Test
    void create_missingFloor_throwsNoSuchElementException() {
        given(floorRepository.findById(1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> zoneService.create(requestDto(null)))
                .isInstanceOf(NoSuchElementException.class);
        verify(zoneRepository, never()).save(any(Zone.class));
    }

    @Test
    void update_validFloorReference_savesAndReturnsUpdatedDto() {
        given(floorRepository.findById(1)).willReturn(Optional.of(floor()));
        Zone updated = entity(1);
        updated.setName("Zona B");
        given(zoneRepository.save(any(Zone.class))).willReturn(updated);

        ZoneResponseDto result = zoneService.update(requestDto(1));

        assertThat(result.getName()).isEqualTo("Zona B");
    }

    @Test
    void deleteById_existingZone_deletesAndReturnsTrue() {
        given(zoneRepository.existsById(1)).willReturn(true);

        assertThat(zoneService.deleteById(1)).isTrue();
        verify(zoneRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteById_nonExistingZone_returnsFalse() {
        given(zoneRepository.existsById(99)).willReturn(false);

        assertThat(zoneService.deleteById(99)).isFalse();
        verify(zoneRepository, never()).deleteById(99);
    }
}
