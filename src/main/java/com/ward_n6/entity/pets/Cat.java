package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "cats")
@PrimaryKeyJoinColumn(name = "CAT")
@DiscriminatorValue("CAT")

public class Cat extends Pet {
    public Cat() {
    }

    public Cat(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, int petAge, String bread, Owner Owner) {
        super(id, petsType, petsSex, petName, petBirthDay, petAge, bread, Owner);
    }

    public Cat(PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, int petAge, String bread, Owner Owner) {
        super(petsType, petsSex, petName, petBirthDay, petAge, bread, Owner);
    }

    public Cat(PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, String bread) {
        super(petsType, petsSex, petName, petBirthDay, bread);
    }

    public Cat(long id, PetsType petsType, PetsSex petsSex, String petName, int petAge, String bread, Owner Owner) {
        super(id, petsType, petsSex, petName, petAge, bread, Owner);
    }
}