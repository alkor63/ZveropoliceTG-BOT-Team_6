package com.ward_n6.service.interfaces;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import javassist.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface PetsOwnerArchiveService {
    int getId();

    PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) throws PutToMapException;

    PetWithOwner getFromArchivePetWithOwnerById(int recordId);

    List<PetWithOwner> getAllFromArchivePetWithOwner();

    PetWithOwner editArchivePetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException;

    boolean deleteAllFromArchive();

    boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException;

    PetsOwner editDateEndPetsOwnerById(int petsOwnerId, LocalDate newDateEnd)
            throws NotFoundException;
}
