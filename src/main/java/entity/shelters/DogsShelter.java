package entity.shelters;

import entity.pets.Pet;

import java.util.Map;

public class DogsShelter extends PetShelter{
    public DogsShelter(String shelterName, String shelterAdress, Map<Integer, Pet> petsInSelter) {
        super(shelterName, shelterAdress, petsInSelter);
    }

    public DogsShelter() {
    }
}
