package com.ward_n6.repository;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;

import java.util.List;

public interface OwnerRepository {
    Owner addOwner(Owner owner) throws PutToMapException;

    Owner getOwnerById(int recordId);

    List<Owner> getAllOwners();

    Owner editOwnerById(int recordId, Owner owner) throws EditMapException;

    void deleteAllFromOwner();

    boolean deleteOwnerById(int recordId) throws DeleteFromMapException;

    boolean deleteOwnerByValue(Owner owner) throws DeleteFromMapException;

    int idOwnerByValue(Owner owner);

    int getId();

    Owner addOwnerToDB(Owner owner);
}
