package com.ward_n6.entity.pets;

import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cats")
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("CAT")

public class Cat extends Pet {

    public Cat() {
    }

//    public Cat(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, int petAge, String bread, long Owner) {
//        super(id, petsType, petsSex, petName, petBirthDay, petAge, bread, Owner);
//    }
//
//    public Cat(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, int petAge, String bread, Owner Owner) {
//        super(petsType, petsSex, petName, petBirthDay, petAge, bread, Owner);
//    }

    public Cat(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread) {
        super(petsType, petsSex, petName, petBirthDay, bread);
    }

//    public Cat(long id, PetsType petsType, PetsSex petsSex, String petName, int petAge, String bread, long Owner) {
//        super(id, petsType, petsSex, petName, petAge, bread, Owner);
//    }
}