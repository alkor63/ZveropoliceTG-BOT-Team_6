package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.PetOwner;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
// класс, содержащий общие свойства для кошек и собак и для создания БД
@NoArgsConstructor
@Entity
@Table(name = "pets")
@Inheritance(strategy = InheritanceType.JOINED) //позволяет классам наследникам сопоставляться со своей собственной таблицей
public abstract class Pet {
    @javax.persistence.Id
    @Id
    @Column(name = "pet_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

     protected String typeOfPet;
    @Column(name = "pet_name")
    private String petName;
    @Column(name = "pet_birthday")
    private LocalDateTime petBirthDay;
//    @Column(name = "pet_Age")
//    private int petAge;
    @Column(name = "bread")
    private String bread;
    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private PetOwner petOwner;


    public Pet(int petId, String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.id = id;
        if (typeOfPet.equals("кошка") || typeOfPet.equals("собака")) {
            this.typeOfPet = typeOfPet;
        } else {
            this.typeOfPet = this.getClass().getSimpleName();
        }
        this.petName = petName;
        this.petBirthDay = petBirthDay;
      //  this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.petOwner = petOwner;
        this.bread = bread;

    }

    public Pet(String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.typeOfPet = this.getClass().getSimpleName();
        this.petName = petName;
        this.petBirthDay = petBirthDay;
    //    this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.bread = bread;
    }

    public Pet(String typeOfPet, String petName, LocalDateTime petBirthDay, String bread) {
        this.typeOfPet = this.getClass().getSimpleName();
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;
        this.petOwner = petOwner;
    }

    // ************** ГЕТТЕРЫ: **********
//    public long getPetId() {
//        return petId;
//    }

    public LocalDateTime getPetBirthDay() {
        return petBirthDay;
    }

//    public int getPetAge() {
//        return petAge;
//    }

    public String getBread() {
        return bread;
    }
    // *************** СЕТТЕРЫ: ********

    public Pet setPetName(String petName) {
        this.petName = petName;
        return this;
    }
    // ************ toString ********


    @Override
    public String toString() {
        return "Pet{" +
                "ID " + id +
                ", животное " + typeOfPet +
                ", кличка " + petName + '\'' +
                ", дата рождения: " + petBirthDay +
                ", возраст, лет: "  +
                ", порода " + bread + '\'' +
                ", владелец: " + petOwner.getId() + " " + petOwner.getLastName() + " " +
                petOwner.getFirstName() + " " + petOwner.getPhoneNumber() + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}



