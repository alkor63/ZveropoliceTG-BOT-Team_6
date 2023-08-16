package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

//@Entity
    @NoArgsConstructor
    @Getter
    @Setter
    @Table(name = "pets_owner")

    public class PetsOwner {

//    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "end_date", nullable = false)
    private LocalDate dateEnd; // дата, время

    @Column(name = "start_date", nullable = false)
    private LocalDate dateBegin; // дата, время

    @Column(name = "owner")
    private Owner owner; // питание

    @Column(name = "pet")
    private Pet pet; // о здоровье

    public PetsOwner(Owner owner, Pet pet, LocalDate dateBegin, LocalDate dateEnd) {
    }
}

