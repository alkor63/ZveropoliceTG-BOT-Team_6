package com.ward_n6.entity.owners;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "owner")
@Builder


//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // позволяет классам наследникам сопоставляться со своей собственной таблицей
// общий класс для владельцев (нужно ли его делать АБСТРАКТНЫМ?)

public class Owner { // хозяин животного, его свойства

    @javax.persistence.Id
    @Id
    @Column(name = "owner_id", nullable = false)
//    @GeneratedValue(strategy = GenerationType.TABLE)
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


//    public void setId(Long id, String source) {
//        if ("bot".equalsIgnoreCase(source)) {
//            // Источник - бот, используем ID чата для присваивания ID сущности
//            this.id = id;
//        } else {
//            // Источник - Swagger или другой, генерируем ID автоматически
//            this.id = generateAutomaticId();
//        }
//    }


    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPhoneNumber());
    }

    @Override
    public String toString() {
        return "ID: " + id + ", \n" +
                "имя: " + firstName + ", \n" +
                "фамилия: " + lastName + ", \n" +
                "номер телефона: " + phoneNumber + ". \n";
    }


}