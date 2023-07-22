package entity.pets;

import entity.owners.PetOwner;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "pet_id", nullable = false)
    private long petId;
    protected String typeOfPet;
    @Column(name = "pet_name")
    private String petName;
    @Column(name = "pet_birthday")
    private LocalDateTime petBirthDay;
    @Column(name = "pet_Age")
    private int petAge;
    @Column(name = "bread")
    private String bread;
    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private PetOwner petOwner;


    public Pet(int petId, String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.petId = petId;
        if (typeOfPet.equals("кошка") || typeOfPet.equals("собака")) {
            this.typeOfPet = typeOfPet;
        } else {
            this.typeOfPet = this.getClass().getSimpleName();
        }
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.petOwner = petOwner;
        this.bread = bread;

    }

    public Pet(String typeOfPet, String petName, LocalDateTime petBirthDay, int petAge, String bread, PetOwner petOwner) {
        this.typeOfPet = this.getClass().getSimpleName();
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
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
    public long getPetId() {
        return petId;
    }

    public LocalDateTime getPetBirthDay() {
        return petBirthDay;
    }

    public int getPetAge() {
        return petAge;
    }

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
                "ID " + petId +
                ", животное " + typeOfPet +
                ", кличка " + petName + '\'' +
                ", дата рождения: " + petBirthDay +
                ", возраст, лет: " + petAge +
                ", порода " + bread + '\'' +
                ", владелец: " + petOwner.getOwnerId() + " " + petOwner.getLastName() + " " +
                petOwner.getFirstName() + " " + petOwner.getPhoneNumber() + '\'' +
                '}';
    }


    public Long getId() {
        return id;
    }
}



