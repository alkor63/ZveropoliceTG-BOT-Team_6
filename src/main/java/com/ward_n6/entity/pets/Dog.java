package com.ward_n6.entity.pets;

import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "dogs")
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("DOG")

public class Dog extends Pet {
    public Dog() {
    }

//    public Dog(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, int petAge, String bread, Long Owner) {
//        super(id, petsType, petsSex, petName, petBirthDay, petAge, bread, Owner);
//    }
//
//    public Dog(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, int petAge, String bread, Owner Owner) {
//        super(petsType, petsSex, petName, petBirthDay, petAge, bread, Owner);
//    }

    public Dog(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread) {
        super(petsType, petsSex, petName, petBirthDay, bread);
    }

//    public Dog(long id, PetsType petsType, PetsSex petsSex, String petName, int petAge, String bread, Long Owner) {
//        super(id, petsType, petsSex, petName, petAge, bread, Owner);
//    }
}