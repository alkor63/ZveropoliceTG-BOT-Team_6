package com.ward_n6.repository.Impl;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.DeleteFromMapException;
import com.ward_n6.exception.EditMapException;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerReportRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OwnerReportRepositoryImpl implements OwnerReportRepository {
    private Map<Integer, OwnerReport> ownerReportMap = new HashMap<>();
    private int mapId = 0;
    @Override
    public OwnerReport addOwnerReport(OwnerReport ownerReport) {
        if (ownerReport.equals(null)) throw new NullPointerException(
                "ОШИБКА при попытке добавить ownerReport=Null в МАПу ownerReportMap");
        ownerReportMap.putIfAbsent(mapId++, ownerReport);
        return ownerReport;
    }

    @Override
    public OwnerReport getOwnerReportById(int recordId) {
        return ownerReportMap.get(recordId);
    }

    @Override
    public List<OwnerReport> getAllOwnerReports() {
        return new ArrayList<>(ownerReportMap.values());
    }

    @Override
    public OwnerReport editOwnerReportById(int recordId, OwnerReport ownerReport) throws EditMapException {
        if (ownerReportMap.containsKey(recordId)) {
            ownerReportMap.put(recordId, ownerReport);
            if (!ownerReportMap.get(recordId).equals(ownerReport)) {
                throw new EditMapException("ОШИБКА при попытке изменить запись отчета о животном c petId = "
                        +ownerReport.getPetId()+" в МАПе ownerReportMap под id="+recordId);
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
        throw new DeleteFromMapException("ОШИБКА при попытке удалить запись отчета о животном в МАПе ownerReportMap под id="+recordId);
    }

    @Override
    public int idOwnerReportByValue(OwnerReport ownerReport) {
        for (Map.Entry<Integer, OwnerReport> entry : ownerReportMap.entrySet())
            if (entry.getValue().equals(ownerReport)) return entry.getKey();
        return -1;
    }
    @Override
    public int getId() {
        return mapId;
    }

}
