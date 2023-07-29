package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "dog_owner")
@PrimaryKeyJoinColumn(name = "pet_id")
@DiscriminatorValue("pet_id")
public class DogOwner extends PetOwner {
    public DogOwner() {
    }

    public DogOwner(Long id, String firstName, String lastName, String phoneNumber, boolean probationPeriodIsPositive, List<Pet> petsOfOwnerList, PetsType petsType) {
        super(id, firstName, lastName, phoneNumber, probationPeriodIsPositive, petsOfOwnerList, petsType);
    }
}
