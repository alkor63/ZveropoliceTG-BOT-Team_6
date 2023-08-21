package com.ward_n6.repository;

import com.ward_n6.entity.pets.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogsCrud extends JpaRepository<Dog, Long> {
}
