package com.ward_n6.entity.owners;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "owner")
@Getter
@Setter
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // позволяет классам наследникам сопоставляться со своей собственной таблицей
// общий класс для владельцев (нужно ли его делать АБСТРАКТНЫМ?)
public class Owner { // хозяин животного, его свойства

    @Id
    @Column(name = "owner_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        return Objects.equals(getId(), owner.getId()) && Objects.equals(getFirstName(), owner.getFirstName()) && Objects.equals(getLastName(), owner.getLastName()) && Objects.equals(getPhoneNumber(), owner.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber());
    }

    @Override
    public String toString() {
        return "PetOwner{" +
                "ownerId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }


}