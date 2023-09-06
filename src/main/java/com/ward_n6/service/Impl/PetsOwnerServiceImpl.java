package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.repository.PetsOwnerRepository;
import com.ward_n6.service.PetsOwnerService;
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
        Optional<PetsOwner> optionalPetsOwner = petsOwnerRepository.findById((long) petsOwnerId);
        if (optionalPetsOwner.isEmpty()) {
            throw new NotFoundException("Невозможно изменить запись, т.к. в базе pets_owner нет id = " + petsOwnerId);
        }
        PetsOwner petsOwner = optionalPetsOwner.get();
        petsOwner.setEndDate(newDateEnd);
        return petsOwnerRepository.save(petsOwner);
    }
}
