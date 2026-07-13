package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.FloorRequestDto;
import com.estacionamiento.parking.dto.FloorResponseDto;
import com.estacionamiento.parking.model.Floor;
import com.estacionamiento.parking.repository.FloorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FloorServiceImplTest {

    @Mock
    private FloorRepository floorRepository;

    @InjectMocks
    private FloorServiceImpl floorService;

    private Floor entity(int id) {
        return new Floor(id, 1, "Piso principal");
    }

    private FloorRequestDto requestDto(Integer id) {
        return new FloorRequestDto(id, 1, "Piso principal");
    }

    @Test
    void findAll_returnsAllMappedFloors() {
        given(floorRepository.findAll()).willReturn(List.of(entity(1), entity(2)));

        List<FloorResponseDto> result = floorService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_existingFloor_returnsMappedDto() {
        given(floorRepository.findById(1)).willReturn(Optional.of(entity(1)));

        FloorResponseDto result = floorService.findById(1);

        assertThat(result).isNotNull();
        assertThat(result.getNumber()).isEqualTo(1);
    }

    @Test
    void findById_nonExistingFloor_returnsNull() {
        given(floorRepository.findById(99)).willReturn(Optional.empty());

        assertThat(floorService.findById(99)).isNull();
    }

    @Test
    void create_validRequest_savesAndReturnsDto() {
        given(floorRepository.save(any(Floor.class))).willReturn(entity(1));

        FloorResponseDto result = floorService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1);
        verify(floorRepository, times(1)).save(any(Floor.class));
    }

    @Test
    void update_existingFloor_savesAndReturnsUpdatedDto() {
        Floor updated = entity(1);
        updated.setDescription("Piso remodelado");
        given(floorRepository.save(any(Floor.class))).willReturn(updated);

        FloorResponseDto result = floorService.update(requestDto(1));

        assertThat(result.getDescription()).isEqualTo("Piso remodelado");
    }

    @Test
    void deleteById_existingFloor_deletesAndReturnsTrue() {
        given(floorRepository.existsById(1)).willReturn(true);

        assertThat(floorService.deleteById(1)).isTrue();
        verify(floorRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteById_nonExistingFloor_returnsFalse() {
        given(floorRepository.existsById(99)).willReturn(false);

        assertThat(floorService.deleteById(99)).isFalse();
        verify(floorRepository, never()).deleteById(99);
    }
}
