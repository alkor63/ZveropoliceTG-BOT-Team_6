package com.ward_n6.service.interfaces;

import com.ward_n6.entity.owners.PetsOwner;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public interface PetsOwnerService {
    PetsOwner editDateEndPetsOwnerById(long petsOwnerId, LocalDate newDateEnd)
            throws NotFoundException;

}
