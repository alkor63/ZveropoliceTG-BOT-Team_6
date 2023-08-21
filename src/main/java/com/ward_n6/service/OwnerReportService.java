package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface OwnerReportService {
    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    OwnerReport addOwnerReport(OwnerReport ownerReport);

    // ++++++++++++++++++++++++++++++++++++
    List<OwnerReport> getAllOwnerReports();

    // ++++++++++++++++++++++++++++++++++++
    OwnerReport getOwnerReportById(Integer ownerReportId)
            throws EntityNotFoundException;

    //+++++++++++++++++++++++++++++++++++++++++
    ResponseEntity<OwnerReport> deleteOwnerReportById(Integer ownerReportId)
            throws EntityNotFoundException;

    OwnerReport editOwnerReportById(int ownerReportId, OwnerReport ownerReport)
        throws EntityNotFoundException;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++

}
