package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.SlotTypeRequestDto;
import com.estacionamiento.parking.dto.SlotTypeResponseDto;
import com.estacionamiento.parking.model.SlotTypeModel;
import com.estacionamiento.parking.repository.SlotTypeRepository;
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
class SlotTypeServiceImplTest {

    @Mock
    private SlotTypeRepository slotTypeRepository;

    @InjectMocks
    private SlotTypeServiceImpl slotTypeService;

    private SlotTypeModel entity(int id) {
        return new SlotTypeModel(id, "Estandar", "Espacio estandar");
    }

    private SlotTypeRequestDto requestDto(Integer id) {
        return new SlotTypeRequestDto(id, "Estandar", "Espacio estandar");
    }

    @Test
    void findAll_returnsAllMappedSlotTypes() {
        given(slotTypeRepository.findAll()).willReturn(List.of(entity(1), entity(2)));

        List<SlotTypeResponseDto> result = slotTypeService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void findById_existingSlotType_returnsMappedDto() {
        given(slotTypeRepository.findById(1)).willReturn(Optional.of(entity(1)));

        SlotTypeResponseDto result = slotTypeService.findById(1);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Estandar");
    }

    @Test
    void findById_nonExistingSlotType_returnsNull() {
        given(slotTypeRepository.findById(99)).willReturn(Optional.empty());

        assertThat(slotTypeService.findById(99)).isNull();
    }

    @Test
    void create_validRequest_savesAndReturnsDto() {
        given(slotTypeRepository.save(any(SlotTypeModel.class))).willReturn(entity(1));

        SlotTypeResponseDto result = slotTypeService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1);
        verify(slotTypeRepository, times(1)).save(any(SlotTypeModel.class));
    }

    @Test
    void update_existingSlotType_savesAndReturnsUpdatedDto() {
        SlotTypeModel updated = entity(1);
        updated.setDescription("Espacio para discapacitados");
        given(slotTypeRepository.save(any(SlotTypeModel.class))).willReturn(updated);

        SlotTypeResponseDto result = slotTypeService.update(requestDto(1));

        assertThat(result.getDescription()).isEqualTo("Espacio para discapacitados");
    }

    @Test
    void deleteById_existingSlotType_deletesAndReturnsTrue() {
        given(slotTypeRepository.existsById(1)).willReturn(true);

        assertThat(slotTypeService.deleteById(1)).isTrue();
        verify(slotTypeRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteById_nonExistingSlotType_returnsFalse() {
        given(slotTypeRepository.existsById(99)).willReturn(false);

        assertThat(slotTypeService.deleteById(99)).isFalse();
        verify(slotTypeRepository, never()).deleteById(99);
    }
}
