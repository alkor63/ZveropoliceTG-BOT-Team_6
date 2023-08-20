package com.ward_n6;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Pet;
import liquibase.pro.packaged.P;

import java.util.List;

import static com.ward_n6.enums.PetsSex.FEM;
import static com.ward_n6.enums.PetsSex.MALE;
import static com.ward_n6.enums.PetsType.CAT;
import static com.ward_n6.enums.PetsType.DOG;

public class OwnerAndPetConstants {

    public static final String MESSAGE = "Приложение стартовало!";
    public static final String INFO = "Spring-Boot-приложение с функцией telegram-bot, skyPro Java_14, команда Палата_№6 (Анастасия, Алексей, Елизавета, Роман, Тимур), июль-август 2023г.";
    public static final Owner OWNER_1 = new Owner(1L, "Ivan", "Ivanov", "+79099999999");
    public static final Owner OWNER_2 = new Owner(2L, "Boris", "Petrov", "+7909888888");
    public static final Owner INCORRECT_OWNER = new Owner(2L, "", "", "");
}
