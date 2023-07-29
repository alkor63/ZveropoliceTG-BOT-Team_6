package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.PetOwner;
import com.ward_n6.enums.PetsType;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

// класс, содержащий общие свойства для кошек и собак и для создания БД
@NoArgsConstructor
//@MappedSuperclass
@Entity
@Table(name = "pets")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public abstract class Pet {

    @javax.persistence.Id
    @Column(name = "pet_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private PetOwner petOwner;
    @Column(name = "sex")
    private String sex;

    public Pet(long id, PetsType petsType, String sex, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.id = id;
        this.sex = sex;
        this.petsType = petsType;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        //  this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.petOwner = petOwner;
        this.bread = bread;

    }

    public Pet(PetsType petsType, String sex, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.petsType = petsType;
        this.sex = sex;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        //    this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.bread = bread;
    }

    public Pet(PetsType petsType, String sex, String petName, LocalDateTime petBirthDay, String bread) {
        this.petsType = petsType;
        this.sex = sex;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;
        this.petOwner = petOwner;
    }

    public Pet(long id, PetsType petsType, String petName, int petAge, String bread, PetOwner petOwner) {
        this.id = id;
        this.petsType = petsType;
        this.petName = petName;
        this.petAge = LocalDateTime.now().getYear() - petBirthDay.getYear();
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
                ", животное " + petsType.getTitle() +
                ", кличка " + petName + '\'' +
                ", дата рождения: " + petBirthDay +
                ", возраст, лет: " +
                ", порода " + bread + '\'' +
                ", владелец: " + petOwner.getId() + " " + petOwner.getLastName() + " " +
                petOwner.getFirstName() + " " + petOwner.getPhoneNumber() + '\'' +
                '}';
    }


}



