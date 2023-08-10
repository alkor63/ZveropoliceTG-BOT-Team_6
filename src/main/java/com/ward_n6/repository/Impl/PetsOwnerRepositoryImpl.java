package com.ward_n6.repository.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.PetsOwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PetsOwnerRepositoryImpl implements PetsOwnerRepository {
    private Map<Integer, PetWithOwner> petWithOwnerMap = new HashMap<>();
    private int mapId = 0;


    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public PetWithOwner addToPetWithOwner(PetWithOwner petWithOwner) {
        if (petWithOwner.equals(null)) throw new NullPointerException(
                "ОШИБКА при попытке добавить petWithOwner=Null в МАПу petWithOwner");
        petWithOwnerMap.putIfAbsent(mapId++, petWithOwner);
        return petWithOwner;
    }

    @Override
    public PetWithOwner getFromPetWithOwnerById(int recordId) {
        return petWithOwnerMap.get(recordId);
    }

    @Override
    public List<PetWithOwner> getAllFromPetWithOwner() {
        return new ArrayList<>(petWithOwnerMap.values());
    }

    @Override
    public PetWithOwner editPetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException {
        if (petWithOwnerMap.containsKey(recordId))
            petWithOwnerMap.put(recordId, petWithOwner);
        if (!petWithOwnerMap.get(recordId).equals(petWithOwner)) {
            throw new EditMapException("ОШИБКА при попытке изменить запись о животном "
                    + petWithOwner.getPet().getBread() + " " + petWithOwner.getPet().getPetName() +
                    "и новом хозяине " + petWithOwner.getOwner().getFirstName() + " "
                    + petWithOwner.getOwner().getLastName() + " в МАПе petWithOwnerMap под id=" + recordId);
        }
        return petWithOwner;
    }

    @Override
    public boolean deleteAllFromPetWithOwner() {
        petWithOwnerMap.clear();
        return true;
    }

    @Override
    public boolean deletePetWithOwnerById(int recordId) throws DeleteFromMapException {
        if (petWithOwnerMap.containsKey(recordId)) {
            petWithOwnerMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись из МАПы petWithOwnerMap под id=" + recordId);
    }

    @Override
    public boolean deletePetWithOwnerByValue(PetWithOwner petWithOwner) throws DeleteFromMapException {
        if (petWithOwnerMap.containsValue(petWithOwner)) {
            petWithOwnerMap.values().remove(petWithOwner);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о животном "
                + petWithOwner.getPet().getBread() + " " + petWithOwner.getPet().getPetName() +
                "и новом хозяине " + petWithOwner.getOwner().getFirstName() + " "
                + petWithOwner.getOwner().getLastName() + " из МАПы petWithOwnerMap");
    }

    @Override
    public int idByValue(PetWithOwner petWithOwner) {
        for (Map.Entry<Integer, PetWithOwner> entry : petWithOwnerMap.entrySet())
            if (entry.getValue().equals(petWithOwner)) return entry.getKey();
        return -1;
    }
}