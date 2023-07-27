package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "dog_owner")
public class DogOwner extends PetOwner {
    public DogOwner() {
    }

    public DogOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
    }
}
