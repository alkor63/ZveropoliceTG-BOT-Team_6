package com.ward_n6.service.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.service.PetsOwnerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetsOwnerServiceImpl implements PetsOwnerService {
    private Map<Integer, PetWithOwner> petWithOwnerMap = new HashMap<>();
    private int mapId = 0;


    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public PetWithOwner addToPetWithOwner(PetWithOwner petWithOwner) throws PutToMapException {
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
        if (petWithOwnerMap.containsKey(recordId)) {
            petWithOwnerMap.put(recordId, petWithOwner);
            return petWithOwner;
        }
        return null;
    }

    @Override
    public void deleteAllFromPetWithOwner() {
        petWithOwnerMap.clear();
    }

    @Override
    public boolean deletePetWithOwnerById(int recordId) throws DeleteFromMapException {
        if (petWithOwnerMap.containsKey(recordId)) {
            petWithOwnerMap.remove(recordId);
            return true;
        }
        return false;
    }
    @Override
    public boolean deletePetWithOwnerByValue(PetWithOwner petWithOwner) throws DeleteFromMapException {
        if (petWithOwnerMap.containsValue(petWithOwner)) {
            petWithOwnerMap.values().remove(petWithOwner);
            return true;
        }
        return false;
    }

}
