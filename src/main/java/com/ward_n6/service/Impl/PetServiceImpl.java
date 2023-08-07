package com.ward_n6.service.Impl;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.PetRepository;
import com.ward_n6.service.PetService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;


    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public int getId() {
        return petRepository.getId();
    }

    @Override
    public Pet addPet(Pet pet) throws PutToMapException {
        return petRepository.addPet(pet);
    }

    @Override
    public Pet getPetById(int recordId) {
        return petRepository.getPetById(recordId);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.getAllPets();
    }

    @Override
    public Pet editPetById(int recordId, Pet pet) throws EditMapException {
        return petRepository.editPetById(recordId, pet);
    }

    @Override
    public void deleteAllFromPet() {
        petRepository.deleteAllFromPet();
    }

    @Override
    public boolean deletePetById(int recordId) throws DeleteFromMapException {
        return petRepository.deletePetById(recordId);
    }

    @Override
    public boolean deletePetByValue(Pet pet) throws DeleteFromMapException {
        return petRepository.deletePetByValue(pet);
    }

    @Override
    public int idPetByValue(Pet pet) {
        return petRepository.idPetByValue(pet);
    }
}
