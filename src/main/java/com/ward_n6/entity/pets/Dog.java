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
//@NoArgsConstructor
//@AllArgsConstructor

public class Dog extends Pet {
    public Dog(Long id, String bread, int petAge, LocalDate petBirthDay, String petName, PetsSex petsSex, PetsType petsType, com.ward_n6.entity.owners.Owner Owner) {
        super(id, bread, petAge, petBirthDay, petName, petsSex, petsType, Owner);
    }

    public Dog() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}