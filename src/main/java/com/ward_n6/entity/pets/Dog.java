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
    public Dog(Long id, String bread, LocalDate petBirthDay, String petName, PetsSex petsSex, PetsType petsType, long ownerId) {
        super(id, bread, petBirthDay, petName, petsSex, petsType, ownerId);
    }

    public Dog() {
    }

    public Dog(Long id, String bread, LocalDate petBirthDay, String petName, PetsSex petsSex, PetsType petsType) {
        super(id, bread, petBirthDay, petName, petsSex, petsType);
    }
}