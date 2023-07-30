package entity.shelters;

import entity.pets.Cat;
import entity.pets.Pet;

import java.util.List;
import java.util.Map;

public class CatsShelter extends PetShelter{
    public CatsShelter() {
        super();
    }
    public CatsShelter(String shelterName, String shelterAddress, Map<Integer, Pet> petsInSelter) {
        super(shelterName, shelterAddress, petsInSelter);
    }

    private String adoptCat = getTakePet() +
            "<b>Рекомендации по транспортировке:</b> Кошек всех возрастов рекомендуется НЕ кормить перед поездкой. Последний прием пищи за 10-12 часов. Кошки перевозятся в специальных переносках. " +
            "<b>Рекомендации по обустройству дома для животного:</b> Котята: разместить лоток в удобное для вас и доступное для животного место. Также необходимо разместить миски для корма и воды. Для котят мы рекомендуем приобрести когтеточку и некоторое количество игрушек. " +
            "Взрослые кошки - разместить лоток в удобное для вас и доступное для животного место. Также необходимо разместить миски для корма и воды. " +
            getProhibitedTakePet();

    private List<Cat> cats;

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }


}
