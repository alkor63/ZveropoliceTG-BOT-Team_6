package com.ward_n6.entity.pets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatsCrud extends JpaRepository<Cat_2, Long> {
//    @Query(value = "UPDATE cats_2 SET name = :name WHERE id = :id RETURNING *", nativeQuery = true)
//    List<Base> updateUserName(@Param("id") Long id, @Param("name") String name);
}
