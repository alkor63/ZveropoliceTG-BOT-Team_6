package com.ward_n6.entity.pets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ward_n6.enums.PetsSex;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;


@Entity
@NoArgsConstructor
@Data

@Table(name = "cats_2")
public class Cat_2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long id;

    private String bread;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pet_birthday")
    private LocalDate petBirthDay;

    @Column(name = "pet_name")
    private String petName;
    @JsonIgnore
    @Column(name = "sex")
    private PetsSex petsSex;

    @Column(name = "owner_id")
    private int ownerId;

    public Cat_2(Long id, String bread, LocalDate petBirthDay, String petName, PetsSex petsSex, int ownerId) {
        this.id = id;
        this.bread = bread;
        this.petBirthDay = petBirthDay;
        this.petName = petName;
        this.petsSex = petsSex;
        this.ownerId = ownerId;
    }

    private int getAge() {
        return Period.between(petBirthDay, LocalDate.now()).getYears();
    }

    public void setPetBirthDay(LocalDate petBirthDay) {
        this.petBirthDay = petBirthDay;
    }

    @Override
    public String toString() {
        return "Кошка" +
                "ID " + id +
                ", кличка " + petName + '\'' +
               ", возраст: " + getAge() +
                ", порода " + bread + '\'' +
                ", пол " + petsSex.getTitle() + '\'' +
                ", id владелец: " + ownerId + "\n";
    }
}
