package com.ward_n6.entity.owners;


import java.time.LocalDate;

public class DogOwner extends PetsOwner{


    public DogOwner(long id, Long ownerId, LocalDate startDate, LocalDate endDate) {
        super(id, ownerId, startDate, endDate);
    }

    public DogOwner() {
    }
}
