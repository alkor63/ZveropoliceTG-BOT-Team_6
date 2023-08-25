package com.ward_n6.entity.pets;

import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "cats")
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("CAT")

public class Cat extends Pet {

    public Cat(Long id, String bread, LocalDate petBirthDay, String petName, PetsSex petsSex, PetsType petsType, long ownerId) {
        super(id, bread, petBirthDay, petName, petsSex, petsType, ownerId);
    }

    public Cat() {
    }

    public Cat(Long id, String bread, LocalDate petBirthDay, String petName, PetsSex petsSex, PetsType petsType) {
        super(id, bread, petBirthDay, petName, petsSex, petsType);
    }
}