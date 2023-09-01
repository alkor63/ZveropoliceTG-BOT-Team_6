package com.ward_n6.service.interfaces;

import com.ward_n6.entity.reports.OwnerReport;

import javax.persistence.EntityNotFoundException;

public interface OwnerReportService {

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    OwnerReport addOwnerReport(OwnerReport ownerReport);

    OwnerReport getOwnerReportById(Integer ownerReportId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteOwnerReportById(Integer ownerReportId);

    OwnerReport editOwnerReportById(int ownerReportId, OwnerReport ownerReport)
            throws EntityNotFoundException;
}
