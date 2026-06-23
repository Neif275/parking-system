package com.estacionamiento.report.service;

import com.estacionamiento.report.dto.ReportRequestDto;
import com.estacionamiento.report.dto.ReportResponseDto;
import com.estacionamiento.report.model.ReportModel;
import com.estacionamiento.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportRepository reportRepository;

    private ReportResponseDto toDto(ReportModel entity) {
        return new ReportResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getType(),
                entity.getGeneratedBy(),
                entity.getGeneratedAt(),
                entity.getDateFrom(),
                entity.getDateTo()
        );
    }

    private ReportModel toEntity(ReportRequestDto dto) {
        return new ReportModel(
                dto.getId(),
                dto.getTitle(),
                dto.getType(),
                dto.getGeneratedBy(),
                dto.getGeneratedAt(),
                dto.getDateFrom(),
                dto.getDateTo()
        );
    }

    @Override
    public ReportResponseDto findById(Long id) {
        log.info("Buscando reporte con id: {}", id);
        ReportResponseDto result = reportRepository.findById(id).map(this::toDto).orElse(null);
        if (result == null) log.warn("Reporte con id {} no encontrado", id);
        return result;
    }

    @Override
    public List<ReportResponseDto> findAll() {
        log.info("Consultando todos los reportes");
        return reportRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ReportResponseDto> findByType(String type) {
        log.info("Buscando reportes por tipo: {}", type);
        return reportRepository.findByType(type).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReportResponseDto> findByGeneratedBy(Long generatedBy) {
        log.info("Buscando reportes generados por usuario id: {}", generatedBy);
        return reportRepository.findByGeneratedBy(generatedBy).stream().map(this::toDto).toList();
    }

    @Override
    public ReportResponseDto create(ReportRequestDto dto) {
        log.info("Generando reporte tipo: {} por usuario id: {}", dto.getType(), dto.getGeneratedBy());
        ReportResponseDto result = toDto(reportRepository.save(toEntity(dto)));
        log.info("Reporte generado con id: {}", result.getId());
        return result;
    }

    @Override
    public ReportResponseDto update(ReportRequestDto dto) {
        log.info("Actualizando reporte con id: {}", dto.getId());
        return toDto(reportRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            log.info("Reporte con id {} eliminado", id);
            return true;
        }
        log.warn("No se pudo eliminar: reporte con id {} no existe", id);
        return false;
    }
}
