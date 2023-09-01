package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Pet;
import lombok.Getter;

import java.util.Map;

@Getter
public class DogsShelter extends PetShelter{
    private String descriptionDog = "Здесь содержатся ~300 бездомных, потерянных или брошенных собак. Все привиты, чипированы и давно мечтают найти себе дом!";
    @Getter
    private String takeDogRecommendation =
            "Рекомендации по транспортировке :blue_car:: Собак всех возрастов рекомендуется НЕ кормить перед поездкой. Последний прием пищи за 10-12 часов. Собаки маленьких пород перевозятся в специальных переносках. Собаки средних и крупных пород перевозятся в специальных авто-гамаках, либо также в переносках. \n\n Рекомендации по обустройству дома для животного:\n Щенки: необходимо обустроить его место (лежанка, коврик) и снабдить его игрушками, в т.ч. «грызунками» (на период прорезывания зубов). Также необходимо разместить миски для корма и воды. \n Взрослые собаки: необходимо обустроить место (лежанка, коврик) и снабдить его игрушками. Также необходимо разместить миски для корма и воды. \n\n Для получения рекомендаций по работе с кинологом, пожалуйста, обращайтесь к Борису  +77894561230. "
            ;
    public DogsShelter(String shelterName, String shelterAddress, Map<Integer, Pet> petsInShelter) {
        super(shelterName, shelterAddress, petsInShelter);
    }

    public DogsShelter() {
    }
}
