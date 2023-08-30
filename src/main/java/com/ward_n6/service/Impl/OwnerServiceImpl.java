package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.service.OwnerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static jdk.internal.misc.StructureViolationExceptions.throwException;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;
    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner createOwner (Owner owner) {
        return ownerRepository.save(owner);
    }
    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerById(Integer id) {
        long longId = id;
        return ownerRepository.findById(longId).orElseThrow(() -> throwException(String.valueOf(id)));
    }
    @Override
    public boolean deleteOwnerById(Integer id) {
        long longId = id;
        Optional<Owner> optionalOwner = ownerRepository.findById(longId);
        if (optionalOwner.isPresent()) {
            ownerRepository.deleteById(longId);
            return true;
        } else {
            throwException(String.valueOf(id));
            return false;
        }
    }

    @Override
    public Owner editOwnerById(int id, Owner owner)
            throws EntityNotFoundException {
        long longId = id;
        Optional optionalOwner = ownerRepository.findById(longId);
        if (!optionalOwner.isPresent()) {
            throwException(String.valueOf(ownerRepository));
        }
        Owner existingOwner = (Owner) optionalOwner.get();

        existingOwner.setFirstName(owner.getFirstName());
        existingOwner.setLastName(owner.getLastName());
        existingOwner.setPhoneNumber(owner.getPhoneNumber());

        return ownerRepository.save(owner);
    }
    private RecordNotFoundException throwException(String value) {
        throw new RecordNotFoundException("OwnerReport Not Found with ID: " + value);
    }
}
