package com.ward_n6.repository.Impl;

import com.ward_n6.entity.owners.Owner;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class OwnerRepositoryImpl implements OwnerRepository {
    private Map<Integer, Owner> ownerMap = new HashMap<>();
    private int mapId = 0;


    @Override
    public Owner addOwner(Owner owner) {
        if (owner.equals(null)) throw new NullPointerException("ОШИБКА при попытке добавить owner=Null в МАПу ownerMap");
        ownerMap.putIfAbsent(mapId++, owner);
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
    @Override
    public int getId() {
        return mapId;
    }
}
