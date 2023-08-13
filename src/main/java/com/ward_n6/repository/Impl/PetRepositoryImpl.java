package com.ward_n6.repository.Impl;

import com.ward_n6.entity.pets.Pet;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.PetRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PetRepositoryImpl implements PetRepository {

    private Map<Integer, Pet> petMap = new HashMap<>();
    private int mapId = 0;
    @Override
    public Pet addPet(Pet pet) {
        if (pet.equals(null)) throw new NullPointerException(
                "ОШИБКА при попытке добавить pet=Null в МАПу petMap");
        petMap.putIfAbsent(mapId++, pet);
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
    @Override
    public int getId() {
        return mapId;
    }

}
