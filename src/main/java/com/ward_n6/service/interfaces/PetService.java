package com.ward_n6.service.interfaces;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;

import java.util.List;

public interface PetService {
    int getId();



    Pet getPetById(int recordId);

    Pet getPetByIdInt(int recordId);

    List<Pet> getAllPets();

    Pet editPetById(int recordId, Pet pet) throws EditMapException;

    void deleteAllFromPet();

    boolean deletePetById(int recordId) throws DeleteFromMapException;

    boolean deletePetByValue(Pet pet) throws DeleteFromMapException;

    int idPetByValue(Pet pet);
}
