package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;

import java.util.List;

public interface OwnerReportService {
    int getId();

    OwnerReport addOwnerReport(OwnerReport ownerReport) throws PutToMapException;

    OwnerReport getOwnerReportById(int recordId);

    List<OwnerReport> getAllOwnerReports();

    OwnerReport editOwnerReportById(int recordId, OwnerReport ownerReport) throws EditMapException;

    void deleteAllFromOwnerReport();

    boolean deleteOwnerReportById(int recordId) throws DeleteFromMapException;

    int idOwnerReportByValue(OwnerReport ownerReport);
}
