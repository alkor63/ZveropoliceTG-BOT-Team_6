package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cats")
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("CAT")

public class Cat extends Pet {
    public Cat() {
    }
    public Cat(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread) {
        super(petsType, petsSex, petName, petBirthDay, bread);
    }

}