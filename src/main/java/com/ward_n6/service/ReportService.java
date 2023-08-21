package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;


    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void save(OwnerReport ownerReport) {
        reportRepository.save(ownerReport);
    }

    Optional<OwnerReport> optionalOwnerReport;
   public void optiOwnerReport(OwnerReport ownerReport){
       OwnerReport existingOwnerReport = optionalOwnerReport.get();
       existingOwnerReport.setHavePhoto(ownerReport.isHavePhoto());
       existingOwnerReport.setNutrition(ownerReport.getNutrition());
       existingOwnerReport.setPetsBehavior(ownerReport.getPetsBehavior());
       existingOwnerReport.setPetsHealth(ownerReport.getPetsHealth());
       existingOwnerReport.setReportDateTime(ownerReport.getReportDateTime());
   }
}
