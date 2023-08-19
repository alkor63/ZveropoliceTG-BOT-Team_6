package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.PetsOwnerArchive;
import com.ward_n6.repository.PetsOwnerArchiveRepository;
import com.ward_n6.service.PetsOwnerArchiveService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetsOwnerArchiveServiceImpl implements PetsOwnerArchiveService {
private final PetsOwnerArchiveRepository petsOwnerArchiveRepository;

    public PetsOwnerArchiveServiceImpl(PetsOwnerArchiveRepository petsOwnerArchiveRepository) {
        this.petsOwnerArchiveRepository = petsOwnerArchiveRepository;
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public PetsOwnerArchive addPetsOwnerArchive(PetsOwnerArchive petsOwnerArchive) {
        return (PetsOwnerArchive) petsOwnerArchiveRepository.save(petsOwnerArchive);
    }

    // ++++++++++++++++++++++++++++++++++++
    @Override
    public List<PetsOwnerArchive> getAllPetsOwnerArchives() throws NotFoundException {
        List<PetsOwnerArchive> allPetsOwnerArchives = petsOwnerArchiveRepository.findAll();
        if (allPetsOwnerArchives.size() > 0) {
            return allPetsOwnerArchives;
        }
        throw new NotFoundException("не удалось обнаружить записей в базе pets_ownerArchive");
    }

    // ++++++++++++++++++++++++++++++++++++
    @Override
    public PetsOwnerArchive getPetsOwnerArchiveById(Integer petsOwnerArchiveId) throws NotFoundException {
        long longId = petsOwnerArchiveId;
        Optional<PetsOwnerArchive> optionalPetsOwnerArchive = petsOwnerArchiveRepository.findById(longId);
        if (optionalPetsOwnerArchive.isPresent())
            return optionalPetsOwnerArchive.get();
        throw new NotFoundException("В базе pets_ownerArchive нет записи с id = " + petsOwnerArchiveId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public PetsOwnerArchive deletePetsOwnerArchiveById(Integer petsOwnerArchiveId)
            throws NotFoundException {
        long longId = petsOwnerArchiveId;
        Optional<PetsOwnerArchive> optionalPetsOwnerArchive = petsOwnerArchiveRepository.findById(longId);
        if (optionalPetsOwnerArchive.isPresent()) {
            petsOwnerArchiveRepository.deleteById(longId);
            return optionalPetsOwnerArchive.get();
        }
        throw new NotFoundException("Невозможно удалить запись, т.к. в базе pets_ownerArchive нет id = " + petsOwnerArchiveId);
    }

    @Override
    public PetsOwnerArchive editPetsOwnerArchiveById(int petsOwnerArchiveId, PetsOwnerArchive petsOwnerArchive)
            throws NotFoundException {
        long longId = petsOwnerArchiveId;
        Optional optionalPetsOwnerArchive = petsOwnerArchiveRepository.findById(longId);
        if (!optionalPetsOwnerArchive.isPresent()) {
            throw new NotFoundException("Невозможно изменить запись, т.к. в базе pets_owner_archive нет id = " + petsOwnerArchiveId);
        }
        PetsOwnerArchive existingPetsOwnerArchive = (PetsOwnerArchive) optionalPetsOwnerArchive.get();

        existingPetsOwnerArchive.setDateEnd(petsOwnerArchive.getDateEnd());
        existingPetsOwnerArchive.setDateBegin(petsOwnerArchive.getDateBegin());
        existingPetsOwnerArchive.setOwner(petsOwnerArchive.getOwner());
        existingPetsOwnerArchive.setPet(petsOwnerArchive.getPet());

        return (PetsOwnerArchive) petsOwnerArchiveRepository.save(petsOwnerArchive);
    }

}
