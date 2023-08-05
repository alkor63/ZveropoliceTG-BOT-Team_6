package com.ward_n6.service.Impl;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.service.PetService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PetServiceImpl implements PetService {
        private Map<Integer, Pet> petMap = new HashMap<>();
    private int mapId = 0;

    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public Pet addPet(Pet pet) throws PutToMapException {
        petMap.putIfAbsent(mapId++, pet);
        if (!petMap.get(mapId-1).equals(pet)) {
            throw new PutToMapException("ОШИБКА при попытке добавить животное "
                    +pet.getBread()+" "+pet.getPetName()+" в МАПу petMap");
        }
        return pet;
    }

    @Override
    public Pet getPetById(int recordId) {
        return petMap.get(recordId);
    }

    @Override
    public List<Pet> getAllPets() {
        return new ArrayList<>(petMap.values());
    }

    @Override
    public Pet editPetById(int recordId, Pet pet) throws EditMapException {
        if (petMap.containsKey(recordId)) {
            petMap.put(recordId, pet);
            if (!petMap.get(recordId).equals(pet)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись о животном "
                        +pet.getBread()+" "+pet.getPetName()+" в МАПе petMap под id="+recordId);
            }
            return null;
        }
        return petMap.get(recordId);
    }

    @Override
    public void deleteAllFromPet() {
        petMap.clear();
    }

    @Override
    public boolean deletePetById(int recordId) throws DeleteFromMapException {
        if (petMap.containsKey(recordId)) {
            petMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о животном в МАПе petMap под id="+recordId);
    }
    @Override
    public boolean deletePetByValue(Pet pet) throws DeleteFromMapException {
        if (petMap.containsValue(pet)) {
            petMap.values().remove(pet);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о животном "
                +pet.getBread()+" "+pet.getPetName()+" в МАПе petMap");
    }
    @Override
    public int idPetByValue(Pet pet) {
        for (Map.Entry<Integer, Pet> entry : petMap.entrySet())
            if (entry.getValue().equals(pet)) return entry.getKey();
        return -1;
    }
}
