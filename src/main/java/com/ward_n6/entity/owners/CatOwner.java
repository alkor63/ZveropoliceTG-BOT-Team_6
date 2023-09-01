package com.ward_n6.entity.owners;


import java.time.LocalDate;

public class CatOwner extends  PetsOwner{

    public CatOwner(long id, Long ownerId, LocalDate startDate, LocalDate endDate) {
        super(id, ownerId, startDate, endDate);
    }

    public CatOwner() {
    }
}
