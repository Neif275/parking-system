package com.estacionamiento.vehicle.service;

import com.estacionamiento.vehicle.dto.VehicleRequestDto;
import com.estacionamiento.vehicle.dto.VehicleResponseDto;
import com.estacionamiento.vehicle.model.BrandModel;
import com.estacionamiento.vehicle.model.ModelModel;
import com.estacionamiento.vehicle.model.VehicleCategoryModel;
import com.estacionamiento.vehicle.model.VehicleModel;
import com.estacionamiento.vehicle.repository.VehicleCategoryRepository;
import com.estacionamiento.vehicle.repository.VehicleModelRepository;
import com.estacionamiento.vehicle.repository.VehicleRepository;
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
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleModelRepository vehicleModelRepository;
    @Mock
    private VehicleCategoryRepository vehicleCategoryRepository;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private BrandModel brand() {
        return new BrandModel(1, "Toyota");
    }

    private ModelModel model() {
        return new ModelModel(1, "Corolla", brand());
    }

    private VehicleCategoryModel category() {
        return new VehicleCategoryModel(1, "Sedan", "Vehiculo sedan");
    }

    private VehicleModel entity(long id) {
        return new VehicleModel(id, "ABCD12", "Rojo", "2020", model(), category(), 5L);
    }

    private VehicleRequestDto requestDto(Long id) {
        return new VehicleRequestDto(id, "ABCD12", "Rojo", "2020", 1, 1, 5L);
    }

    @Test
    void findById_existingVehicle_returnsMappedDto() {
        given(vehicleRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        VehicleResponseDto result = vehicleService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getPlate()).isEqualTo("ABCD12");
        assertThat(result.getModel().getName()).isEqualTo("Corolla");
        assertThat(result.getCategory().getName()).isEqualTo("Sedan");
    }

    @Test
    void findById_nonExistingVehicle_returnsNull() {
        given(vehicleRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(vehicleService.findById(99L)).isNull();
    }

    @Test
    void findByPlate_existingVehicle_returnsMappedDto() {
        given(vehicleRepository.findByPlate("ABCD12")).willReturn(Optional.of(entity(1L)));

        VehicleResponseDto result = vehicleService.findByPlate("ABCD12");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void findByPlate_nonExistingVehicle_returnsNull() {
        given(vehicleRepository.findByPlate("ZZZZZZ")).willReturn(Optional.empty());

        assertThat(vehicleService.findByPlate("ZZZZZZ")).isNull();
    }

    @Test
    void findAll_returnsAllMappedVehicles() {
        given(vehicleRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        List<VehicleResponseDto> result = vehicleService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void create_validReferences_savesAndReturnsDto() {
        given(vehicleModelRepository.findById(1)).willReturn(Optional.of(model()));
        given(vehicleCategoryRepository.findById(1)).willReturn(Optional.of(category()));
        given(vehicleRepository.save(any(VehicleModel.class))).willReturn(entity(1L));

        VehicleResponseDto result = vehicleService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(vehicleRepository, times(1)).save(any(VehicleModel.class));
    }

    @Test
    void create_missingModel_throwsNoSuchElementException() {
        given(vehicleModelRepository.findById(1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.create(requestDto(null)))
                .isInstanceOf(NoSuchElementException.class);
        verify(vehicleRepository, never()).save(any(VehicleModel.class));
    }

    @Test
    void create_missingCategory_throwsNoSuchElementException() {
        given(vehicleModelRepository.findById(1)).willReturn(Optional.of(model()));
        given(vehicleCategoryRepository.findById(1)).willReturn(Optional.empty());

        assertThatThrownBy(() -> vehicleService.create(requestDto(null)))
                .isInstanceOf(NoSuchElementException.class);
        verify(vehicleRepository, never()).save(any(VehicleModel.class));
    }

    @Test
    void update_validReferences_savesAndReturnsUpdatedDto() {
        given(vehicleModelRepository.findById(1)).willReturn(Optional.of(model()));
        given(vehicleCategoryRepository.findById(1)).willReturn(Optional.of(category()));
        VehicleModel updated = entity(1L);
        updated.setColor("Azul");
        given(vehicleRepository.save(any(VehicleModel.class))).willReturn(updated);

        VehicleResponseDto result = vehicleService.update(requestDto(1L));

        assertThat(result.getColor()).isEqualTo("Azul");
    }

    @Test
    void deleteById_existingVehicle_deletesAndReturnsTrue() {
        given(vehicleRepository.existsById(1L)).willReturn(true);

        assertThat(vehicleService.deleteById(1L)).isTrue();
        verify(vehicleRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingVehicle_returnsFalse() {
        given(vehicleRepository.existsById(99L)).willReturn(false);

        assertThat(vehicleService.deleteById(99L)).isFalse();
        verify(vehicleRepository, never()).deleteById(99L);
    }
}
