package com.ward_n6.service.pets;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.service.interfaces.PetService;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.repository.pets.PetBaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Repository
public class PetServiceImpl implements PetService {
    private PetBaseRepository petBaseRepository;

    private CatRepository catRepository;
    private DogRepository dogRepository;


    public PetServiceImpl(PetBaseRepository petBaseRepository) {
        this.petBaseRepository = petBaseRepository;
        this.cat = cat;
        this.dog = dog;
    }

    @Override
    public Pet getPetById(int recordId) {
        return null;
    }

    @Override
    public Pet getPetByIdInt(int recordId) {
        return null;
    }

    @Override
    public List<Pet> getAllPets() {
        return null;
    }

    @Override
    public Pet editPetById(int recordId, Pet pet) throws RecordNotFoundException {
        return null;
    }

    @Override
    public void deleteAllFromPet() {

    }

    @Override
    public boolean deletePetById(int recordId) throws DeleteFromMapException {
        return false;
    }

    @Override
    public boolean deletePetByValue(Pet pet) throws DeleteFromMapException {
        return false;
    }

    @Override
    public int idPetByValue(Pet pet) {
        return 0;
    }
    @Override
    public Pet addPet(Pet pet){
        petBaseRepository.save(pet);
        return pet;
     };


    ///**************************** методы репозитория **************
    //*****************************************************************
    private Pet cat = new Cat();
    private Pet dog = new Dog();

    public Pet getPetById(long petId) {
        return cat = catRepository.getById(petId);
    }

    //************* КОШКИ: ************

    public void saveCat(Cat cat) {
        catRepository.save(cat);
    }


    public Pet getCatById(long petId) {
        return cat = catRepository.getById(petId);
    }


    // ******************* СОБАКИ ****************
    public void saveDog(Dog dog) {
        dogRepository.save(dog);
    }

    public Pet getDogById(long petId) {
        return dog = dogRepository.getById(petId);
    }
}
