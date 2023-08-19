package com.ward_n6.service;

import com.ward_n6.entity.pets.Dog_2;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.repository.DogsCrud;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
@Service
public class DogService {
    @Resource
    private DogsCrud dogsCrud;

    public DogService(DogsCrud dogsCrud) {
        this.dogsCrud = dogsCrud;
    }


    public void addDog(PetsSex petsSex, Dog_2 dog_2) {
        dog_2.setPetsSex(petsSex);
        dogsCrud.save(dog_2);
    }

    public Dog_2 findDog(long id) {
        Optional<Dog_2> byId = dogsCrud.findById(id);
        return byId.orElse(null);
    }

    public Dog_2 deleteDog(long id) {
        Dog_2 dog = findDog(id);
        if (dog != null) dogsCrud.deleteById(id);
        return dog;
    }


    public List<Dog_2> allDogs() {
        return dogsCrud.findAll();
    }

    public Dog_2 change(long id, Dog_2 dog_2, PetsSex petsSex) {
        dog_2.setId(id);
        dog_2.setPetsSex(petsSex);
        return dogsCrud.save(dog_2);
    }
}
