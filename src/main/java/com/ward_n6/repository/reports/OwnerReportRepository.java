package com.ward_n6.repository.reports;

import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
@EnableJpaRepositories
@Service
public interface OwnerReportRepository extends JpaRepository<OwnerReport, Long> {
    @Override
    List<OwnerReport> findAll();
}
