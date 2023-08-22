package com.ward_n6.repository.pets;

import com.ward_n6.entity.pets.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBaseRepository <T extends Pet> extends JpaRepository<T, Long> {
}
