package com.ward_n6.service;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

}
