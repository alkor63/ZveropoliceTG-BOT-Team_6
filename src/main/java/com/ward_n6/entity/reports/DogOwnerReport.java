package com.ward_n6.entity.reports;

<<<<<<< HEAD
import com.ward_n6.entity.pets.Pet;

public class DogOwnerReport extends OwnerReport {
    public DogOwnerReport(Pet pet) {
        super(pet);
    }

    public DogOwnerReport() {
    }
=======
import java.time.LocalDateTime;

public class DogOwnerReport extends OwnerReport {


    public DogOwnerReport() {
    }

    public DogOwnerReport(long id, LocalDateTime reportDateTime, boolean havePhoto, String nutrition, String petsHealth, String petsBehavior, long petId) {
        super(id, reportDateTime, havePhoto, nutrition, petsHealth, petsBehavior, petId);
    }
>>>>>>> origin/feture-TimurA
}
