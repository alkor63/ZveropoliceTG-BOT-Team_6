package com.ward_n6.service;

import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.owner.OwnerReportRepository;
import com.ward_n6.service.interfaces.OwnerReportService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
//@Repository
public class OwnerReportServiceImpl implements OwnerReportService {

        private final OwnerReportRepository ownerReportRepository;

        public OwnerReportServiceImpl(OwnerReportRepository ownerReportRepository) {
            this.ownerReportRepository = ownerReportRepository;
        }


        //++++++++++++++++++++++++++++++++++++++++++++++++++++
        @Override
        public OwnerReport addOwnerReport(OwnerReport ownerReport) {
            return ownerReportRepository.save(ownerReport);
        }

        // ++++++++++++++++++++++++++++++++++++
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
        public boolean deleteOwnerReportById(Integer ownerReportId) {
            long longId = ownerReportId;
            Optional<OwnerReport> optionalOwnerReport = ownerReportRepository.findById(longId);
            if (optionalOwnerReport.isPresent()) {
                ownerReportRepository.deleteById(longId);
                return true;
            } else {
                throwException(String.valueOf(ownerReportId));
//            throw new EntityNotFoundException("Невозможно удалить отчёт, т.к. в базе нет отчёта с id = " + ownerReportId);
                return false;
            }
        }


        @Override
        public OwnerReport editOwnerReportById(int ownerReportId, OwnerReport ownerReport)
                throws EntityNotFoundException {
            long longId = ownerReportId;
            Optional optionalOwnerReport = ownerReportRepository.findById(longId);
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


    //*************************************
    public void save(OwnerReport ownerReport) {
        ownerReportRepository.save(ownerReport);
    }
}
