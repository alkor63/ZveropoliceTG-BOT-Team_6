package com.ward_n6.service;

import com.ward_n6.entity.owners.Owner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface OwnerService {

    Owner createOwner(Owner owner);

    // ++++++++++++++++++++++++++++++++++++
    List<Owner> getAllOwners();

    Owner findOwnerById(Integer ownerId);

    //+++++++++++++++++++++++++++++++++++++++++
    boolean deleteOwnerById(Integer ownerId);

    Owner editOwnerById(int ownerId, Owner owner)
            throws EntityNotFoundException;
}
