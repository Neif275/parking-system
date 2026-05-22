package com.estacionamiento.report.service;

import com.estacionamiento.report.dto.ReportRequestDto;
import com.estacionamiento.report.dto.ReportResponseDto;
import java.util.List;

public interface ReportService {
    ReportResponseDto findById(Long id);
    List<ReportResponseDto> findAll();
    List<ReportResponseDto> findByType(String type);
    List<ReportResponseDto> findByGeneratedBy(Long generatedBy);
    ReportResponseDto create(ReportRequestDto dto);
    ReportResponseDto update(ReportRequestDto dto);
    boolean deleteById(Long id);
}
