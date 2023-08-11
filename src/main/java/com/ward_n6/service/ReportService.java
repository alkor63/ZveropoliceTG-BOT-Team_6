package com.ward_n6.service;

import com.ward_n6.entity.owners.OwnerReport;
import com.ward_n6.repository.ReportRepository;

public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void save(OwnerReport ownerReport) {
        reportRepository.save(ownerReport);
    }

}
