package entity.owners;

import entity.pets.Pet;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
@Entity
@NoArgsConstructor

@Table(name = "cat_owner") // отдельная таблица

public class CatOwner extends PetOwner {
    public CatOwner(long ownerId, String firstName, String lastName, String phoneNumber) {
    }

    public CatOwner(long ownerId, String firstName, String lastName, List<Pet> petOwnersList) {
        super(ownerId, firstName, lastName, petOwnersList);
    }

    public CatOwner(long ownerId, String firstName, String lastName, String phoneNumber,
                    long petId, LocalDate dateOfPetAdoption, LocalDate endOfOwnerProbationPeriod) {
        super(ownerId, firstName, lastName, phoneNumber, petId, dateOfPetAdoption, endOfOwnerProbationPeriod);
    }
}
