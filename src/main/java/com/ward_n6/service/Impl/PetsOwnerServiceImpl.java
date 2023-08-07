package com.ward_n6.service.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.PetsOwnerRepository;
import com.ward_n6.service.PetsOwnerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class PetsOwnerServiceImpl implements PetsOwnerService {
    private final PetsOwnerRepository petsOwnerRepository;

    public PetsOwnerServiceImpl(PetsOwnerRepository petsOwnerRepository) {
        this.petsOwnerRepository = petsOwnerRepository;
    }

    @Override
    public int getId() {
        return petsOwnerRepository.getId();
    }

    @Override
    public PetWithOwner addToPetWithOwner(PetWithOwner petWithOwner) throws PutToMapException {
        return petsOwnerRepository.addToPetWithOwner(petWithOwner);
    }

    @Override
    public PetWithOwner getFromPetWithOwnerById(int recordId) {
        return petsOwnerRepository.getFromPetWithOwnerById(recordId);
    }

    @Override
    public List<PetWithOwner> getAllFromPetWithOwner() {
        return petsOwnerRepository.getAllFromPetWithOwner();
    }

    @Override
    public PetWithOwner editPetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException {
        return petsOwnerRepository.editPetWithOwnerById(recordId, petWithOwner);
    }

    @Override
    public boolean deleteAllFromPetWithOwner() {
        return petsOwnerRepository.deleteAllFromPetWithOwner();
    }

    @Override
    public boolean deletePetWithOwnerById(int recordId) throws DeleteFromMapException {
        return petsOwnerRepository.deletePetWithOwnerById(recordId);
    }

    @Override
    public boolean deletePetWithOwnerByValue(PetWithOwner petWithOwner) throws DeleteFromMapException {
        return petsOwnerRepository.deletePetWithOwnerByValue(petWithOwner);
    }

    @Override
    public int idByValue(PetWithOwner petWithOwner) {
        return petsOwnerRepository.idByValue(petWithOwner);
    }
}
