package com.ward_n6.entity.owners;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


    @Entity
    @NoArgsConstructor
    @Table(name = "pets_owner")
    @Getter
    @Setter
    public abstract class PetsOwner {

        @javax.persistence.Id
        @Column(name = "id",nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "owner_id", insertable = false, updatable = false)
        private Owner Owner;

        @ManyToOne
        @JoinColumn(name = "pet_id", insertable = false, updatable = false)
        private Pet Pet;

        @Column(name = "start_date")
        private LocalDateTime startDate;

        @Column(name = "end_date")
        private LocalDateTime endDate;

        public PetsOwner(Long id, Owner owner, Pet pet) {
            this.id = id;
            Owner = owner;
            Pet = pet;
        }
    }

