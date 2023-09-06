package com.ward_n6.repository;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.reports.OwnerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
