package com.estacionamiento.parking.service.impl;

import com.estacionamiento.parking.dto.SlotTypeRequestDto;
import com.estacionamiento.parking.dto.SlotTypeResponseDto;
import com.estacionamiento.parking.model.SlotTypeModel;
import com.estacionamiento.parking.repository.SlotTypeRepository;
import com.estacionamiento.parking.service.SlotTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlotTypeServiceImpl implements SlotTypeService {

    private final SlotTypeRepository slotTypeRepository;

    private SlotTypeResponseDto toDto(SlotTypeModel entity) {
        return new SlotTypeResponseDto(entity.getId(), entity.getName(), entity.getDescription());
    }

    private SlotTypeModel toEntity(SlotTypeRequestDto dto) {
        return new SlotTypeModel(dto.getId(), dto.getName(), dto.getDescription());
    }

    @Override
    public List<SlotTypeResponseDto> findAll() {
        return slotTypeRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public SlotTypeResponseDto findById(Integer id) {
        return slotTypeRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public SlotTypeResponseDto create(SlotTypeRequestDto dto) {
        return toDto(slotTypeRepository.save(toEntity(dto)));
    }

    @Override
    public SlotTypeResponseDto update(SlotTypeRequestDto dto) {
        return toDto(slotTypeRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Integer id) {
        if (slotTypeRepository.existsById(id)) {
            slotTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
