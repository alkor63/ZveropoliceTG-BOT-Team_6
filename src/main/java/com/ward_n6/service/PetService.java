package com.ward_n6.service;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.EditMapException;
import com.ward_n6.service.Impl.PutToMapException;

import java.util.List;

public interface PetService {
    int getId();

    Pet addPet(Pet pet) throws PutToMapException;

    Pet getFromPetById(int recordId);

    List<Pet> getAllPets();

    Pet editPetById(int recordId, Pet pet) throws EditMapException;

    void deleteAllFromPet();

    boolean deletePetById(int recordId) throws DeleteFromMapException;

    boolean deletePetByValue(Pet pet) throws DeleteFromMapException;

    int idPetByValue(Pet pet);
}
