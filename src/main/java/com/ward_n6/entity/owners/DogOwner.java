package com.ward_n6.entity.owners;


import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsType;

import java.time.LocalDate;

public class DogOwner extends PetsOwner{

    public DogOwner(Long id, long ownerId, long petId, Owner owner, PetsType petsType, Pet pet, LocalDate startDate, LocalDate endDate) {
        super(id, ownerId, petId, owner, petsType, pet, startDate, endDate);
    }

    public DogOwner() {
    }
}
