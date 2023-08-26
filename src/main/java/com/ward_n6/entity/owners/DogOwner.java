package com.ward_n6.entity.owners;


import java.time.LocalDate;

public class DogOwner extends PetsOwner{

    public DogOwner(Long id, long ownerId, long petId, LocalDate startDate, LocalDate endDate) {
        super(id, ownerId, petId, startDate, endDate);
    }

    public DogOwner() {
    }
}
