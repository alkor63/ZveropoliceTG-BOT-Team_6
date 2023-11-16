package com.ward_n6.Controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.repository.reports.OwnerReportRepository;
import com.ward_n6.service.interfaces.OwnerReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.ward_n6.enums.PetsType.DOG;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestExecutionListeners
@ExtendWith(MockitoExtension.class)

@WebMvcTest(OwnerReportController.class)
public class OwnerReportControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    private OwnerReportRepository ownerReportRepository;

    @InjectMocks
    private OwnerReportController ownerReportController;
    @Autowired
    private OwnerReportService ownerReportService;


    // создаем несколько объектов тестируемого класса
    OwnerReport ownerReport1 = new OwnerReport(1, 1, LocalDateTime.now(), DOG, true,
            "Pedigree", "good", "Ok", 1);
    OwnerReport ownerReport2 = new OwnerReport(2, 2, LocalDateTime.now(), DOG, true,
            "meat", "very good", "Ok", 2);
    OwnerReport ownerReport3 = new OwnerReport(3, 3, LocalDateTime.now(), PetsType.CAT, false,
            "fish", "normal", "Ok", 3);
    List<OwnerReport> ownerReports = new ArrayList<>();


    @Test // работает
    public void addOwnerReportTest() throws Exception {
        OwnerReport newOwnerReport = new OwnerReport();
        newOwnerReport.setId(1);
        newOwnerReport.setOwnerId(1);
        newOwnerReport.setPetsType(PetsType.CAT);
        newOwnerReport.setHavePhoto(true);
        newOwnerReport.setNutrition("Good");
        newOwnerReport.setPetsHealth("Excellent");
        newOwnerReport.setPetsBehavior("Friendly");
        newOwnerReport.setPetId(1);
// мокаем репортСервис
        OwnerReportService ownerReportService = Mockito.mock(OwnerReportService.class);

        Mockito.when(ownerReportService.addOwnerReportFromController(1, PetsType.CAT, true,
                        "Good", "Excellent", "Friendly", 1))
                .thenReturn(newOwnerReport);
// добавляем репортСервис
        OwnerReportController ownerReportController = new OwnerReportController(ownerReportService);

        ResponseEntity<OwnerReport> response = ownerReportController.addOwnerReport(1, PetsType.CAT,
                true, "Good", "Excellent", "Friendly", 1);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newOwnerReport, response.getBody());
    }

    @Test
    public void getOwnerReportById() throws Exception { //working

        // Mock the owner report service
        OwnerReportService ownerReportService = Mockito.mock(OwnerReportService.class);
        Mockito.when(ownerReportService.getOwnerReportById(1)).thenReturn(ownerReport1);

        OwnerReportController ownerReportController = new OwnerReportController(ownerReportService);

        ResponseEntity<OwnerReport> response = ownerReportController.getOwnerReportById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ownerReport1, response.getBody());
    }

    @Test
    public void getAllOwnerReportsTest() throws Exception { //рабочий

        ownerReports.add(ownerReport1);
        ownerReports.add(ownerReport2);

        OwnerReportService ownerReportService = Mockito.mock(OwnerReportService.class);
        Mockito.when(ownerReportService.getAllOwnerReports()).thenReturn(ownerReports);

        OwnerReportController ownerReportController = new OwnerReportController(ownerReportService);

        ResponseEntity<List<OwnerReport>> response = ownerReportController.getAllOwnerReports();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ownerReports, response.getBody());
    }

    @Test
    public void deleteOwnerReportByIdTest() throws Exception {

        Integer ownerReportId = 1;
        boolean deleteOwnerReportById = true;

        OwnerReportService ownerReportService = Mockito.mock(OwnerReportService.class);
        Mockito.when(ownerReportService.deleteOwnerReportById(ownerReportId)).thenReturn(deleteOwnerReportById);

        OwnerReportController ownerReportController = new OwnerReportController(ownerReportService);

        ResponseEntity<String> response = ownerReportController.deleteOwnerReportById(ownerReportId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OwnerReport id = " + ownerReportId + "успешно удален из базы", response.getBody());
    }

    @Test
    public void testDeleteOwnerReportById_WithError() {

        long ownerReportId = 1;
        boolean deleteOwnerReportById = false;
        OwnerReportService ownerReportService = Mockito.mock(OwnerReportService.class);
        Mockito.when(ownerReportService.deleteOwnerReportById(ownerReportId)).thenReturn(deleteOwnerReportById);

        OwnerReportController ownerReportController = new OwnerReportController(ownerReportService);


        ResponseEntity<String> result = ownerReportController.deleteOwnerReportById(ownerReportId);


        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Ошибка при попытке удалить запись OwnerReport ID = " + ownerReportId, result.getBody());
        Mockito.verify(ownerReportService, Mockito.times(1)).deleteOwnerReportById(ownerReportId);
    }

    @Test
    public void editOwnerReportByIdTest() throws Exception {

        int ownerReportId = 1;
        boolean photo = true;
        String nutrition = "Good";
        String health = "Excellent";
        String behavior = "Friendly";
        ownerReport1.setHavePhoto(photo);
        ownerReport1.setNutrition(nutrition);
        ownerReport1.setPetsBehavior(behavior);
        ownerReport1.setPetsHealth(health);

        OwnerReportService ownerReportService = Mockito.mock(OwnerReportService.class);

        OwnerReport editedOwnerReport = ownerReport1;
        Mockito.when(ownerReportService.editOwnerReportByIdFromController(ownerReportId, photo, nutrition, health,
                behavior)).thenReturn(editedOwnerReport);

        OwnerReportController ownerReportController = new OwnerReportController(ownerReportService);

        OwnerReport response = ownerReportController.editOwnerReportById(ownerReportId, photo, nutrition, health, behavior);

        assertEquals(ownerReportId, response.getId());
        assertEquals(photo, response.isHavePhoto());
        assertEquals(nutrition, response.getNutrition());
        assertEquals(health, response.getPetsHealth());
        assertEquals(behavior, response.getPetsBehavior());
    }
}