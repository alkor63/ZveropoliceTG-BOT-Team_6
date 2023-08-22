package com.ward_n6.service;

import com.ward_n6.entity.owners.CatOwner;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.PetRepository;
import com.ward_n6.repository.PetsOwnerArchiveRepository;
import com.ward_n6.repository.PetsOwnerRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.ward_n6.enums.PetsSex.*;
import static com.ward_n6.enums.PetsType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {
    private static int i;
    String firstName = "Саня";

    private static Owner owner = new CatOwner(110L, "testName", "testLastName", "testPhoneNumber");
    private static Owner owner1 = new CatOwner(111L, "testName1", "testLastName1", "testPhoneNumber1");
    private OwnerReport ownerReport1 = new OwnerReport(11L, LocalDateTime.now().minusDays(1), false, "nutrition1", "health1", "bahavior1", 1L);
    private OwnerReport ownerReport2 = new OwnerReport(12L, LocalDateTime.now(), true, "food", "health2", "bahavior2", 2L);
    private PetsOwner petsOwner = new PetsOwner(1l, LocalDate.now(), LocalDate.now().minusDays(45), 1L, 1L);
    private static Pet pet = new Cat(99L, CAT, MALE, "Атос", LocalDate.of(2020, 5, 11), "mixBread");
    @Mock
    private OwnerReportService ownerReportService;
    @Mock
    private PetRepository petRepository;

    @Mock
    private PetsOwnerArchiveRepository petsOwnerArchiveRepository;
    @Mock
    private PetsOwnerRepository petsOwnerRepository;
    @Mock
    private PetsOwnerService petsOwnerService;
    @Spy
    final VolunteerService volunteerMock = spy(new VolunteerService(
            petsOwnerRepository,
            petsOwnerArchiveRepository,
            petsOwnerService,
            ownerReportService,
            petRepository));

    @InjectMocks
    private VolunteerService volunteer;

    PetsOwner petWithOwner = new PetsOwner(2L, LocalDate.now().minusDays(30),
            LocalDate.now(), 2L, 2L);


    @Test
    void shouldCallVolunteerTest() {
        assertEquals("Привет, Саня! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы",
                volunteer.callVolunteer(firstName), "Ждём приветствие");
    }


    @Test
    void viewAllReportsTest() {
        when(ownerReportService.getAllOwnerReports()).thenReturn(Arrays.asList(ownerReport1, ownerReport2));
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalTime time = LocalTime.of(21, 00);
        // запрос на просмотр отчетов может прийти, например, в 21:01
        // чтоб не потерять отчеты, пришедшие с 21:00 по 21:01, время задаём здесь
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now().minusDays(1), time);
        LocalDateTime stopTime = LocalDateTime.of(LocalDate.now(), time);
        String s = "С " + startTime.format(fmt) + " по " + stopTime.format(fmt) +
                " поступило 1 отчетов усыновителей, все отчеты обработаны";
        assertEquals(s, volunteer.viewAllReports(LocalDate.now()));
    }

    @Test
    void endOfProbationPeriodTest() {
        when(ownerReportService.getAllOwnerReports()).thenReturn(Arrays.asList(ownerReport1, ownerReport2));
        assertEquals(1, volunteer.endOfProbationPeriod(petWithOwner));
    }

    @Test
    void shouldReactionByOwnersVerdict_1() throws NotFoundException {
        when(ownerReportService.getAllOwnerReports()).thenReturn(Arrays.asList(ownerReport1, ownerReport2));

        assertEquals("Вы очень редко присылали отчеты Испытательный срок продлен на 30 дней",
                volunteer.ownersVerdict(petWithOwner), "Ждём Verdict");
    }

    @Test
    void shouldBinaryCodeAfterVerifyReport() {
        assertEquals(111, volunteer.verifyReport(ownerReport1));
    }

    @Test
    void shouldStringAnswerOnBinaryCodeAfterVerifyReport() {
        //       VolunteerTest verifyReportMock = new VolunteerTest();
        when(volunteerMock.verifyReport(ownerReport1)).thenReturn(111);
        assertEquals(("Вы забыли прислать фото питомца. Заполните, пожалуйста, это поле"),
                volunteer.reportExpertise(ownerReport1));
    }

}