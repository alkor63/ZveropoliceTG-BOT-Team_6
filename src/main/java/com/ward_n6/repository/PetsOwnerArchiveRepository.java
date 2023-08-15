package com.ward_n6.repository;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;

import java.util.List;

public interface PetsOwnerArchiveRepository {
    int getId();

    PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) throws PutToMapException;

    PetWithOwner getFromArchivePetWithOwnerById(int recordId);

    List<PetWithOwner> getAllFromArchivePetWithOwner();

    PetWithOwner editArchivePetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException;

    boolean deleteAllFromArchive();

    boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException;
}
