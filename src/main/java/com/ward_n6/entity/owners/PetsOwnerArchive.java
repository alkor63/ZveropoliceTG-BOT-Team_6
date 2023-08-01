package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "pets_owner_archive")
@Getter
@Setter
public abstract class PetsOwnerArchive {

    @javax.persistence.Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "pet_id", insertable = false, updatable = false)
    private Pet pet;

    @Column(name = "end_date")
    private LocalDate endDate;

    public PetsOwnerArchive(Owner owner, Pet pet, LocalDate endDate) {
        this.owner = owner;
        this.pet = pet;
        this.endDate = endDate;
    }
}

