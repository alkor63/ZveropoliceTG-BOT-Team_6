package com.ward_n6.service.interfaces;

import com.ward_n6.entity.owners.Owner;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public interface OwnerService {
    long getId();

    Owner createOwner(Owner owner);

    List<Owner> getAllOwners();

    Owner findOwnerById(long id);

    boolean deleteOwnerById(long id);

    Owner editOwnerById(long id, Owner owner)
            throws EntityNotFoundException;

}
