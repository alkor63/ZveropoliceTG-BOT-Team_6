package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "cat_owner") // отдельная таблица

public class CatOwner  extends PetOwner {
    public CatOwner() {
    }

    public CatOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
    }
}
