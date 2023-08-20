package com.ward_n6.service.Impl;


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
//
//    @Override
//    public OwnerReport getOwnerReportById(int recordId) {
//        long longId = recordId;
//        return ownerReportRepository.findById(longId).orElseThrow(
//                () -> new InvalidRequestException("Отчёта с id = " + recordId + " нет в нашей базе."));
//    }
//
//
//    @Override
//    public OwnerReport getOwnerReportById(Long recordId) {
//            Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(recordId);
//            if(optionalOwnerReport.isPresent()) {
//                final OwnerReport ownerReport = optionalOwnerReport.get();
//                return ownerReport;
//        }
//        throw new InvalidRequestException("ОШИБКА: не удалось найти отчёт с id = "+recordId);
//    }

}

