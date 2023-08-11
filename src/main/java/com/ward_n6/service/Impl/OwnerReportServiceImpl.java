package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.OwnerReport;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.service.OwnerReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerReportServiceImpl implements OwnerReportService {
    private final OwnerReportRepository ownerReportRepository;

    public OwnerReportServiceImpl(OwnerReportRepository ownerReportRepository) {
        this.ownerReportRepository = ownerReportRepository;
    }

    @Override
    public int getId() {
        return ownerReportRepository.getId();
    }

    @Override
    public OwnerReport addOwnerReport(OwnerReport ownerReport) throws PutToMapException {
        return ownerReportRepository.addOwnerReport(ownerReport);
    }

    @Override
    public OwnerReport getOwnerReportById(int recordId) {
        return ownerReportRepository.getOwnerReportById(recordId);
    }

    @Override
    public List<OwnerReport> getAllOwnerReports() {
        return ownerReportRepository.getAllOwnerReports();
    }

    @Override
    public OwnerReport editOwnerReportById(int recordId, OwnerReport ownerReport) throws EditMapException {
        return ownerReportRepository.editOwnerReportById(recordId, ownerReport);
    }

    @Override
    public void deleteAllFromOwnerReport() {
        ownerReportRepository.deleteAllFromOwnerReport();
    }

    @Override
    public boolean deleteOwnerReportById(int recordId) throws DeleteFromMapException {
        return ownerReportRepository.deleteOwnerReportById(recordId);
    }

    @Override
    public int idOwnerReportByValue(OwnerReport ownerReport) {
        return ownerReportRepository.idOwnerReportByValue(ownerReport);
    }
}

