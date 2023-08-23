package com.ward_n6.entity.pets;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.Period;

/**
 * класс для отработки БД
 * будет удалён в финальной версии
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "cats_2")
public class Cat_2 {
    @Id
    @Column(name = "pet_id")
    private Long id;

    private String bread;


    @Column(name = "pet_birthday")
    private LocalDate petBirthDay;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "sex")
    private String petsSex;

    @Column(name = "owner_id")
    private int ownerId;

    public Cat_2(Long id, String bread, LocalDate petBirthDay, String petName, String petsSex, int ownerId) {
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
               ", возраст, лет: " + getAge() +
                ", порода " + bread + '\'' +
                ", id владелец: " + ownerId + "\n";
    }
}
