package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Pet;

import java.util.HashMap;
import java.util.Map;
// абстрактный класс для реализации конструктора и методов приюта
public abstract class PetShelter {
    private String shelterName;
    private String shelterAdress;
    private Map<Integer, Pet> petsInSelter = new HashMap<Integer, Pet>(); // список питомцев в приюте

    public PetShelter(String shelterName, String shelterAdress, Map<Integer, Pet> petsInSelter) {
        this.shelterName = shelterName;
        this.shelterAdress = shelterAdress;
        this.petsInSelter = petsInSelter;
    }
//
    public PetShelter() {
    }

    public String getShelterAdress() {
        return shelterAdress;
    }

    public PetShelter setShelterAdress(String shelterAdress) {
        this.shelterAdress = shelterAdress;
        return this;
    }

    public Map<Integer, Pet> getPetsInSelter() {
        return petsInSelter;
    }

    public PetShelter setPetsInSelter(Map<Integer, Pet> petsInSelter) {
        this.petsInSelter = petsInSelter;
        return this;
    }


}
