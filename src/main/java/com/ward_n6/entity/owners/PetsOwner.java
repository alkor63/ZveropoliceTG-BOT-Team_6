package com.ward_n6.entity.owners;
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
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // позволяет классам наследникам сопоставляться со своей собственной таблицей
// общий класс для владельцев (нужно ли его делать АБСТРАКТНЫМ?)
    public abstract class PetsOwner { // хозяин животного, его свойства
// закончился испыталка? что делать
        // через сколько времени удалять из базы
        // нужен ли чёрный список усыновителей

        @javax.persistence.Id
        @Column(name = "owner_id",nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name")
        private String firstName;

        @Column(name = "last_name")
        private String lastName;

        @Column(name = "owner_phone")
        private String phoneNumber;

        @Column(name = "pet_id")
        private Long pet_id;

        @Column(name = "bread")
        private String bread;

        private int petAge;

        @Column(name = "pet_birthday")
        private LocalDateTime petBirthDay;

        @Column(name = "pet_name")
        private String petName;

        @Column(name = "sex")
        private PetsSex petsSex;

        @Column(name = "pets_type", insertable = false, updatable = false)
        private PetsType petsType;

        @Column(name = "start_date")
        private LocalDateTime startDate;

        @Column(name = "end_date")
        private LocalDateTime endDate;

        public PetsOwner(Long id, String firstName, String lastName, String phoneNumber, Long pet_id, String bread, int petAge, LocalDateTime petBirthDay, String petName, PetsSex petsSex, PetsType petsType) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            this.pet_id = pet_id;
            this.bread = bread;
            this.petAge = petAge;
            this.petBirthDay = petBirthDay;
            this.petName = petName;
            this.petsSex = petsSex;
            this.petsType = petsType;
        }

    }

