package com.ward_n6.service.interfaces;

import com.ward_n6.entity.owners.PetsOwner;
import javassist.NotFoundException;

import java.time.LocalDate;

public interface PetsOwnerService {
    PetsOwner editDateEndPetsOwnerById(int petsOwnerId, LocalDate newDateEnd)
            throws NotFoundException;

}
