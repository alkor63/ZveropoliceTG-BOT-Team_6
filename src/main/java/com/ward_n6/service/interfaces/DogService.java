package com.ward_n6.service.interfaces;

import com.ward_n6.entity.pets.Cat_2;
import com.ward_n6.entity.pets.Dog_2;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.repository.CatsCrud;
import com.ward_n6.repository.DogsCrud;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
@Service
public class DogService {
    @Resource
    private DogsCrud dogsCrud;


    public void addDog(PetsSex petsSex, Dog_2 dog_2) {
        dog_2.setPetsSex(petsSex);
        dogsCrud.save(dog_2);
    }

    public Dog_2 findDog(long id) {
        Optional<Dog_2> byId = dogsCrud.findById(id);
        return byId.orElse(null);
    }

    public String deleteDog(long id) {
        try {
            Dog_2 dog = findDog(id);
            dogsCrud.deleteById(id);
            return dog.getPetName() + " удалена";
        } catch (EmptyResultDataAccessException e) {
            return "Такая собака была удалена ранее или вы ошиблись с поиском.";
        }
    }


    public String allDogs() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Dog_2 dog2 : dogsCrud.findAll()) {
            stringBuilder.append("\n").append(dog2);
        }
        return stringBuilder.toString();
    }

    public Dog_2 change(long id, Dog_2 dog_2, PetsSex petsSex) {
        dog_2.setId(id);
        dog_2.setPetsSex(petsSex);
        return dogsCrud.save(dog_2);
    }
}
