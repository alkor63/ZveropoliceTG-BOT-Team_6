package com.ward_n6.entity;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Pet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter

public class PetWithOwner {
    private Owner owner;
    private Pet pet;
    private LocalDate startDate;
    private LocalDate endDate;

    public PetWithOwner(Owner owner, Pet pet, LocalDate startDate, LocalDate endDate) {
        this.owner = owner;
        this.pet = pet;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
