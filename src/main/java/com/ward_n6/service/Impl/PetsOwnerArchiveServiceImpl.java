package com.ward_n6.service.Impl;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.service.interfaces.PetsOwnerArchiveService;
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
        return petWithOwnerMap.get(mapId-1);
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
            return petWithOwnerMap.get(recordId);
        }
        return null;
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
        return false;
    }

}
