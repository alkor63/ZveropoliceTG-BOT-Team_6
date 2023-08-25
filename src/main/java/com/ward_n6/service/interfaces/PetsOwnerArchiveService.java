package com.ward_n6.service.interfaces;

import com.ward_n6.entity.owners.PetsOwnerArchive;
import javassist.NotFoundException;

import java.util.List;

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
