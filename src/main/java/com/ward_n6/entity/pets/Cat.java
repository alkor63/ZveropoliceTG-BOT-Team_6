package com.ward_n6.entity.pets;

import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Builder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "cats")
@PrimaryKeyJoinColumn(name = "CAT")
@DiscriminatorValue("CAT")
@Builder



public class Cat extends Pet {
    public Cat(Long id, String bread, int petAge, LocalDate petBirthDay, String petName, PetsSex petsSex, PetsType petsType, long ownerId) {
        super(id, bread, petAge, petBirthDay, petName, petsSex, petsType, ownerId);
    }

    public Cat() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}