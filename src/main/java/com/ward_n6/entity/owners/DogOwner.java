package com.ward_n6.entity.owners;

//@Entity
//@Table(name = "dog_owner")
//@PrimaryKeyJoinColumn(name = "pet_id")
//@DiscriminatorValue("pet_id")

public class DogOwner extends Owner{

    public DogOwner(Long ownerId, String firstName, String lastName, String phoneNumber) {
        super(ownerId, firstName, lastName, phoneNumber);
    }

    public DogOwner() {
    }
}
