package com.ward_n6.repository.pets;

import com.ward_n6.entity.pets.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PetRepository extends JpaRepository<Pet, Long> {

}
