package com.ward_n6.repository;

import com.ward_n6.entity.pets.Pet;
import javassist.NotFoundException;

import java.util.List;

public interface PetRepository {
    Pet addPet(Pet pet);

    Pet getPetById(int recordId);

    List<Pet> getAllPets();

    Pet editPetById(int recordId, Pet pet) throws NotFoundException;

    void deleteAllFromPet();

    boolean deletePetById(int recordId) throws NotFoundException;

    boolean deletePetByValue(Pet pet) throws NotFoundException;

    int idPetByValue(Pet pet);

    int getId();
}
