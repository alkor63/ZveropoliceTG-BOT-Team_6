package com.ward_n6.service.interfaces;

import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service

public interface OwnerReportService {

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    OwnerReport addOwnerReport(OwnerReport ownerReport);

    // ++++++++++++++++++++++++++++++++++++
    List<OwnerReport> getAllOwnerReports();

    // ++++++++++++++++++++++++++++++++++++

    OwnerReport getOwnerReportById(Integer ownerReportId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteOwnerReportById(Integer ownerReportId);

    OwnerReport editOwnerReportById(int ownerReportId, OwnerReport ownerReport)
            throws EntityNotFoundException;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++

}
