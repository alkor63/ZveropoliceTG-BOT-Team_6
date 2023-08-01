package com.ward_n6.entity.reports;

import com.ward_n6.entity.pets.Pet;

public class CatOwnerReport extends OwnerReport{
    public CatOwnerReport() {
    }
    public CatOwnerReport(Pet pet) {
        super(pet);
    }
}
