package com.ward_n6.service.pets;

import com.ward_n6.entity.pets.Dog;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import com.ward_n6.repository.pets.DogRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    @Resource
    private final DogRepository dogsCrud;

    public DogService(DogRepository dogsCrud) {
        this.dogsCrud = dogsCrud;
    }


    public Dog addDog(PetsSex petsSex, PetsType petsType, Dog dog) {
        dog.setPetsType(petsType);
        dog.setPetsSex(petsSex);
        dogsCrud.save(dog);
        return dog;
    }

    public Dog findDog(long id) {
        Optional<Dog> byId = dogsCrud.findById(id);
        return byId.orElse(null);
    }

    public Dog deleteDog(long id) {
        Dog dog = findDog(id);
        if (dog != null) dogsCrud.deleteById(id);
        return dog;
    }


    public List<Dog> allDogs() {
        return dogsCrud.findAll();
    }

    public Dog change(long id, PetsSex petsSex, PetsType petsType, Dog dog) {
        dog.setId(id);
        dog.setPetsSex(petsSex);
        dog.setPetsType(petsType);
        return dogsCrud.save(dog);
    }
}
