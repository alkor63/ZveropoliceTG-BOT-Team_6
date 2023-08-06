package com.ward_n6.repository;

import com.ward_n6.entity.owners.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Override
   List<Owner> findAll();

}
