package com.ward_n6.service.Impl;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerReport2Repository;
import com.ward_n6.service.OwnerReport2Service;

import java.util.List;

public class OwnerReport2ServiceImpl implements OwnerReport2Service {
    private final OwnerReport2Repository ownerReport2Repository;

    public OwnerReport2ServiceImpl(OwnerReport2Repository ownerReport2Repository) {
        this.ownerReport2Repository = ownerReport2Repository;
    }

    @Override
    public int getId() {
        return ownerReport2Repository.getId();
    }

    @Override
    public OwnerReport addOwnerReport(OwnerReport ownerReport) throws PutToMapException {
        return ownerReport2Repository.addOwnerReport(ownerReport);
    }

    @Override
    public OwnerReport getOwnerReportById(int recordId) {
        return ownerReport2Repository.getOwnerReportById(recordId);
    }

    @Override
    public List<OwnerReport> getAllOwnerReports() {
        return ownerReport2Repository.getAllOwnerReports();
    }

    @Override
    public OwnerReport editOwnerReportById(int recordId, OwnerReport ownerReport) throws EditMapException {
        return ownerReport2Repository.editOwnerReportById(recordId, ownerReport);
    }

    @Override
    public void deleteAllFromOwnerReport() {
        ownerReport2Repository.deleteAllFromOwnerReport();
    }

    @Override
    public boolean deleteOwnerReportById(int recordId) throws DeleteFromMapException {
        return ownerReport2Repository.deleteOwnerReportById(recordId);
    }

    @Override
    public int idOwnerReportByValue(OwnerReport ownerReport) {
        return ownerReport2Repository.idOwnerReportByValue(ownerReport);
    }
}
