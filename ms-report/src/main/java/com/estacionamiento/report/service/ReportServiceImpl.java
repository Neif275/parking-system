package com.estacionamiento.report.service;

import com.estacionamiento.report.dto.ReportRequestDto;
import com.estacionamiento.report.dto.ReportResponseDto;
import com.estacionamiento.report.model.ReportModel;
import com.estacionamiento.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

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
        return reportRepository.findById(id).map(this::toDto).orElse(null);
    }

    @Override
    public List<ReportResponseDto> findAll() {
        return reportRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<ReportResponseDto> findByType(String type) {
        return reportRepository.findByType(type).stream().map(this::toDto).toList();
    }

    @Override
    public List<ReportResponseDto> findByGeneratedBy(Long generatedBy) {
        return reportRepository.findByGeneratedBy(generatedBy).stream().map(this::toDto).toList();
    }

    @Override
    public ReportResponseDto create(ReportRequestDto dto) {
        return toDto(reportRepository.save(toEntity(dto)));
    }

    @Override
    public ReportResponseDto update(ReportRequestDto dto) {
        return toDto(reportRepository.save(toEntity(dto)));
    }

    @Override
    public boolean deleteById(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
