package com.ward_n6;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.entity.pets.Pet;

import java.time.LocalDate;
import java.util.List;

import static com.ward_n6.enums.PetsSex.FEM;
import static com.ward_n6.enums.PetsSex.MALE;
import static com.ward_n6.enums.PetsType.CAT;
import static com.ward_n6.enums.PetsType.DOG;

public class OwnerAndPetConstants {

    public static final String MESSAGE = "Приложение стартовало!";
    public static final String INFO = "Spring-Boot-приложение с функцией telegram-bot, skyPro Java_14, команда Палата_№6 (Анастасия, Алексей, Елизавета, Роман, Тимур), июль-август 2023г.";
    public static final Owner OWNER_1 = new Owner(1000000001L, "Ivan", "Ivanov", "+79099999999");
    public static final Owner OWNER_2 = new Owner(1000000002L, "Boris", "Petrov", "+79090000009");
    public static final List<Owner> LIST_OF_OWNERS = List.of(OWNER_1, OWNER_2);
    public static final Pet PET_1 = new Cat(CAT, FEM, "Маруся", LocalDate.of(2022, 02, 23),
            "метис");
    public static final Pet PET_2 = new Dog(DOG, MALE, "ШАРИК", LocalDate.of(2021, 03, 21),
            "метис");
    public static final List<Pet> LIST_OF_PETS = List.of(PET_1, PET_2);
}
