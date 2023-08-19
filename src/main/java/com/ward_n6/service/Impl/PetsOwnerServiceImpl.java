package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.repository.PetsOwnerRepository;
import com.ward_n6.service.PetsOwnerService;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;

public class PetsOwnerServiceImpl implements PetsOwnerService {
    private final PetsOwnerRepository petsOwnerRepository;

    public PetsOwnerServiceImpl(PetsOwnerRepository petsOwnerRepository) {
        this.petsOwnerRepository = petsOwnerRepository;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Override
    public PetsOwner addPetsOwner(PetsOwner petsOwner) {
        return petsOwnerRepository.save(petsOwner);
    }

    // ++++++++++++++++++++++++++++++++++++
    @Override
    public List<PetsOwner> getAllPetsOwners() throws NotFoundException {
        List<PetsOwner> allPetsOwners = petsOwnerRepository.findAll();
        if (allPetsOwners.size() > 0) {
            return allPetsOwners;
        }
        throw new NotFoundException("не удалось обнаружить записей в базе pets_owner");
    }

    // ++++++++++++++++++++++++++++++++++++
    @Override
    public PetsOwner getPetsOwnerById(Integer petsOwnerId) throws NotFoundException {
        long longId = petsOwnerId;
        Optional<PetsOwner> optionalPetsOwner = petsOwnerRepository.findById(longId);
        if (optionalPetsOwner.isPresent())
            return optionalPetsOwner.get();
        throw new NotFoundException("В базе pets_owner нет записи с id = " + petsOwnerId);
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public PetsOwner deletePetsOwnerById(Integer petsOwnerId)
            throws NotFoundException {
        long longId = petsOwnerId;
        Optional<PetsOwner> optionalPetsOwner = petsOwnerRepository.findById(longId);
        if (optionalPetsOwner.isPresent()) {
            petsOwnerRepository.deleteById(longId);
            return optionalPetsOwner.get();
        }
        throw new NotFoundException("Невозможно удалить запись, т.к. в базе pets_owner нет id = " + petsOwnerId);
    }

    @Override
    public PetsOwner editPetsOwnerById(int petsOwnerId, PetsOwner petsOwner)
            throws NotFoundException {
        long longId = petsOwnerId;
        Optional optionalPetsOwner = petsOwnerRepository.findById(longId);
        if (!optionalPetsOwner.isPresent()) {
            throw new NotFoundException("Невозможно изменить запись, т.к. в базе pets_owner нет id = " + petsOwnerId);
        }
        PetsOwner existingPetsOwner = (PetsOwner) optionalPetsOwner.get();

        existingPetsOwner.setDateEnd(petsOwner.getDateEnd());
        existingPetsOwner.setDateBegin(petsOwner.getDateBegin());
        existingPetsOwner.setOwner(petsOwner.getOwner());
        existingPetsOwner.setPet(petsOwner.getPet());

        return petsOwnerRepository.save(petsOwner);
    }
}
