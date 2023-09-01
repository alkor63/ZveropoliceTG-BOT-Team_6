package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CatsShelter extends PetShelter {
    public CatsShelter() {
        super();
    }
    public CatsShelter(String shelterName, String shelterAddress, Map<Integer, Pet> petsInSelter) {
        super(shelterName, shelterAddress, petsInSelter);
    }

private String descriptionCat = "В приюте для кошек единовременно содержится более 600 кошек самых разных пород и возрастов. Все они привиты, чипированы и стерилизованы. За годы работы мы нашли новый дом более чем для 7400 питомцев и хотим, чтобы на улицах не было бездомных животных."; //описание приюта для кошек
    @Getter
    private String takeCatRecommendation =
            "Рекомендации по транспортировке :blue_car: : Кошек всех возрастов рекомендуется НЕ кормить перед поездкой. Последний прием пищи за 10-12 часов. Кошки перевозятся в специальных переносках. \n\n Рекомендации по обустройству дома для животного :house_with_garden:: \n Котята: разместить лоток в удобное для вас и доступное для животного место. Также необходимо разместить миски для корма и воды. Для котят мы рекомендуем приобрести когтеточку и некоторое количество игрушек.\nВзрослые кошки : разместить лоток в удобное для вас и доступное для животного место. Также необходимо разместить миски для корма и воды. ";

    private List<Cat> cats;

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }


}
