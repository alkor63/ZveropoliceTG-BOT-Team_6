package com.ward_n6.entity.owners;

<<<<<<< HEAD
import com.ward_n6.entity.pets.Pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "dog_owner")
=======
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "dog_owner")
@PrimaryKeyJoinColumn(name = "pet_id")
@DiscriminatorValue("pet_id")
>>>>>>> origin/feture-TimurA
public class DogOwner extends PetOwner {
    public DogOwner() {
    }

<<<<<<< HEAD
    public DogOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
=======
    public DogOwner(Long id, String firstName, String lastName, String phoneNumber) {
        super(id, firstName, lastName, phoneNumber);
>>>>>>> origin/feture-TimurA
    }
}
