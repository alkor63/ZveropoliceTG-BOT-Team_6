package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.EditMapException;
import com.ward_n6.service.Impl.PutToMapException;

import java.util.List;

public interface PetsOwnerService {
    int getId();

    PetWithOwner addToPetWithOwner(PetWithOwner petWithOwner) throws PutToMapException;

    PetWithOwner getFromPetWithOwnerById(int recordId);

    List<PetWithOwner> getAllFromPetWithOwner();

    PetWithOwner editPetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException;

    void deleteAllFromPetWithOwner();

    boolean deletePetWithOwnerById(int recordId) throws DeleteFromMapException;

    boolean deletePetWithOwnerByValue(PetWithOwner petWithOwner) throws DeleteFromMapException;
}
