package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.owner.OwnerReportRepository;
import com.ward_n6.service.interfaces.OwnerReportService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Repository

public class OwnerReportServiceImpl implements OwnerReportService {
    private final OwnerReportRepository ownerReportRepository;

    public OwnerReportServiceImpl(OwnerReportRepository ownerReportRepository) {
        this.ownerReportRepository = ownerReportRepository;
    }


    @Override
    public List<OwnerReport> getAllOwnerReports() {
        return ownerReportRepository.findAll();
    }


    @Override
    public OwnerReport getOwnerReportById(Integer ownerReportId) {
        long longId = ownerReportId;
        return ownerReportRepository.findById(longId).orElseThrow(() -> throwException(String.valueOf(ownerReportId)));
    }

    //+++++++++++++++++++++++++++++++++++++++++
    @Override
    public boolean deleteOwnerReportById(long ownerReportId) {

        Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(ownerReportId);
        if (optionalOwnerReport.isPresent()) {
            ownerReportRepository.deleteById(ownerReportId);
            return true;
        } else {
            throwException(String.valueOf(ownerReportId));
//            throw new EntityNotFoundException("Невозможно удалить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
            return false;
        }
    }


    @Override
    public OwnerReport editOwnerReportById(long ownerReportId, OwnerReport ownerReport)
            throws EntityNotFoundException {

        Optional optionalOwnerReport = ownerReportRepository.findById(ownerReportId);
        if (!optionalOwnerReport.isPresent()) {
            throwException(String.valueOf(ownerReportId));
//            throw new EntityNotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
        }
        OwnerReport existingOwnerReport = (OwnerReport) optionalOwnerReport.get();

        existingOwnerReport.setHavePhoto(ownerReport.isHavePhoto());
        existingOwnerReport.setNutrition(ownerReport.getNutrition());
        existingOwnerReport.setPetsBehavior(ownerReport.getPetsBehavior());
        existingOwnerReport.setPetsHealth(ownerReport.getPetsHealth());
        existingOwnerReport.setReportDateTime(ownerReport.getReportDateTime());

        return ownerReportRepository.save(ownerReport);
    }

    private RecordNotFoundException throwException(String value) {
        throw new RecordNotFoundException("OwnerReport Not Found with ID: " + value);
    }
@Override
    public OwnerReport addOwnerReportFromController(long ownerId, PetsType petsType, boolean photo,
                                                    String nutrition, String health, String behavior, long petId){
        OwnerReport ownerReport = new OwnerReport();
        ownerReport.setOwnerId(ownerId);
        ownerReport.setPetsType(petsType);
        ownerReport.setNutrition(nutrition);
        ownerReport.setHavePhoto(photo);
        ownerReport.setReportDateTime(LocalDateTime.now());
        ownerReport.setPetId(petId);
        ownerReport.setPetsBehavior(behavior);
        ownerReport.setPetsHealth(health);
    return   ownerReportRepository.save(ownerReport);
    }

    @Override
    public OwnerReport editOwnerReportByIdFromController(long ownerReportId, boolean photo,
                                                         String nutrition, String health, String behavior)
            throws EntityNotFoundException {

        Optional optionalOwnerReport = ownerReportRepository.findById(ownerReportId);
        if (!optionalOwnerReport.isPresent()) {
            throwException(String.valueOf(ownerReportId));
//            throw new EntityNotFoundException("Невозможно изменить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
        }
        OwnerReport existingOwnerReport = (OwnerReport) optionalOwnerReport.get();

        existingOwnerReport.setHavePhoto(photo);
        existingOwnerReport.setNutrition(nutrition);
        existingOwnerReport.setPetsBehavior(behavior);
        existingOwnerReport.setPetsHealth(health);
        existingOwnerReport.setReportDateTime(LocalDateTime.now());

        return ownerReportRepository.save(existingOwnerReport);
    }

    //****************** репозиторий
    public void save(OwnerReport ownerReport){
        ownerReportRepository.save(ownerReport);
    }
}