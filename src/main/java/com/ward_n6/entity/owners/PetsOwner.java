package com.ward_n6.entity.owners;

import lombok.*;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


    @Getter
    @Setter
    @AllArgsConstructor
//    @Builder
    @Entity
    @Table(name = "pets_owner")
    public class PetsOwner {

    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "end_date", nullable = false)
    private LocalDate dateEnd; // дата, время

    @Column(name = "start_date", nullable = false)
    private LocalDate dateBegin; // дата, время

    @Column(name = "owner_id",nullable = false)
    private Long ownerId;

    @Column(name = "owner_first_name")
    private String firstName;

    @Column(name = "owner_last_name")
    private String lastName;

    @Column(name = "owner_phone")
    private String phoneNumber;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

    @Column(name = "pet_bread")
    private String bread;

    @Column(name = "pet_birthday")
    private LocalDate petBirthDay;

    @Column(name = "pet_name")
    private String petName;

//    @Column(name = "pet_sex")
//    private PetsSex petsSex;


    public PetsOwner(LocalDate dateEnd, LocalDate dateBegin, Long ownerId, String firstName, String lastName, String phoneNumber,
                     Long petId, String bread, LocalDate petBirthDay, String petName) {
//                     Long petId, String bread, LocalDate petBirthDay, String petName, PetsSex petsSex) {
        this.dateEnd = dateEnd;
        this.dateBegin = dateBegin;
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.petId = petId;
        this.bread = bread;
        this.petBirthDay = petBirthDay;
        this.petName = petName;
    }

        public PetsOwner() {
        }
        public PetsOwner(Long id, LocalDate dateEnd, LocalDate dateBegin, Long ownerId, Long petId) {
            this.id = id;
            this.dateEnd = dateEnd;
            this.dateBegin = dateBegin;
            this.ownerId = ownerId;
            this.petId = petId;
        }

        public PetsOwner(LocalDate dateEnd, LocalDate dateBegin, Long ownerId, Long petId) {
            this.dateEnd = dateEnd;
            this.dateBegin = dateBegin;
            this.ownerId = ownerId;
            this.petId = petId;
        }

        public PetsOwner(long id, LocalDate dateEnd) {
            this.id = id;
            this.dateEnd = dateEnd;
        }

        @Override
        public String toString() {
            return "PetsOwner{" +
                    "id=" + id +
                    ", dateEnd=" + dateEnd +
                    ", dateBegin=" + dateBegin +
                    ", ownerId=" + ownerId +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", petId=" + petId +
                    ", bread='" + bread + '\'' +
                    ", petBirthDay=" + petBirthDay +
                    ", petName='" + petName + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PetsOwner petsOwner = (PetsOwner) o;
            return Objects.equals(id, petsOwner.id) && Objects.equals(dateEnd, petsOwner.dateEnd) && Objects.equals(dateBegin, petsOwner.dateBegin) && Objects.equals(ownerId, petsOwner.ownerId) && Objects.equals(firstName, petsOwner.firstName) && Objects.equals(lastName, petsOwner.lastName) && Objects.equals(phoneNumber, petsOwner.phoneNumber) && Objects.equals(petId, petsOwner.petId) && Objects.equals(bread, petsOwner.bread) && Objects.equals(petBirthDay, petsOwner.petBirthDay) && Objects.equals(petName, petsOwner.petName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, dateEnd, dateBegin, ownerId, firstName, lastName, phoneNumber, petId, bread, petBirthDay, petName);
        }
    }

