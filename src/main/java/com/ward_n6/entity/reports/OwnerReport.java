package com.ward_n6.entity.reports;

import com.ward_n6.entity.pets.Pet;

import java.util.Objects;

// нужно дклать класс абстрактным???
public class OwnerReport {
    private Pet pet;

    public OwnerReport() {
    }

    public OwnerReport(Pet pet) {
        this.pet = pet;
    }
    // фото
    //рацион
    //здоровье
    //изменение поведения - м.б. null?


    public Pet getPet() {
        return pet;
    }

    public OwnerReport setPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OwnerReport)) return false;
        OwnerReport that = (OwnerReport) o;
        return Objects.equals(getPet(), that.getPet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPet());
    }
}
