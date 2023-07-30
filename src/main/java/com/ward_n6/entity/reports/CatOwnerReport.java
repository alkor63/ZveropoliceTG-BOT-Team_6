package com.ward_n6.entity.reports;

<<<<<<< HEAD
import com.ward_n6.entity.pets.Pet;

public class CatOwnerReport extends OwnerReport{
    public CatOwnerReport() {
    }
    public CatOwnerReport(Pet pet) {
        super(pet);
=======
import java.time.LocalDateTime;

public class CatOwnerReport extends OwnerReport{

    public CatOwnerReport() {
    }

    public CatOwnerReport(long id, LocalDateTime reportDateTime, boolean havePhoto, String nutrition, String petsHealth, String petsBehavior, long petId) {
        super(id, reportDateTime, havePhoto, nutrition, petsHealth, petsBehavior, petId);
>>>>>>> origin/feture-TimurA
    }
}
