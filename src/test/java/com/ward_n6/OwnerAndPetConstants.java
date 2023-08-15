package com.ward_n6;

import com.ward_n6.entity.owners.Owner;

import java.util.List;

public class OwnerAndPetConstants {

    public static final String MESSAGE = "���������� ����������!";
    public static final String INFO = "Spring-Boot-���������� � �������� telegram-bot, skyPro Java_14, ������� ������_�6 (���������, �������, ���������, �����, �����), ����-������ 2023�.";
    public static final Owner OWNER_1 = new Owner(1000000001L, "Ivan", "Ivanov", "+79099999999");
    public static final Owner OWNER_2 = new Owner(1000000002L, "Boris", "Petrov", "+79090000009");
    public static final List<Owner> LIST_OF_OWNERS = List.of(OWNER_1, OWNER_2);
//    public static final Pet PET_1 = new Cat(1L, CAT, FEM, "Marusya", LocalDate.of(2019,5,19), "britain");
//    public static final Pet PET_2 = new Dog( 1L, DOG, MALE, "Sharik", LocalDate.of(2022,12,12), "malamut");
//    public static final List <Pet> LIST_OF_PETS = List.of(PET_1, PET_2);
}
