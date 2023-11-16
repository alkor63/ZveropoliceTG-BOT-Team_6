package com.ward_n6.repository.pets;

import com.ward_n6.entity.pets.Cat;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CatRepository extends PetBaseRepository<Cat> {
}
