package com.ward_n6.service.Impl;


import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.service.OwnerReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerReportServiceImpl implements OwnerReportService {
    private final OwnerReportRepository ownerReportRepository;

    public OwnerReportServiceImpl(OwnerReportRepository ownerReportRepository) {
        this.ownerReportRepository = ownerReportRepository;
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
        return ownerReportRepository.save(ownerReport);
    }

    // ++++++++++++++++++++++++++++++++++++
    @Override
    public List<OwnerReport> getAllOwnerReports() {
        return ownerReportRepository.findAll();
    }

    // ++++++++++++++++++++++++++++++++++++
    @Override
    public OwnerReport getOwnerReportById(Integer ownerReportId)
            throws EntityNotFoundException {
        long longId = ownerReportId;
        Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(longId);
        if (optionalOwnerReport.isPresent())
            return optionalOwnerReport.get();
        throw new EntityNotFoundException("В базе нет отчёта с id=" + ownerReportId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public ResponseEntity<OwnerReport> deleteOwnerReportById(Integer ownerReportId)
            throws EntityNotFoundException {
        long longId = ownerReportId;
        Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(longId);
        if (optionalOwnerReport.isPresent()) {
            ownerReportRepository.deleteById(longId);
        }
        throw new EntityNotFoundException("Невозможно удалить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
    }

    @Override
    public OwnerReport editOwnerReportById(int ownerReportId, OwnerReport ownerReport)
            throws EntityNotFoundException {
        long longId = ownerReportId;
        Optional optionalOwnerReport = ownerReportRepository.findById(longId);
        if (!optionalOwnerReport.isPresent()) {
            throw new EntityNotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
        }
        OwnerReport existingOwnerReport = (OwnerReport) optionalOwnerReport.get();

        existingOwnerReport.setHavePhoto(ownerReport.isHavePhoto());
        existingOwnerReport.setNutrition(ownerReport.getNutrition());
        existingOwnerReport.setPetsBehavior(ownerReport.getPetsBehavior());
        existingOwnerReport.setPetsHealth(ownerReport.getPetsHealth());
        existingOwnerReport.setReportDateTime(ownerReport.getReportDateTime());

        return ownerReportRepository.save(ownerReport);
    }
}

