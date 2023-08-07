package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }


    @Override
    public int getId() {
        return ownerRepository.getId();
    }

    @Override
    public Owner addOwner(Owner owner) throws PutToMapException {
        return ownerRepository.addOwner(owner);
    }

    @Override
    public Owner getOwnerById(int recordId) {
        return ownerRepository.getOwnerById(recordId);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.getAllOwners();
    }

    @Override
    public Owner editOwnerById(int recordId, Owner owner) throws EditMapException {
        return ownerRepository.editOwnerById(recordId, owner);
    }

    @Override
    public void deleteAllFromOwner() {
        ownerRepository.deleteAllFromOwner();
    }

    @Override
    public boolean deleteOwnerById(int recordId) throws DeleteFromMapException {
        return ownerRepository.deleteOwnerById(recordId);
    }

    @Override
    public boolean deleteOwnerByValue(Owner owner) throws DeleteFromMapException {
        return ownerRepository.deleteOwnerByValue(owner);
    }

    @Override
    public int idOwnerByValue(Owner owner) {
        return ownerRepository.idOwnerByValue(owner);
    }
}
