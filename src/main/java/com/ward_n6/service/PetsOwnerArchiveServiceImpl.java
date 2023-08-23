package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.service.interfaces.PetsOwnerArchiveService;
import javassist.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public class PetsOwnerArchiveServiceImpl implements PetsOwnerArchiveService {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) throws PutToMapException {
        return null;
    }

    @Override
    public PetWithOwner getFromArchivePetWithOwnerById(int recordId) {
        return null;
    }

    @Override
    public List<PetWithOwner> getAllFromArchivePetWithOwner() {
        return null;
    }

    @Override
    public PetWithOwner editArchivePetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException {
        return null;
    }

    @Override
    public boolean deleteAllFromArchive() {
        return false;
    }

    @Override
    public boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException {
        return false;
    }

    @Override
    public PetsOwner editDateEndPetsOwnerById(int petsOwnerId, LocalDate newDateEnd) throws NotFoundException {
        return null;
    }
}
