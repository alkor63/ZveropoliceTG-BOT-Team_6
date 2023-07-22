package entity.reports;

import entity.pets.Pet;

public class CatOwnerReport extends OwnerReport{
    public CatOwnerReport() {
    }
    public CatOwnerReport(Pet pet) {
        super(pet);
    }
}
