package com.ward_n6.repository;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;

import java.util.List;

public interface PetRepository {
    Pet addPet(Pet pet) throws PutToMapException;

    Pet getPetById(int recordId);

    List<Pet> getAllPets();

    Pet editPetById(int recordId, Pet pet) throws EditMapException;

    void deleteAllFromPet();

    boolean deletePetById(int recordId) throws DeleteFromMapException;

    boolean deletePetByValue(Pet pet) throws DeleteFromMapException;

    int idPetByValue(Pet pet);

    int getId();
}
