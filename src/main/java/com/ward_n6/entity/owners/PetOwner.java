package com.ward_n6.entity.owners;

import com.ward_n6.entity.pets.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "pet_owners")
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // позволяет классам наследникам сопоставляться со своей собственной таблицей
// общий класс для владельцев (нужно ли его делать АБСТРАКТНЫМ?)
public abstract class PetOwner { // хозяин животного, его свойства
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

//    private String eMale;

//    @Column(name = "adoption_start_date")
//    private LocalDateTime dateOfPetAdoption;
//    private LocalDateTime endOfOwnerProbationPeriod;
//    private boolean probationPeriodIsPositive;

    @OneToMany(mappedBy = "petOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> petsOfOwnerList = new ArrayList<>();
//    @Column(name = "pets_type")
//    private PetsType petsType;


    public PetOwner(Long id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }




//    @Override
//    public String toString() {
//        return "PetOwner{" +
//                "ownerId=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", eMale='" + eMale +
//                ", pets=" + petsOfOwnerList + '\'' +
//                '}';
//    }


    public void setId(Long id) {
        this.id = id;
    }


}
