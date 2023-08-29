package com.ward_n6.entity.pets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * класс, содержащий общие свойства для кошек и собак и для создания отдельных
 * для животных - на рассмотреии - сколько нужно таблицБД
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pets")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // каждому наследнику свою таблицу


public abstract class Pet {

    @javax.persistence.Id
    @Column(name = "pet_id", nullable = false)
    @Id
    //   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    // стратегия будет выбрана автоматически, так как IDENTITY по умолчанию здесь не компилируется \о/
    private Long id;

    @Column(name = "bread")
    private String bread;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pet_birthday")
    private LocalDate petBirthDay;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private PetsSex petsSex;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "pets_type")
    private PetsType petsType;

    @Column
   (name = "owner_id")
    private Long ownerId;


    public Pet(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread, Long ownerId) {
        this.id = id;
        this.petsSex = petsSex;
        this.petsType = petsType;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.ownerId = ownerId;
        this.bread = bread;

    }

    public Pet(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread) {
        this.petsType = petsType;
        this.petsSex = petsSex;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;
    }


    private int getAge() {
        return Period.between(petBirthDay, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return
                "ID " + id +
                " животное " + petsType.getTitle() + '\n' +
                " кличка " + petName + '\n' +
                " дата рождения: " + petBirthDay + '\n' +
                " пол: " + petsSex.getTitle() + '\n' +
                " возраст: " + getAge() + '\n' +
                " порода " + bread + '\n' +
                " id владельца: " + ownerId + '}' + '\n';
    }

    public String reportToString() {
        return "Кошка{" +
                "ID " + id +
                " " + petsType.getTitle() + ", \n " +
                " кличка " + petName + '\'' + ", \n" +
                " возраст, лет: " + getAge() + ", \n" +
                " порода " + bread + '\'' +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(bread, pet.bread) && Objects.equals(petBirthDay, pet.petBirthDay) && Objects.equals(petName, pet.petName) && petsSex == pet.petsSex && petsType == pet.petsType && Objects.equals(ownerId, pet.ownerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bread, petBirthDay, petName, petsSex, petsType, ownerId);
    }
}


