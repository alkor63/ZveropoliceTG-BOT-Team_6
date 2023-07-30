package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.PetOwner;
<<<<<<< HEAD
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
=======
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

// класс, содержащий общие свойства для кошек и собак и для создания БД
@Getter
@Setter
@NoArgsConstructor
//@MappedSuperclass
@Entity
@Table(name = "pets")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class Pet {

    @javax.persistence.Id
    @Column(name = "pet_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // стратегия будет выбрана автоматически, так как IDENTITY по умолчанию здесь не компилируется \о/
    private Long id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pets_type", insertable = false, updatable = false)
    protected PetsType petsType;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "pet_birthday")
    private LocalDateTime petBirthDay;

    private int petAge;
    @Column(name = "bread")
    private String bread;

>>>>>>> origin/feture-TimurA
    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private PetOwner petOwner;

<<<<<<< HEAD

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
=======
    @Column(name = "sex")
    private PetsSex petsSex;

    public Pet(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.id = id;
        this.petsSex = petsSex;
        this.petsType = petsType;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        //  this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
>>>>>>> origin/feture-TimurA
        this.petOwner = petOwner;
        this.bread = bread;

    }

<<<<<<< HEAD
    public Pet(String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.typeOfPet = this.getClass().getSimpleName();
        this.petName = petName;
        this.petBirthDay = petBirthDay;
    //    this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.bread = bread;
    }

    public Pet(String typeOfPet, String petName, LocalDateTime petBirthDay, String bread) {
        this.typeOfPet = this.getClass().getSimpleName();
=======
    public Pet(PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.petsType = petsType;
        this.petsSex = petsSex;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        //    this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.bread = bread;
    }

    public Pet(PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, String bread) {
        this.petsType = petsType;
        this.petsSex = petsSex;
>>>>>>> origin/feture-TimurA
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;
        this.petOwner = petOwner;
    }

<<<<<<< HEAD
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
=======
    public Pet(long id, PetsType petsType, PetsSex petsSex, String petName, int petAge, String bread, PetOwner petOwner) {
        this.id = id;
        this.petsType = petsType;
        this.petsSex = petsSex;
        this.petName = petName;
        this.petAge = LocalDateTime.now().getYear() - petBirthDay.getYear();
        this.bread = bread;
        this.petOwner = petOwner;
    }
>>>>>>> origin/feture-TimurA


    @Override
    public String toString() {
        return "Pet{" +
                "ID " + id +
<<<<<<< HEAD
                ", животное " + typeOfPet +
                ", кличка " + petName + '\'' +
                ", дата рождения: " + petBirthDay +
                ", возраст, лет: "  +
=======
                ", животное " + petsType.getTitle() +
                ", кличка " + petName + '\'' +
                ", дата рождения: " + petBirthDay +
                ", возраст, лет: " +
>>>>>>> origin/feture-TimurA
                ", порода " + bread + '\'' +
                ", владелец: " + petOwner.getId() + " " + petOwner.getLastName() + " " +
                petOwner.getFirstName() + " " + petOwner.getPhoneNumber() + '\'' +
                '}';
    }
<<<<<<< HEAD


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


=======
    public String reportToString() {
        return "Pet{" +
                "ID " + id +
                ", животное " + petsType.getTitle() +
                ", кличка " + petName + '\'' +
                ", возраст, лет: " + petAge +
                ", порода " + bread + '\'' +
                '}';

    }

>>>>>>> origin/feture-TimurA
}



