package com.ward_n6.repository;

import com.ward_n6.entity.owners.PetsOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsOwnerRepository extends JpaRepository<PetsOwner, Long> {
}
