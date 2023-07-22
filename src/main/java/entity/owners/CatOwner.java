package entity.owners;

import entity.pets.Pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@Entity
@Table(name = "cat_owner")

public class CatOwner  extends PetOwner {
    public CatOwner() {
    }

    public CatOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
    }
}
