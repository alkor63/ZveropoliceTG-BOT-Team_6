package com.ward_n6.repository;

import com.ward_n6.entity.owners.OwnerReport;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;

import java.util.List;

public interface OwnerReportRepository {
    OwnerReport addOwnerReport(OwnerReport ownerReport) throws PutToMapException;

    OwnerReport getOwnerReportById(int recordId);

    List<OwnerReport> getAllOwnerReports();

    OwnerReport editOwnerReportById(int recordId, OwnerReport ownerReport) throws EditMapException;

    void deleteAllFromOwnerReport();

    boolean deleteOwnerReportById(int recordId) throws DeleteFromMapException;

    int idOwnerReportByValue(OwnerReport ownerReport);

    int getId();
}
