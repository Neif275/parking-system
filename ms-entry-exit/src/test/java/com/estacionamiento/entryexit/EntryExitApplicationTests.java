package com.estacionamiento.entryexit;

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
import com.estacionamiento.entryexit.service.EntryExitServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EntryExitApplicationTests {

    @Mock
    private EntryExitRepository entryExitRepository;

    @Mock
    private ParkingSpaceClient parkingSpaceClient;

    @Mock
    private TariffClient tariffClient;

    @Mock
    private VehicleClient vehicleClient;

    @InjectMocks
    private EntryExitServiceImpl service;

    @Test
    void testCreateEntrySuccessfully() {
        EntryExitRequestDto request = new EntryExitRequestDto(
                null, "SFKD95", LocalDateTime.of(2026, 5, 17, 9, 15), null, 2L, 1L, "ACTIVO"
        );
        ParkingSpaceResponseDto space = new ParkingSpaceResponseDto(2L, "A-02", true);
        VehicleResponseDto vehicle = new VehicleResponseDto(1L, "SFKD95", "Rojo", "2020", 2L);
        TariffResponseDto tariff = new TariffResponseDto(1L, "Tarifa Auto Estandar", "AUTO", BigDecimal.valueOf(37), "Tarifa por minuto para autos");
        EntryExitModel saved = new EntryExitModel(2L, "SFKD95", request.getEntryTime(), null, 2L, 1L, "ACTIVO");

        when(parkingSpaceClient.getParkingSpaceById(2L)).thenReturn(space);
        when(vehicleClient.getVehicleByPlate("SFKD95")).thenReturn(vehicle);
        when(tariffClient.getTariffById(1L)).thenReturn(tariff);
        when(entryExitRepository.save(any())).thenReturn(saved);

        EntryExitResponseDto result = service.create(request);

        assertNotNull(result);
        assertEquals("SFKD95", result.getPlate());
        assertEquals("ACTIVO", result.getStatus());
        verify(entryExitRepository, times(1)).save(any());
    }

    @Test
    void testCreateEntryFailsWhenSpaceUnavailable() {
        EntryExitRequestDto request = new EntryExitRequestDto(
                null, "SFKD95", LocalDateTime.of(2026, 5, 17, 9, 15), null, 2L, 1L, "ACTIVO"
        );
        ParkingSpaceResponseDto spaceOcupado = new ParkingSpaceResponseDto(2L, "A-02", false);

        when(parkingSpaceClient.getParkingSpaceById(2L)).thenReturn(spaceOcupado);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(request)
        );
        assertEquals("El espacio de estacionamiento no está disponible", ex.getMessage());
        verify(entryExitRepository, never()).save(any());
    }

    @Test
    void testCreateEntryFailsWhenVehicleNotFound() {
        EntryExitRequestDto request = new EntryExitRequestDto(
                null, "MNTQ47", LocalDateTime.of(2026, 5, 17, 9, 15), null, 1L, 1L, "ACTIVO"
        );
        ParkingSpaceResponseDto space = new ParkingSpaceResponseDto(1L, "A-01", true);

        when(parkingSpaceClient.getParkingSpaceById(1L)).thenReturn(space);
        when(vehicleClient.getVehicleByPlate("MNTQ47")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(request)
        );
        assertTrue(ex.getMessage().contains("MNTQ47"));
        verify(entryExitRepository, never()).save(any());
    }

    @Test
    void testCreateEntryFailsWhenTariffNotFound() {
        EntryExitRequestDto request = new EntryExitRequestDto(
                null, "SFKD95", LocalDateTime.of(2026, 5, 17, 9, 15), null, 2L, 99L, "ACTIVO"
        );
        ParkingSpaceResponseDto space = new ParkingSpaceResponseDto(2L, "A-02", true);
        VehicleResponseDto vehicle = new VehicleResponseDto(1L, "SFKD95", "Rojo", "2020", 2L);

        when(parkingSpaceClient.getParkingSpaceById(2L)).thenReturn(space);
        when(vehicleClient.getVehicleByPlate("SFKD95")).thenReturn(vehicle);
        when(tariffClient.getTariffById(99L)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.create(request)
        );
        assertTrue(ex.getMessage().contains("99"));
        verify(entryExitRepository, never()).save(any());
    }

    @Test
    void testFindByIdReturnsRecord() {
        EntryExitModel model = new EntryExitModel(
                1L, "RPSP29",
                LocalDateTime.of(2026, 5, 17, 8, 0),
                LocalDateTime.of(2026, 5, 17, 10, 30),
                1L, 1L, "COMPLETADO"
        );
        when(entryExitRepository.findById(1L)).thenReturn(Optional.of(model));

        EntryExitResponseDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("RPSP29", result.getPlate());
        assertEquals("COMPLETADO", result.getStatus());
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        when(entryExitRepository.findById(99L)).thenReturn(Optional.empty());

        EntryExitResponseDto result = service.findById(99L);

        assertNull(result);
    }

    @Test
    void testDeleteByIdReturnsTrueWhenExists() {
        when(entryExitRepository.existsById(1L)).thenReturn(true);

        boolean result = service.deleteById(1L);

        assertTrue(result);
        verify(entryExitRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdReturnsFalseWhenNotFound() {
        when(entryExitRepository.existsById(99L)).thenReturn(false);

        boolean result = service.deleteById(99L);

        assertFalse(result);
        verify(entryExitRepository, never()).deleteById(any());
    }

    @Test
    void testFindAllReturnsAllRecords() {
        List<EntryExitModel> models = List.of(
                new EntryExitModel(1L, "RPSP29", LocalDateTime.of(2026, 5, 17, 8, 0), LocalDateTime.of(2026, 5, 17, 10, 30), 1L, 1L, "COMPLETADO"),
                new EntryExitModel(2L, "SFKD95", LocalDateTime.of(2026, 5, 17, 9, 15), null, 2L, 2L, "ACTIVO")
        );
        when(entryExitRepository.findAll()).thenReturn(models);

        List<EntryExitResponseDto> result = service.findAll();

        assertEquals(2, result.size());
        assertEquals("RPSP29", result.get(0).getPlate());
        assertEquals("SFKD95", result.get(1).getPlate());
    }
}
