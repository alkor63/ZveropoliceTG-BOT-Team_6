package com.ward_n6.repository.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PetsOwnerArchiveRepositoryImpl {
    private Map<Integer, PetWithOwner> petWithOwnerMap = new HashMap<>();
    private int mapId = 0;



    public int getId() {
        return mapId;
    }


    public PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) {
        if (petWithOwner.equals(null)) throw new NullPointerException(
                "ОШИБКА при попытке добавить petWithOwner=Null в архив ownerReportMap");
        petWithOwnerMap.putIfAbsent(mapId++, petWithOwner);
        return petWithOwner;
    }


    public PetWithOwner getFromArchivePetWithOwnerById(int recordId) {
        return petWithOwnerMap.get(recordId);
    }


    public List<PetWithOwner> getAllFromArchivePetWithOwner() {
        return new ArrayList<>(petWithOwnerMap.values());
    }


    public PetWithOwner editArchivePetWithOwnerById(int recordId, PetWithOwner petWithOwner) throws EditMapException {
        if (petWithOwnerMap.containsKey(recordId)) {
            petWithOwnerMap.put(recordId, petWithOwner);
            if (!petWithOwnerMap.get(recordId).equals(petWithOwner)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись о животном "
                        + petWithOwner.getPet().getBread() + " " + petWithOwner.getPet().getPetName() +
                        "и новом хозяине " + petWithOwner.getOwner().getFirstName() + " "
                        + petWithOwner.getOwner().getLastName() + " в АРХИВЕ petWithOwnerMap под id=" + recordId);
            }
            return null;
        }
        return petWithOwner;
    }


    public boolean deleteAllFromArchive() {
        petWithOwnerMap.clear();
        return true;
    }


    public boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException {
        if (petWithOwnerMap.containsKey(recordId)) {
            petWithOwnerMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись из АРХИВА petWithOwnerMap под id=" + recordId);
    }

}
