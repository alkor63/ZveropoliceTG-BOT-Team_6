package com.ward_n6.entity.pets;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 * класс для отработки БД
 * будет удалён в финальной версии
 */
public interface CatsCrud extends JpaRepository<Cat_2, Long> {
//    @Query(value = "UPDATE cats_2 SET name = :name WHERE id = :id RETURNING *", nativeQuery = true)
//    List<Base> updateUserName(@Param("id") Long id, @Param("name") String name);
}
