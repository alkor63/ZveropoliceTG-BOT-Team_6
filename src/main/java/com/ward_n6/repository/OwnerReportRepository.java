package com.ward_n6.repository;

import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OwnerReportRepository extends JpaRepository <OwnerReport, Long>{

//    OwnerReport owr;
//        @Modifying
//    @Query("update owner_report set owr.havePhoto = :havePhoto WHERE owr.id = :id");
//
//    @Query("update owr set owr.nutrition = :nutrition WHERE owr.id = :id");
//    @Query("update owr set owr.petsBehavior = :petsBehavior WHERE owr.id = :id");
//    @Query("update owr set owr.petsHealth = :petsHealth WHERE owr.id = :id");
//    @Query("update owr set owr.reportDateTime = : reportDateTime WHERE owr.id = :id");
//
//    OwnerReport updateReport(@Param("id") Long id,
//                      @Param("havePhoto") boolean havePhoto,
//                      @Param("nutrition") String nutrition,
//                      @Param("petsBehavior") String petsBehavior,
//                      @Param("petsHealth") String petsHealth,
//                      @Param("reportDateTime") LocalDateTime reportDateTime
//    );

//    @Query("update Customer c set c.name = :name WHERE c.id = :customerId")
//    void setCustomerName(@Param("customerId") Long id, @Param("name") String name);

//    OwnerReport addOwnerReport(OwnerReport ownerReport);
//
//    Optional<OwnerReport> getOwnerReportById(Integer recordId);
//
//    List<OwnerReport> getAllOwnerReports();
//
//    OwnerReport editOwnerReportById(Integer recordId, OwnerReport ownerReport);
//
//    boolean deleteOwnerReportById(Integer recordId);
//
//    List<OwnerReport> findAllByOwnerIdIsNotNull();
}
