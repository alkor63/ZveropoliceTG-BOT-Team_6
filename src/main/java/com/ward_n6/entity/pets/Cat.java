package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.PetOwner;
import com.ward_n6.enums.PetsType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cats")
@PrimaryKeyJoinColumn(name = "CAT")
@DiscriminatorValue("CAT")

public class Cat extends Pet {
    public Cat() {
    }

    public Cat(long id, PetsType petsType, String sex, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        super(id, petsType, sex, petName, petBirthDay, petAge, bread, petOwner);
    }
}

