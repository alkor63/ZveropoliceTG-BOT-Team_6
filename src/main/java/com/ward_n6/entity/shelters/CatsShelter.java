package entity.shelters;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.shelters.PetShelter;

import java.util.List;
import java.util.Map;

public class CatsShelter extends PetShelter {
    public CatsShelter() {
        super();
    }

    public CatsShelter(Cat cat, String descriptionCat, String takeCat, List<Cat> cats) {
        this.cat = cat;
        this.descriptionCat = descriptionCat;
        this.takeCat = takeCat;
        this.cats = cats;
    }

    public CatsShelter(String shelterName, String shelterAdress, Map<Integer, Pet> petsInSelter, Cat cat, String descriptionCat, String takeCat, List<Cat> cats) {
        super(shelterName, shelterAdress, petsInSelter);
        this.cat = cat;
        this.descriptionCat = descriptionCat;
        this.takeCat = takeCat;
        this.cats = cats;
    }

    private Cat cat;

    private String descriptionCat = "В приюте для кошек единовременно содержится более 600 кошек самых разных пород и возрастов. Все они привиты, чипированы и стерилизованы. За годы работы мы нашли новый дом более чем для 7400 питомцев и хотим, чтобы на улицах не было бездомных животных."; //описание приюта для кошек
    private String takeCat = getTakePet() +
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
