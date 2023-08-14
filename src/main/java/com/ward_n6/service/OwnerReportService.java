package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.EditMapException;

import java.util.List;

public interface OwnerReportService {
    //
    //    @Override
    //    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
    //        return this.ownerReportRepository.save(ownerReport);
    //    }
    //
    OwnerReport getOwnerReportById(int recordId);

    //
    //    @Override
    //    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
    //        return this.ownerReportRepository.save(ownerReport);
    //    }
    //
    OwnerReport getOwnerReportById(Long recordId);

//
//    OwnerReport addOwnerReport(OwnerReport ownerReport);
//
////    OwnerReport getOwnerReportById(int recordId);
//
//    List<OwnerReport> getAllOwnerReports();
//
//    OwnerReport editOwnerReportById(Integer recordId, OwnerReport ownerReport) throws EditMapException;
//
//    OwnerReport editOwnerReportById(long recordId, OwnerReport ownerReport);
//
//    void deleteOwnerReportById(Integer recordId);
}
