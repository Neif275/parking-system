package com.estacionamiento.report.service;

import com.estacionamiento.report.dto.ReportRequestDto;
import com.estacionamiento.report.dto.ReportResponseDto;
import com.estacionamiento.report.model.ReportModel;
import com.estacionamiento.report.repository.ReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private ReportModel entity(long id) {
        return new ReportModel(id, "Reporte mensual", "REVENUE", 5L, LocalDateTime.now(),
                LocalDateTime.now().minusDays(30), LocalDateTime.now());
    }

    private ReportRequestDto requestDto(Long id) {
        return new ReportRequestDto(id, "Reporte mensual", "REVENUE", 5L, LocalDateTime.now(),
                LocalDateTime.now().minusDays(30), LocalDateTime.now());
    }

    @Test
    void findById_existingReport_returnsMappedDto() {
        given(reportRepository.findById(1L)).willReturn(Optional.of(entity(1L)));

        ReportResponseDto result = reportService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("REVENUE");
    }

    @Test
    void findById_nonExistingReport_returnsNull() {
        given(reportRepository.findById(99L)).willReturn(Optional.empty());

        assertThat(reportService.findById(99L)).isNull();
    }

    @Test
    void findAll_returnsAllMappedReports() {
        given(reportRepository.findAll()).willReturn(List.of(entity(1L), entity(2L)));

        assertThat(reportService.findAll()).hasSize(2);
    }

    @Test
    void findByType_returnsMatchingReports() {
        given(reportRepository.findByType("REVENUE")).willReturn(List.of(entity(1L)));

        assertThat(reportService.findByType("REVENUE")).hasSize(1);
    }

    @Test
    void findByGeneratedBy_returnsMatchingReports() {
        given(reportRepository.findByGeneratedBy(5L)).willReturn(List.of(entity(1L)));

        assertThat(reportService.findByGeneratedBy(5L)).hasSize(1);
    }

    @Test
    void create_validRequest_savesAndReturnsDto() {
        given(reportRepository.save(any(ReportModel.class))).willReturn(entity(1L));

        ReportResponseDto result = reportService.create(requestDto(null));

        assertThat(result.getId()).isEqualTo(1L);
        verify(reportRepository, times(1)).save(any(ReportModel.class));
    }

    @Test
    void update_existingReport_savesAndReturnsUpdatedDto() {
        ReportModel updated = entity(1L);
        updated.setTitle("Reporte mensual actualizado");
        given(reportRepository.save(any(ReportModel.class))).willReturn(updated);

        ReportResponseDto result = reportService.update(requestDto(1L));

        assertThat(result.getTitle()).isEqualTo("Reporte mensual actualizado");
    }

    @Test
    void deleteById_existingReport_deletesAndReturnsTrue() {
        given(reportRepository.existsById(1L)).willReturn(true);

        assertThat(reportService.deleteById(1L)).isTrue();
        verify(reportRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_nonExistingReport_returnsFalse() {
        given(reportRepository.existsById(99L)).willReturn(false);

        assertThat(reportService.deleteById(99L)).isFalse();
        verify(reportRepository, never()).deleteById(99L);
    }
}
