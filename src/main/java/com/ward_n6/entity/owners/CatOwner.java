package com.ward_n6.entity.owners;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ward_n6.enums.PetsSex;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import com.ward_n6.entity.pets.Cat_2;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@NoArgsConstructor
@Data

@Table(name = "cat_owner") // отдельная таблица
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("pets_type")

public class CatOwner  extends Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerId")
    private Long ownerId;

    @Column(name = "owner_firstName")
    private String name;

    @Column(name = "owner_lastname")
    private String lastName;

    @Column(name = "owner_phone")
    private String phoneNumber;

    @Column(name = "pet_id")
    private int id;

    public CatOwner (Long ownerId, String name, String lastName, String phoneNumber) {
        this.ownerId = ownerId;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Владелец кота или кошки" +
                "ID " + id +
                ", имя " + name + '\'' +
                ", фамилия: " + lastName +
                ", номер телефона " + phoneNumber + "\n";
    }


}
