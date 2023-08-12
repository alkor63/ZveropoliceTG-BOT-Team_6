package com.ward_n6;

import com.ward_n6.entity.owners.Owner;

import java.util.List;

public class OwnerAndPetConstants {

    public static final String MESSAGE = "Приложение стартовало!";
    public static final String INFO = "Spring-Boot-приложение с функцией telegram-bot, skyPro Java_14, команда Палата_№6 (Анастасия, Алексей, Елизавета, Роман, Тимур), июль-август 2023г.";
    public static final Owner OWNER_1 = new Owner(1000000001L, "Ivan", "Ivanov", "+79099999999");
    public static final Owner OWNER_2 = new Owner(1000000002L, "Boris", "Petrov", "+79090000009);
    public static final Owner OWNER_3 = new Owner(1000000003L, "Gleb", "Sidorov", "+7977777777");
    public static final Owner INCORRECT_OWNER = new Owner(3, "Petya", "Volkov", "89008887766");
    public static final List<Owner> LIST_OF_OWNERS = List.of(OWNER_1, OWNER_2, OWNER_3);

}
