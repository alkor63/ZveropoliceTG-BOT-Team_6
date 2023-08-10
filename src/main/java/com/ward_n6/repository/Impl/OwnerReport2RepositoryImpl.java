package com.ward_n6.repository.Impl;

import com.ward_n6.entity.reports.OwnerReport2;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.repository.OwnerReport2Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnerReport2RepositoryImpl implements OwnerReport2Repository {
    private Map<Integer, OwnerReport2> ownerReportMap = new HashMap<>();
    private int mapId = 0;
    @Override
    public OwnerReport2 addOwnerReport(OwnerReport2 ownerReport2) {
        if (ownerReport2.equals(null)) throw new NullPointerException(
                "ОШИБКА при попытке добавить ownerReport2=Null в МАПу ownerReport2Map");
        ownerReportMap.putIfAbsent(mapId++, ownerReport2);
        return ownerReport2;
    }

    @Override
    public OwnerReport2 getOwnerReportById(int recordId) {
        return ownerReportMap.get(recordId);
    }

    @Override
    public List<OwnerReport2> getAllOwnerReports() {
        return new ArrayList<>(ownerReportMap.values());
    }

    @Override
    public OwnerReport2 editOwnerReportById(int recordId, OwnerReport2 ownerReport2) throws EditMapException {
        if (ownerReportMap.containsKey(recordId)) {
            ownerReportMap.put(recordId, ownerReport2);
            if (!ownerReportMap.get(recordId).equals(ownerReport2)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись отчета о животном c petId = "
                        +ownerReport2.getPetId()+" в МАПе ownerReport2Map под id="+recordId);
            }
            return null;
        }
        return ownerReportMap.get(recordId);
    }

    @Override
    public void deleteAllFromOwnerReport() {
        ownerReportMap.clear();
    }

    @Override
    public boolean deleteOwnerReportById(int recordId) throws DeleteFromMapException {
        if (ownerReportMap.containsKey(recordId)) {
            ownerReportMap.remove(recordId);
            return true;
        }
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись отчета о животном в МАПе ownerReport2Map под id="+recordId);
    }

    @Override
    public int idOwnerReportByValue(OwnerReport2 ownerReport2) {
        for (Map.Entry<Integer, OwnerReport2> entry : ownerReportMap.entrySet())
            if (entry.getValue().equals(ownerReport2)) return entry.getKey();
        return -1;
    }
    @Override
    public int getId() {
        return mapId;
    }


}
