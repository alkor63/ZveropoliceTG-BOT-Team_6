package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.EditMapException;
import com.ward_n6.service.Impl.PutToMapException;

import java.util.List;

public interface PetsOwnerArchiveService {
    int getId();

    PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) throws PutToMapException;

    PetWithOwner getFromArchivePetWithOwnerById(int recordId);

    List<PetWithOwner> getAllFromArchivePetWithOwner();

    PetWithOwner editArchivePetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException;

    void deleteAllFromArchive();

    boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException;
}
