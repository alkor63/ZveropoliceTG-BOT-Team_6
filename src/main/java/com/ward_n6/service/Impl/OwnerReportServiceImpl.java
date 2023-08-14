package com.ward_n6.service.Impl;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.InvalidRequestException;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.service.OwnerReportService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OwnerReportServiceImpl implements OwnerReportService {
    private final OwnerReportRepository ownerReportRepository;

    public OwnerReportServiceImpl(OwnerReportRepository ownerReportRepository) {
        this.ownerReportRepository = ownerReportRepository;
    }

    @Override
    public OwnerReport getOwnerReportById(int recordId) {
        return null;
    }

    //
//    @Override
//    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
//        return this.ownerReportRepository.save(ownerReport);
//    }
//
    @Override
    public OwnerReport getOwnerReportById(Long recordId) {
            Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(recordId);
            if(optionalOwnerReport.isPresent()) {
                final OwnerReport ownerReport = optionalOwnerReport.get();
                return ownerReport;
        }
        throw new InvalidRequestException("ОШИБКА: не удалось найти отчёт с id = "+recordId);
    }

//    @Override
//    public List<OwnerReport> getAllOwnerReports() {
//        return this.ownerReportRepository.findAll();
//    }
//
//
//    @Override
//    public OwnerReport editOwnerReportById(Integer recordId, OwnerReport ownerReport) {
//        long longId = recordId;
//        return this.ownerReportRepository.updateReport(longId,
//                ownerReport.isHavePhoto(),
//                ownerReport.getNutrition(),
//                ownerReport.getPetsBehavior(),
//                ownerReport.getPetsHealth(),
//                ownerReport.getReportDateTime());
//    }
//
//    @Override
//    public OwnerReport editOwnerReportById(long recordId, OwnerReport ownerReport) {
//        return null;
//    }
//
//    @Override
//    public void deleteOwnerReportById(Integer recordId) {
//        if (ownerReportRepository.findById(recordId).isPresent())
//            ownerReportRepository.deleteById(recordId);
//    }

}

