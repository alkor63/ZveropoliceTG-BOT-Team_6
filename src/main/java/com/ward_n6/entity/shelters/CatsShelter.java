package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.listener.MessageStringsConstants;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CatsShelter extends PetShelter {
    MessageStringsConstants messageStringsConstants;
    public CatsShelter() {
        super();
    }
    public CatsShelter(String shelterName, String shelterAddress, Map<Integer, Pet> petsInSelter) {
        super(shelterName, shelterAddress, petsInSelter);
    }

private String descriptionCat = messageStringsConstants.CAT_DESCRIPTION; //описание приюта для кошек
    @Getter
    private String takeCatRecommendation = messageStringsConstants.CAT_RECOMMENDATION; //рекомендации по содержанию кошек

    private List<Cat> cats;

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }


}
