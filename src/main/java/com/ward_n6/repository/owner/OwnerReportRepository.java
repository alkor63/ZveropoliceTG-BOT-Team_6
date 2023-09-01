package com.ward_n6.repository.owner;

import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories
public interface OwnerReportRepository extends JpaRepository<OwnerReport, Long> {


    @Override
    List<OwnerReport> findAll();
}
