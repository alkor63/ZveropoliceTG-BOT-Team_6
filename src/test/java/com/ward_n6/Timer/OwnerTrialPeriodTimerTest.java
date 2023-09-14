package com.ward_n6.Timer;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.owner.OwnerReportRepository;
import com.ward_n6.repository.owner.PetsOwnerRepository;
import com.ward_n6.service.VolunteerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerTrialPeriodTimerTest {

    @MockBean
    private OwnerReportRepository ownerReportRepository;

    @MockBean
    private PetsOwnerRepository petsOwnerRepository;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private Logger logger;

    @InjectMocks
    private OwnerTrialPeriodTimer ownerTrialPeriodTimer;

    @Test
    public void testTask() {
        // Создание фиктивных отчетов
        OwnerReport ownerReport1 = new OwnerReport();
        ownerReport1.setId(1);
        OwnerReport ownerReport2 = new OwnerReport();
        ownerReport2.setId(2);
        List<OwnerReport> allOwnerReports = Arrays.asList(ownerReport1, ownerReport2);
        when(ownerReportRepository.findAll()).thenReturn(allOwnerReports);

        // Создание фиктивных владельцев питомцев
        PetsOwner petsOwner1 = new PetsOwner();
        petsOwner1.setOwnerId(1L);
        petsOwner1.setEndDate(LocalDate.now());
        PetsOwner petsOwner2 = new PetsOwner();
        petsOwner2.setOwnerId(2L);
        petsOwner2.setEndDate(LocalDate.now());
        List<PetsOwner> allPetOwners = Arrays.asList(petsOwner1, petsOwner2);
        when(petsOwnerRepository.findAll()).thenReturn(allPetOwners);

        // Запуск метода task
        ownerTrialPeriodTimer.task();

        // Проверка вызова методов зависимостей
        verify(volunteerService).viewAllReports(LocalDate.now());
        verify(volunteerService, times(2)).reportExpertise(any(OwnerReport.class));
        verify(volunteerService, times(2)).ownersVerdict(any(PetsOwner.class));
        verify(logger, times(4)).info(anyString());
    }

}