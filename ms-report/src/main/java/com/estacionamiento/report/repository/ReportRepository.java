package com.estacionamiento.report.repository;

import com.estacionamiento.report.model.ReportModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<ReportModel, Long> {
    List<ReportModel> findByType(String type);
    List<ReportModel> findByGeneratedBy(Long generatedBy);
}
