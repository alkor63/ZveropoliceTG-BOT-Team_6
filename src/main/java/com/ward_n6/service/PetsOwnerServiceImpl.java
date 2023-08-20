package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.PetsOwnerRepository;
import com.ward_n6.service.interfaces.PetsOwnerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetsOwnerServiceImpl implements PetsOwnerService {
    private final PetsOwnerRepository petsOwnerRepository;

    private Map<Integer, PetWithOwner> petWithOwnerMap = new HashMap<>();
    private int mapId = 0;

    public PetsOwnerServiceImpl(PetsOwnerRepository petsOwnerRepository) {
        this.petsOwnerRepository = petsOwnerRepository;
    }

    @Override
    public int getId() {
        return getId();
    }

    @Override
    public PetWithOwner addToPetWithOwner(PetWithOwner petWithOwner) throws PutToMapException {
        return addToPetWithOwner(petWithOwner);
    }

    @Override
    public PetWithOwner getFromPetWithOwnerById(int recordId) {
        return getFromPetWithOwnerById(recordId);
    }

    @Override
    public List<PetWithOwner> getAllFromPetWithOwner() {
        return getAllFromPetWithOwner();
    }

    @Override
    public PetWithOwner editPetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException {
        return editPetWithOwnerById(recordId,petWithOwner);
    }

    @Override
    public boolean deleteAllFromPetWithOwner() {
        deleteAllFromPetWithOwner();
        return false;
    }

    @Override
    public boolean deletePetWithOwnerById(int recordId) throws DeleteFromMapException {
        return deletePetWithOwnerById(recordId);
    }
    @Override
    public boolean deletePetWithOwnerByValue(PetWithOwner petWithOwner) throws DeleteFromMapException {
        return deletePetWithOwnerByValue(petWithOwner);
    }
    @Override
    public int idByValue (PetWithOwner petWithOwner) {
        return idByValue(petWithOwner);
    }




/////************************* методы репозитория **************
    public void save(PetsOwner petsOwner) {
        petsOwnerRepository.save(petsOwner);

    }
}
