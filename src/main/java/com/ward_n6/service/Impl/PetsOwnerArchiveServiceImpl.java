package com.ward_n6.service.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.PetsOwnerArchiveRepository;
import com.ward_n6.service.PetsOwnerArchiveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetsOwnerArchiveServiceImpl implements PetsOwnerArchiveService {
private final PetsOwnerArchiveRepository petsOwnerArchiveRepository;

    public PetsOwnerArchiveServiceImpl(PetsOwnerArchiveRepository petsOwnerArchiveRepository) {
        this.petsOwnerArchiveRepository = petsOwnerArchiveRepository;
    }

    @Override
    public int getId() {
        return petsOwnerArchiveRepository.getId();
    }

    @Override
    public PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) throws PutToMapException {
return petsOwnerArchiveRepository.addToArchivePetWithOwner(petWithOwner);
    }

    @Override
    public PetWithOwner getFromArchivePetWithOwnerById(int recordId) {
        return petsOwnerArchiveRepository.getFromArchivePetWithOwnerById(recordId);
    }

    @Override
    public List<PetWithOwner> getAllFromArchivePetWithOwner() {
        return petsOwnerArchiveRepository.getAllFromArchivePetWithOwner();
    }

    @Override
    public PetWithOwner editArchivePetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException {
return petsOwnerArchiveRepository.editArchivePetWithOwnerById(recordId, petWithOwner);
    }

    @Override
    public boolean deleteAllFromArchive() {
        return petsOwnerArchiveRepository.deleteAllFromArchive();
    }

    @Override
    public boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException {
return petsOwnerArchiveRepository.deleteFromArchivePetWithOwnerById(recordId);
    }

}
