package com.ward_n6.service;

import com.ward_n6.entity.owners.PetsOwner;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface PetsOwnerService {

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
//    PetsOwner addPetsOwner(PetsOwner petsOwner);
//
//    // ++++++++++++++++++++++++++++++++++++
//    List<PetsOwner> getAllPetsOwners() throws NotFoundException;
//
//    // ++++++++++++++++++++++++++++++++++++
//    PetsOwner getPetsOwnerById(Integer petsOwnerId) throws NotFoundException;
//
//    //+++++++++++++++++++++++++++++++++++++++++
//    PetsOwner deletePetsOwnerById(Integer petsOwnerId)
//            throws NotFoundException;
//
    PetsOwner editDateEndPetsOwnerById(int petsOwnerId, LocalDate newDateEnd)
            throws NotFoundException;
}
