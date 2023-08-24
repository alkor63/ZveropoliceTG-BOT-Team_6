package com.ward_n6.entity.owners;

import lombok.*;
import lombok.Builder;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;


    @Getter
    @Setter
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

    @Column(name = "pet_id", nullable = false)
    private Long petId;

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
                    ", petId=" + petId +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PetsOwner petsOwner = (PetsOwner) o;
            return Objects.equals(id, petsOwner.id) && Objects.equals(dateEnd, petsOwner.dateEnd) && Objects.equals(dateBegin, petsOwner.dateBegin) && Objects.equals(ownerId, petsOwner.ownerId) && Objects.equals(petId, petsOwner.petId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, dateEnd, dateBegin, ownerId, petId);
        }
    }

