package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.PetsOwnerArchive;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PetsOwnerArchiveService {

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    PetsOwnerArchive addPetsOwnerArchive(PetsOwnerArchive petsOwnerArchive);

    // ++++++++++++++++++++++++++++++++++++
    List<PetsOwnerArchive> getAllPetsOwnerArchives() throws NotFoundException;

    // ++++++++++++++++++++++++++++++++++++
    PetsOwnerArchive getPetsOwnerArchiveById(Integer petsOwnerArchiveId) throws NotFoundException;

    //+++++++++++++++++++++++++++++++++++++++++
    PetsOwnerArchive deletePetsOwnerArchiveById(Integer petsOwnerArchiveId)
            throws NotFoundException;

    PetsOwnerArchive editPetsOwnerArchiveById(int petsOwnerArchiveId, PetsOwnerArchive petsOwnerArchive)
            throws NotFoundException;
}
