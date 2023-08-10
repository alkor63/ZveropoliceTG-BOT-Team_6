package com.ward_n6.repository;

import com.ward_n6.entity.reports.OwnerReport2;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;

import java.util.List;

public interface OwnerReport2Repository {
    OwnerReport2 addOwnerReport(OwnerReport2 ownerReport2);

    OwnerReport2 getOwnerReportById(int recordId);

    List<OwnerReport2> getAllOwnerReports();

    OwnerReport2 editOwnerReportById(int recordId, OwnerReport2 ownerReport2) throws EditMapException;

    void deleteAllFromOwnerReport();

    boolean deleteOwnerReportById(int recordId) throws DeleteFromMapException;

    int idOwnerReport2ByValue(OwnerReport2 ownerReport2);

    int getId();
}
