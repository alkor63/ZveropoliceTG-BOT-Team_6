package com.ward_n6.service.interfaces;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;

public interface PetService {


    boolean deletePetById(int recordId) throws DeleteFromMapException;


    Pet getPetById(int recordId);

    boolean deletePetByValue(Pet pet) throws DeleteFromMapException;

    Pet addPet(Pet pet);
}
