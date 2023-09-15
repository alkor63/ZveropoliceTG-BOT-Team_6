package com.ward_n6.service.owners;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.owner.OwnerRepository;
import com.ward_n6.service.interfaces.OwnerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service

public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @Override
    public Owner findOwnerById(long id) {
        return ownerRepository.findById(id).orElseThrow(() -> throwException(String.valueOf(id)));
    }

    @Override
    public boolean deleteOwnerById(long id) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);
        if (optionalOwner.isPresent()) {
            ownerRepository.deleteById(id);
            return true;
        } else {
            throwException(String.valueOf(id));
            return false;
        }
    }


    @Override
    public Owner editOwnerById(long id, Owner owner) throws EntityNotFoundException {

        Optional optionalOwner = ownerRepository.findById(id);
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

    // ***************************** для БД *************
    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

    Owner owner;

    public Owner getOwnerById(long ownerId) {
        return owner = ownerRepository.getById(ownerId);
    }

    public void deleteById(long ownerId) {
        ownerRepository.deleteById(ownerId);
    }
}
