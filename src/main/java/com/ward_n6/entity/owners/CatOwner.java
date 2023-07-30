package com.ward_n6.entity.owners;

<<<<<<< HEAD
import com.ward_n6.entity.pets.Pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "cat_owner") // отдельная таблица

public class CatOwner  extends PetOwner {
    public CatOwner() {
    }

    public CatOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
    }
}
=======
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
@Entity
@Table(name = "cat_owner") // отдельная таблица
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("pets_type")

public class CatOwner  extends PetOwner {

    public CatOwner() {
    }

    public CatOwner(Long id, String firstName, String lastName, String phoneNumber) {
        super(id, firstName, lastName, phoneNumber);
    }
}
>>>>>>> origin/feture-TimurA
