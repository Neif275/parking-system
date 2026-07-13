package com.estacionamiento.tariff.service;

import com.estacionamiento.tariff.dto.TariffRequestDto;
import com.estacionamiento.tariff.dto.TariffResponseDto;
import com.estacionamiento.tariff.model.TariffModel;
import com.estacionamiento.tariff.repository.TariffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TariffServiceImplTest {

    @Mock
    private TariffRepository tariffRepository;

    @InjectMocks
    private TariffServiceImpl tariffService;

    private TariffModel entity(long id) {
        return new TariffModel(id, "Tarifa auto", "Tarifa estandar", new BigDecimal("100.00"), "AUTO");
    }

    private TariffRequestDto requestDto(Long id) {
        return new TariffRequestDto(id, "Tarifa auto", "Tarifa estandar", new BigDecimal("100.00"), "AUTO");
    }

    @Test
    void findById_existingTariff_returnsMappedDto() {
        given(tariffRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        TariffResponseDto result = tariffService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getVehicleType()).isEqualTo("AUTO");
    }

    @Test
    void findById_nonExistingTariff_returnsNull() {
        given(tariffRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(tariffService.findById(99L)).isNull();
    }

    @Test
    void findAll_returnsAllMappedTariffs() {
        given(tariffRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        assertThat(tariffService.findAll()).hasSize(2);
    }

    @Test
    void findByVehicleType_returnsMatchingTariffs() {
        given(tariffRepository.findByVehicleType("MOTO")).willReturn(List.of(entity(1L)));

        List<TariffResponseDto> result = tariffService.findByVehicleType("MOTO");

        assertThat(result).hasSize(1);
    }

    @Test
    void findByVehicleType_noMatches_returnsEmptyList() {
        given(tariffRepository.findByVehicleType("CAMION")).willReturn(List.of());

        assertThat(tariffService.findByVehicleType("CAMION")).isEmpty();
    }

    @Test
    void create_validRequest_savesAndReturnsDto() {
        given(tariffRepository.save(any(TariffModel.class))).willReturn(entity(1L));

        TariffResponseDto result = tariffService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(tariffRepository, times(1)).save(any(TariffModel.class));
    }

    @Test
    void update_existingTariff_savesAndReturnsUpdatedDto() {
        TariffModel updated = entity(1L);
        updated.setPricePerMinute(new BigDecimal("150.00"));
        given(tariffRepository.save(any(TariffModel.class))).willReturn(updated);

        TariffResponseDto result = tariffService.update(requestDto(1L));

        assertThat(result.getPricePerMinute()).isEqualByComparingTo("150.00");
    }

    @Test
    void deleteById_existingTariff_deletesAndReturnsTrue() {
        given(tariffRepository.existsById(1L)).willReturn(true);

        assertThat(tariffService.deleteById(1L)).isTrue();
        verify(tariffRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingTariff_returnsFalse() {
        given(tariffRepository.existsById(99L)).willReturn(false);

        assertThat(tariffService.deleteById(99L)).isFalse();
        verify(tariffRepository, never()).deleteById(99L);
    }
}
