package com.ward_n6.repository;

import com.ward_n6.entity.owners.OwnerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
@EnableJpaRepositories

public interface ReportRepository extends JpaRepository<OwnerReport, Long> {


    @Override
    List<OwnerReport> findAll();
}
