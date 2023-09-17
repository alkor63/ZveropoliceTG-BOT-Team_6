package com.ward_n6.service.interfaces;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service

public interface OwnerReportService {

    // ++++++++++++++++++++++++++++++++++++
    List<OwnerReport> getAllOwnerReports();

    // ++++++++++++++++++++++++++++++++++++

    OwnerReport getOwnerReportById(long ownerReportId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteOwnerReportById(long ownerReportId);


    OwnerReport addOwnerReportFromController(long ownerId, PetsType petsType, boolean photo,
                                             String nutrition, String health, String behavior, long petId);

    OwnerReport editOwnerReportByIdFromController(long ownerReportId, boolean photo,
                                                  String nutrition, String health, String behavior)
                                                     throws EntityNotFoundException;


//++++++++++++++++++++++++++++++++++++++++++++++++++++
}
