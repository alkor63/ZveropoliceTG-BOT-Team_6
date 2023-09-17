package com.ward_n6.repository.owner;

import com.ward_n6.entity.owners.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@Service
@EnableJpaRepositories

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
