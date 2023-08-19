package com.ward_n6.entity.owners;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.ward_n6.entity.pets.Dog_2;
import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data

@Table(name = "dog_owner") // отдельная таблица
@PrimaryKeyJoinColumn(name = "pets_type")
@DiscriminatorValue("pets_type")

public class DogOwner  extends Owner {
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

    public DogOwner (Long ownerId, String name, String lastName, String phoneNumber) {
        this.ownerId = ownerId;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Владелец собаки" +
                "ID " + id +
                ", имя " + name + '\'' +
                ", фамилия: " + lastName +
                ", номер телефона " + phoneNumber + "\n";
    }


}