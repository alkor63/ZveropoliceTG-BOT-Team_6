package com.ward_n6.entity;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Pet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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

    public PetWithOwner() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetWithOwner that = (PetWithOwner) o;
        return Objects.equals(owner, that.owner) && Objects.equals(pet, that.pet) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, pet, startDate, endDate);
    }
}
