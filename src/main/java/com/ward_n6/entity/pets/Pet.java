package com.ward_n6.entity.pets;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.enums.PetsSex;
import com.ward_n6.enums.PetsType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/** класс, содержащий общие свойства для кошек и собак и для создания отдельных
 *  для животных - на рассмотреии - сколько нужно таблицБД*/
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
    @GeneratedValue(strategy = GenerationType.AUTO) // стратегия будет выбрана автоматически, так как IDENTITY по умолчанию здесь не компилируется \о/
    private Long id;

    @Column(name = "bread")
    private String bread;

    private int petAge;

    @Column(name = "pet_birthday")
    private LocalDateTime petBirthDay;

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



    public Pet(long id, PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, int petAge, String bread, Owner Owner) {
        this.id = id;
        this.petsSex = petsSex;
        this.petsType = petsType;
        this.petName = petName;
        this.petBirthDay = petBirthDay;
        //  this.petAge = LocalDate.now().getYear() - petBirthDay.getYear();
        this.Owner = Owner;
        this.bread = bread;

    }

    public Pet(PetsType petsType, PetsSex petsSex, String petName, LocalDateTime petBirthDay, int petAge, String bread, Owner Owner) {
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
        this.petAge = LocalDateTime.now().getYear() - petBirthDay.getYear();
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
                ", возраст, лет: " +
                ", порода " + bread + '\'' +
                ", владелец: " + Owner.getId() + " " + Owner.getLastName() + " " +
                Owner.getFirstName() + " " + Owner.getPhoneNumber() + '\'' +
                '}';
    }
    public String reportToString() {
        return "Pet{" +
                "ID " + id +
                " " + petsType.getTitle() +
                ", кличка " + petName + '\'' +
                ", возраст, лет: " + petAge +
                ", порода " + bread + '\'' +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return petAge == pet.petAge && Objects.equals(id, pet.id) && Objects.equals(bread, pet.bread) && Objects.equals(petBirthDay, pet.petBirthDay) && Objects.equals(petName, pet.petName) && petsSex == pet.petsSex && petsType == pet.petsType && Objects.equals(Owner, pet.Owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bread, petAge, petBirthDay, petName, petsSex, petsType, Owner);
    }
}