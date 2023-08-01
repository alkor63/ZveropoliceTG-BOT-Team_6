package entity.owners;

import entity.pets.Pet;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor

@Table(name = "dog_owner")
public class DogOwner extends PetOwner {

    public DogOwner(int ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
    }

    public DogOwner(long ownerId, String firstName, String lastName, String phoneNumber,
                    long petId, LocalDate dateOfPetAdoption, LocalDate endOfOwnerProbationPeriod) {
        super(ownerId, firstName, lastName, phoneNumber, petId, dateOfPetAdoption, endOfOwnerProbationPeriod);
    }
}