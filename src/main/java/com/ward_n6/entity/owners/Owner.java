package com.ward_n6.entity.owners;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "owner")


//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // позволяет классам наследникам сопоставляться со своей собственной таблицей
// общий класс для владельцев (нужно ли его делать АБСТРАКТНЫМ?)

public class Owner { // хозяин животного, его свойства
//    @javax.persistence.Id
//    @Column(name = "id", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @javax.persistence.Id
    @Column(name = "owner_id", nullable = false)

    private Long ownerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "owner_phone")
    private String phoneNumber;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owner owner)) return false;
        return Objects.equals(getOwnerId(), owner.getOwnerId()) && Objects.equals(getFirstName(), owner.getFirstName()) && Objects.equals(getLastName(), owner.getLastName()) && Objects.equals(getPhoneNumber(), owner.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOwnerId(), getFirstName(), getLastName(), getPhoneNumber());
    }

    @Override
    public String toString() {
        return "ID: " + ownerId + ", \n" +
                "имя " + firstName + ", \n" +
                "фамилия " + lastName + ", \n" +
                "номер телефона: " + phoneNumber + ". \n";
    }


}