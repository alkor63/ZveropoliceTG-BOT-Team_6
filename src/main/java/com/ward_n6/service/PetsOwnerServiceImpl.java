package com.ward_n6.service;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.repository.owner.PetsOwnerRepository;
import com.ward_n6.service.interfaces.PetsOwnerService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PetsOwnerServiceImpl implements PetsOwnerService {
    private final PetsOwnerRepository petsOwnerRepository;


    public PetsOwnerServiceImpl(PetsOwnerRepository petsOwnerRepository) {
        this.petsOwnerRepository = petsOwnerRepository;
    }

    @Override
    public PetsOwner editDateEndPetsOwnerById(int petsOwnerId, LocalDate newDateEnd)
            throws NotFoundException {
        long longId = petsOwnerId;
        Optional optionalPetsOwner = petsOwnerRepository.findById(longId);
        if (!optionalPetsOwner.isPresent()) {
            throw new NotFoundException("Невозможно изменить запись, т.к. в базе pets_owner нет id = " + petsOwnerId);
        }
        PetsOwner petsOwner = (PetsOwner) optionalPetsOwner.get();
        petsOwner.setEndDate(newDateEnd);
        return petsOwnerRepository.save(petsOwner);
    }




/////************************* методы репозитория **************
    public void save(PetsOwner petsOwner) {
        petsOwnerRepository.save(petsOwner);

    }
}
