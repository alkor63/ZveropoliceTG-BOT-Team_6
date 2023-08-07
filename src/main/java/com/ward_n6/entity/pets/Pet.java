package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Column(name = "bread")
    private String bread;

    private int petAge;

    @Column(name = "pet_birthday")
    private LocalDate petBirthDay;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "sex")
    private PetsSex petsSex;

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pets_type", insertable = false, updatable = false)
    protected PetsType petsType;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private Owner Owner;



    public Pet(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread) {
        this.id = id;
        this.petsSex = petsSex;
        this.petsType = petsType;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;

    }

    public Pet(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, int petAge, String bread, Owner Owner) {
        this.petsType = petsType;
        this.petsSex = petsSex;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;
    }

    public Pet(PetsType petsType, PetsSex petsSex, String petName, LocalDate petBirthDay, String bread) {
        this.petsType = petsType;
        this.petsSex = petsSex;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        this.bread = bread;
        this.Owner = Owner;
    }

    public Pet(long id, PetsType petsType, PetsSex petsSex, String petName, int petAge, String bread, Owner Owner) {
        this.id = id;
        this.petsType = petsType;
        this.petsSex = petsSex;
        this.petName = petName;
        this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.bread = bread;
        this.Owner = Owner;
    }


    @Override
    public String toString() {
        return "Pet{" +
                "ID " + id +
                ", животное " + petsType.getTitle() +
                ", кличка " + petName + '\'' +
                ", дата рождения: " + petBirthDay +
                ", порода " + bread + '\'' +
                '}';
    }
    public String reportToString() {
        return "Pet{" +
                "ID " + id +
                ", животное " + petsType.getTitle() +
                ", кличка " + petName + '\'' +
                ", порода " + bread + '\'' +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(bread, pet.bread) && Objects.equals(petBirthDay, pet.petBirthDay)
                && Objects.equals(petName, pet.petName) && petsSex == pet.petsSex && petsType == pet.petsType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bread, petBirthDay, petName, petsSex, petsType);
    }
}