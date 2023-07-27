package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.PetOwner;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
@Entity
@Table(name = "dogs")
public class Dog extends Pet {


    public Dog(int petId, String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        super(petId, typeOfPet, petName, petBirthDay, petAge, bread, petOwner);
    }

    public Dog() {

    }
}
