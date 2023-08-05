package com.ward_n6.service.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.service.PetsOwnerArchiveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetsOwnerArchiveServiceImpl implements PetsOwnerArchiveService {
    private Map<Integer, PetWithOwner> petWithOwnerMap = new HashMap<>();
    private int mapId = 0;


    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public PetWithOwner addToArchivePetWithOwner(PetWithOwner petWithOwner) throws PutToMapException {
        petWithOwnerMap.putIfAbsent(mapId++, petWithOwner);
        if (!petWithOwnerMap.get(mapId - 1).equals(petWithOwner)) {
            throw new PutToMapException("ОШИБКА при попытке добавить животное "
                    + petWithOwner.getPet().getBread() + " " + petWithOwner.getPet().getPetName() +
                    "и нового хозяина " + petWithOwner.getOwner().getFirstName() + " "
                    + petWithOwner.getOwner().getLastName() + " в АРХИВ petWithOwnerMap");
        }
        return petWithOwner;
    }

    @Override
    public PetWithOwner getFromArchivePetWithOwnerById(int recordId) {
        return petWithOwnerMap.get(recordId);
    }

    @Override
    public List<PetWithOwner> getAllFromArchivePetWithOwner() {
        return new ArrayList<>(petWithOwnerMap.values());
    }

    @Override
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

    @Override
    public void deleteAllFromArchive() {
        petWithOwnerMap.clear();
    }

    @Override
    public boolean deleteFromArchivePetWithOwnerById(int recordId) throws DeleteFromMapException {
        if (petWithOwnerMap.containsKey(recordId)) {
            petWithOwnerMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись из АРХИВА petWithOwnerMap под id=" + recordId);
    }

}
