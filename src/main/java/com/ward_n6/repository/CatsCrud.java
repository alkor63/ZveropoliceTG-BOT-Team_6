package com.ward_n6.repository;

import com.ward_n6.entity.pets.Cat_2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatsCrud extends JpaRepository<Cat_2, Long> {
}
