package com.ward_n6.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.exception.RecordNotFoundException;
import com.ward_n6.repository.reports.OwnerReportRepository;
import com.ward_n6.service.owners.OwnerReportServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@WebMvcTest(OwnerReportServiceImpl.class)
public class OwnerReportServiceTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OwnerReportRepository ownerReportRepository;

    @Autowired
    private OwnerReportServiceImpl ownerReportService;


    @Test
    public void testGetOwnerReportsList() {
        OwnerReport ownerReport1 = new OwnerReport(1L, 2L, LocalDateTime.now(), PetsType.DOG, true,
                "Pedigree", "good", "Ok", 3L);
        OwnerReport ownerReport2 = new OwnerReport(2L, 3L, LocalDateTime.now(), PetsType.CAT, true,
                "meat", "very good", "Ok", 5L);
        when(ownerReportRepository.findAll()).thenReturn(Arrays.asList(ownerReport1, ownerReport2));
        List<OwnerReport> ownerReportList = ownerReportService.getAllOwnerReports();
        assertEquals(ownerReportList.size(), 2);
        assertEquals(ownerReportList.get(0).getPetsHealth(), "good");
        assertEquals(ownerReportList.get(1).getPetsHealth(), "very good");
        assertEquals(ownerReportList.get(0).getPetsBehavior(), "Ok");
        assertEquals(ownerReportList.get(1).getNutrition(), "meat");
    }

    @Test
    public void testGetOwnerReportById() {
        OwnerReport ownerReport = new OwnerReport(7L, 8L, LocalDateTime.now(), PetsType.DOG, false,
                "fish", "normal", "Ok", 9L);
        when(ownerReportRepository.findById(7L)).thenReturn(Optional.of(ownerReport));
        OwnerReport ownerReportById = ownerReportService.getOwnerReportById(7);
        Assertions.assertNotEquals(ownerReportById, null);
        assertEquals(ownerReportById.getNutrition(), "fish");
        assertEquals(ownerReportById.getId(), 7L);
    }


    @Test
    public void testGetInvalidOwnerReportById() {
        when(ownerReportRepository.findById(17L)).thenThrow(new RecordNotFoundException("OwnerReport Not Found with ID"));
        Exception exception = assertThrows(RecordNotFoundException.class, () -> {
            ownerReportService.getOwnerReportById(17);
        });
        Assertions.assertTrue(exception.getMessage().contains("OwnerReport Not Found with ID"));
    }


    @Test
    public void testDeleteOwnerReport() {
        OwnerReport ownerReport = new OwnerReport(13L, 16L, LocalDateTime.now(), PetsType.DOG, true, "Pedigree", "good", "Ok", 18L);
        when(ownerReportRepository.findById(13L)).thenReturn(Optional.of(ownerReport));
        ownerReportService.deleteOwnerReportById(Math.toIntExact(ownerReport.getId()));
        verify(ownerReportRepository, times(1)).deleteById(ownerReport.getId());
        ArgumentCaptor<Long> ownerReportArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(ownerReportRepository).deleteById(ownerReportArgumentCaptor.capture());
        Long ownerReportIdDeleted = ownerReportArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerReportIdDeleted);
        assertEquals(13L, ownerReportIdDeleted);
    }

    // ************* на ошибку ********
    @Test
    public void testDeleteOwnerReportById_WhenOwnerReportDoesNotExist_ThrowsException() {
        long ownerReportId = 4L;
        Optional<OwnerReport> optionalOwnerReport = Optional.empty();
        when(ownerReportRepository.findById(ownerReportId)).thenReturn(optionalOwnerReport);

        assertThrows(RecordNotFoundException.class, () -> {
            ownerReportService.deleteOwnerReportById(ownerReportId);
        });
    }

    @Test
    public void testAddOwnerReportFromController() {
        long ownerId = 1;
        PetsType petsType = PetsType.DOG;
        boolean photo = true;
        String nutrition = "good";
        String health = "excellent";
        String behavior = "friendly";
        long petId = 1;

        ownerReportService.addOwnerReportFromController(ownerId, petsType, photo,
                nutrition, health, behavior, petId);
        ArgumentCaptor<OwnerReport> ownerReportArgumentCaptor = ArgumentCaptor.forClass(OwnerReport.class);
        verify(ownerReportRepository).save(ownerReportArgumentCaptor.capture());
        OwnerReport ownerReportCreated = ownerReportArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerReportCreated.getId());
        assertEquals("good", ownerReportCreated.getNutrition());
    }

    @Test
    public void editOwnerReportByIdFromControllerTest() {
        OwnerReport ownerReport = new OwnerReport(13L, 16L, LocalDateTime.now(), PetsType.DOG, false,
                "Pedigree", "good", "Ok", 18L);
        long reportId = ownerReport.getId();
        when(ownerReportRepository.findById(reportId)).thenReturn(Optional.of(ownerReport));
        boolean photo = true;
        String nutrition = "good";
        String health = "excellent";
        String behavior = "friendly";
        ownerReportService.editOwnerReportByIdFromController(reportId, photo,
                nutrition, health, behavior);
        when(ownerReportRepository.save(ownerReport)).thenReturn(ownerReport);
        ArgumentCaptor<OwnerReport> ownerReportArgumentCaptor = ArgumentCaptor.forClass(OwnerReport.class);
        verify(ownerReportRepository).save(ownerReportArgumentCaptor.capture());
        OwnerReport ownerReportCreated = ownerReportArgumentCaptor.getValue();
        Assertions.assertNotNull(ownerReportCreated.getId());
        assertEquals("good", ownerReportCreated.getNutrition());
        assertEquals("excellent", ownerReportCreated.getPetsHealth());
        assertEquals("friendly", ownerReportCreated.getPetsBehavior());
        assertEquals(true, ownerReportCreated.isHavePhoto());
    }

    // *********** на ошибку *******
    @Test
    public void testEditOwnerReportByIdFromControllerTest_WhenOwnerReportDoesNotExist_ThrowsException() {
        long ownerReportId = 1;
        boolean photo = true;
        String nutrition = "good";
        String health = "excellent";
        String behavior = "friendly";

        when(ownerReportRepository.findById(ownerReportId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> {
            ownerReportService.editOwnerReportByIdFromController
                    (ownerReportId, photo, nutrition, health, behavior);
        });
    }

                // ************* тест метода репозитория ********
        @Test
        public void testSave () {
            OwnerReport ownerReport = new OwnerReport(13L, 16L, LocalDateTime.now(), PetsType.DOG, false,
                    "Pedigree", "good", "Ok", 18L);
            ownerReportService.save(ownerReport);
            verify(ownerReportRepository, times(1)).save(ownerReport);
        }

    }