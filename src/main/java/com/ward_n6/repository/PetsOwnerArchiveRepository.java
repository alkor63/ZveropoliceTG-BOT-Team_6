package com.ward_n6.repository;

import com.ward_n6.entity.owners.PetsOwnerArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetsOwnerArchiveRepository extends JpaRepository <PetsOwnerArchive, Long> {
}
