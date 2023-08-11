package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;

import java.util.List;

public interface PetsOwnerService {
    int getId();

    PetWithOwner addToPetWithOwner(PetWithOwner petWithOwner) throws PutToMapException;

    PetWithOwner getFromPetWithOwnerById(int recordId);

    List<PetWithOwner> getAllFromPetWithOwner();

    PetWithOwner editPetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException;

    boolean deleteAllFromPetWithOwner();

    boolean deletePetWithOwnerById(int recordId) throws DeleteFromMapException;

    boolean deletePetWithOwnerByValue(PetWithOwner petWithOwner) throws DeleteFromMapException;

    int idByValue(PetWithOwner petWithOwner);
}
