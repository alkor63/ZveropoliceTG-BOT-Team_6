package entity.pets;

import entity.owners.PetOwner;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
@Entity
@Table(name = "cats")
public class Cat extends Pet {
    public Cat() {
    }

    public Cat(int petId, String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        super(petId, typeOfPet, petName, petBirthDay, petAge, bread, petOwner);
    }
}
