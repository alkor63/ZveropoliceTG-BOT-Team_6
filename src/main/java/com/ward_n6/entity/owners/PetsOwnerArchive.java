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
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "pets_owner_archive")

public class PetsOwnerArchive {

    @javax.persistence.Id
    @Column(name = "id", nullable = false)
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "end_date", nullable = false)
    private LocalDate dateEnd; // дата, время

    @Column(name = "start_date", nullable = false)
    private LocalDate dateBegin; // дата, время

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

//    @Column(name = "owner_first_name")
//    private String firstName;
//
//    @Column(name = "owner_last_name")
//    private String lastName;
//
//    @Column(name = "owner_phone")
//    private String phoneNumber;

    @Column(name = "pet_id", nullable = false)
    private Long petId;

//    @Column(name = "pet_bread")
//    private String bread;
//
//    @Column(name = "pet_birthday")
//    private LocalDate petBirthDay;
//
//    @Column(name = "pet_name")
//    private String petName;


    public PetsOwnerArchive(Long id, LocalDate dateEnd, LocalDate dateBegin, Long ownerId, Long pet_id) {
        this.dateEnd = dateEnd;
        this.dateBegin = dateBegin;
        this.ownerId = ownerId;
        this.petId = pet_id;
    }
//
//    public PetsOwnerArchive(LocalDate dateEnd, LocalDate dateBegin, Long ownerId,
//                            String firstName, String lastName, String phoneNumber,
//                            Long pet_id, String bread, LocalDate petBirthDay, String petName) {
//        this.dateEnd = dateEnd;
//        this.dateBegin = dateBegin;
//        this.ownerId = ownerId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.petId = petId;
//        this.bread = bread;
//        this.petBirthDay = petBirthDay;
//        this.petName = petName;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        PetsOwnerArchive that = (PetsOwnerArchive) o;
//        return id == that.id && Objects.equals(dateEnd, that.dateEnd) &&
//                Objects.equals(dateBegin, that.dateBegin) && Objects.equals(ownerId, that.ownerId) &&
//                Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) &&
//                Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(petId, that.petId) &&
//                Objects.equals(bread, that.bread) && Objects.equals(petBirthDay, that.petBirthDay) &&
//                Objects.equals(petName, that.petName);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, dateEnd, dateBegin, ownerId, firstName, lastName, phoneNumber,
//                petId, bread, petBirthDay, petName);
//    }
//
//    @Override
//    public String toString() {
//        return "PetsOwnerArchive{" +
//                "id=" + id +
//                ", dateEnd=" + dateEnd +
//                ", dateBegin=" + dateBegin +
//                ", ownerId=" + ownerId +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", petId=" + petId +
//                ", bread='" + bread + '\'' +
//                ", petBirthDay=" + petBirthDay +
//                ", petName='" + petName + '\'' +
//                '}';
//    }
}

