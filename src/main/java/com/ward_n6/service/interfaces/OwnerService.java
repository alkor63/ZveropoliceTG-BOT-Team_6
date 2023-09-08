package com.ward_n6.service.interfaces;

import com.ward_n6.entity.owners.Owner;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Service
public interface OwnerService {
    int getId();


    Owner createOwner(Owner owner);

    List<Owner> getAllOwners();

    Owner getOwnerById(Integer id);

    boolean deleteOwnerById(Integer id);

    Owner editOwnerById(int id, Owner owner)
            throws EntityNotFoundException;

}
