package com.ward_n6.service.Impl;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.OwnerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OwnerServiceImpl implements OwnerService {
    private Map<Integer, Owner> ownerMap = new HashMap<>();
    private int mapId = 0;


    @Override
    public int getId() {
        return mapId;
    }

    @Override
    public Owner addOwner(Owner owner) throws PutToMapException {
        ownerMap.putIfAbsent(mapId++, owner);
        if (!ownerMap.get(mapId - 1).equals(owner)) {
            throw new PutToMapException("ОШИБКА при попытке добавить гостя по имени "
                    + owner.getFirstName() + " " + owner.getLastName() + " в МАПу ownerMap");
        }
        return owner;
    }

    @Override
    public Owner getOwnerById(int recordId) {
        return ownerMap.get(recordId);
    }

    @Override
    public List<Owner> getAllOwners() {
        return new ArrayList<>(ownerMap.values());
    }

    @Override
    public Owner editOwnerById(int recordId, Owner owner) throws EditMapException {
        if (ownerMap.containsKey(recordId)) {
            ownerMap.put(recordId, owner);
            if (!ownerMap.get(recordId).equals(owner)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись о госте "
                        + owner.getFirstName() + " " + owner.getLastName() + " в МАПе ownerMap под id=" + recordId);
            }
            return null;
        }
        return ownerMap.get(recordId);
    }

    @Override
    public void deleteAllFromOwner() {
        ownerMap.clear();
    }

    @Override
    public boolean deleteOwnerById(int recordId) throws DeleteFromMapException {
        if (ownerMap.containsKey(recordId)) {
            ownerMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о госте в МАПе ownerMap под id=" + recordId);
    }

    @Override
    public boolean deleteOwnerByValue(Owner owner) throws DeleteFromMapException {
        if (ownerMap.containsValue(owner)) {
            ownerMap.values().remove(owner);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись о госте "
                + owner.getFirstName() + " " + owner.getLastName() + " из МАПы ownerMap");
    }

    @Override
    public int idOwnerByValue(Owner owner) {
        for (Map.Entry<Integer, Owner> entry : ownerMap.entrySet())
            if (entry.getValue().equals(owner)) return entry.getKey();
        return -1;
    }
}
