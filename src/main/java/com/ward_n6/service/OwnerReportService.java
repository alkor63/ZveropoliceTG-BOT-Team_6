package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

//import javax.persistence.EntityNotFoundException;
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
//            throws EntityNotFoundException;
            throws RecordNotFoundException;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++


}
