package com.ward_n6.service;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.reports.OwnerReport;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface OwnerService {

    Owner createOwner(Owner owner);

    List<Owner> getAllOwners();

    Owner getOwnerById(Integer id);

    boolean deleteOwnerById(Integer id);

    Owner editOwnerById(int id, Owner owner)
            throws EntityNotFoundException;
}
