package com.ward_n6.repository.pets;

import com.ward_n6.entity.pets.Dog;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface DogRepository extends PetBaseRepository<Dog> {

}
