package com.ward_n6.repository;

import com.ward_n6.entity.pets.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatsCrud extends JpaRepository<Cat, Long> {
}
