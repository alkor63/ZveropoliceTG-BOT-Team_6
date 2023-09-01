package com.ward_n6.entity.shelters;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.listener.MessageStringsConstants;
import lombok.Getter;

import java.util.Map;

@Getter
public class DogsShelter extends PetShelter{
    MessageStringsConstants messageStringsConstants;
    private String descriptionDog = messageStringsConstants.DOG_DESCRIPTION; //описание приюта для собак
    @Getter
    private String takeDogRecommendation =messageStringsConstants.DOG_RECOMMEDATION; //рекомендации по содержанию собак
    public DogsShelter(String shelterName, String shelterAddress, Map<Integer, Pet> petsInShelter) {
        super(shelterName, shelterAddress, petsInShelter);
    }

    public DogsShelter() {
    }
}
