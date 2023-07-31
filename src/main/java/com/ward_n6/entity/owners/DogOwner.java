package com.ward_n6.entity.owners;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "dog_owner")
@PrimaryKeyJoinColumn(name = "pet_id")
@DiscriminatorValue("pet_id")
public class DogOwner extends Owner {
    public DogOwner() {
    }

    public DogOwner(Long id, String firstName, String lastName, String phoneNumber) {
        super(id, firstName, lastName, phoneNumber);
    }
}
