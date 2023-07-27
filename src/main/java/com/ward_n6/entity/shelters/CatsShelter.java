package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Pet;

import java.util.Map;

public class CatsShelter extends PetShelter{
    public CatsShelter() {
        super();
    }
    public CatsShelter(String shelterName, String shelterAdress, Map<Integer, Pet> petsInSelter) {
        super(shelterName, shelterAdress, petsInSelter);
    }


}
