package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.PetOwner;
import com.ward_n6.enums.PetsType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "dogs")
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("DOG")

public class Dog extends Pet {

    public Dog(long id, PetsType petsType, String sex, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        super(id, petsType, sex, petName, petBirthDay, petAge, bread, petOwner);
    }

    public Dog() {

    }
}
