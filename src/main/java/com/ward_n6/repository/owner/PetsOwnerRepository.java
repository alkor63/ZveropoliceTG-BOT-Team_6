package com.ward_n6.repository.owner;

import com.ward_n6.entity.owners.PetsOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@EnableJpaRepositories
@Service
public interface PetsOwnerRepository extends JpaRepository<PetsOwner, Long> {

}
