package com.ward_n6.service;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.EditMapException;
import com.ward_n6.service.Impl.PutToMapException;

import java.util.List;

public interface OwnerService {
    int getId();

    Owner addOwner(Owner owner) throws PutToMapException;

    // добавление в БД
//    Owner addOwnerToDB(Owner owner);

    Owner getOwnerById(int recordId);

    List<Owner> getAllOwners();

    Owner editOwnerById(int recordId, Owner owner) throws EditMapException;

    void deleteAllFromOwner();

    boolean deleteOwnerById(int recordId) throws DeleteFromMapException;

    boolean deleteOwnerByValue(Owner owner) throws DeleteFromMapException;

    int idOwnerByValue(Owner owner);
}
