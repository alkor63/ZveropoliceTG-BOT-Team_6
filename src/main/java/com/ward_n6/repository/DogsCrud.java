package com.ward_n6.repository;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.pets.Dog_2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogsCrud extends JpaRepository<Dog_2, Long> {
}
