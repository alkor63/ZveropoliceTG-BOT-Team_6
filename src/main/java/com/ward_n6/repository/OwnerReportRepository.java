package com.ward_n6.repository;

import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerReportRepository extends JpaRepository <OwnerReport, Long>{

}
