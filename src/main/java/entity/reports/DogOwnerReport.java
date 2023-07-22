package entity.reports;

import entity.pets.Pet;

public class DogOwnerReport extends OwnerReport {
    public DogOwnerReport(Pet pet) {
        super(pet);
    }

    public DogOwnerReport() {
    }
}
