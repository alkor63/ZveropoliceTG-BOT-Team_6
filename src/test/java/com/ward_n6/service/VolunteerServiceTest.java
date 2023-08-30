package com.ward_n6.service;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.PetRepository;
import com.ward_n6.repository.PetsOwnerRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {
    String firstName = "Саня";

    private final OwnerReport ownerReport1 = new OwnerReport(11L, LocalDateTime.now().minusDays(1), false, "nutrition1", "health1", "behavior1", 1L);
    private final OwnerReport ownerReport2 = new OwnerReport(12L, LocalDateTime.now(), true, "food", "health2", "behavior2", 2L);
    @Mock
    private OwnerReportService ownerReportService;
    @Mock
    private PetRepository petRepository;
    @Mock
    private PetsOwnerRepository petsOwnerRepository;
    @Mock
    private PetsOwnerService petsOwnerService;
    @Spy
    final VolunteerService volunteerMock = spy(new VolunteerService(
            petsOwnerRepository,
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
        LocalTime time = LocalTime.of(21, 0);
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
    void shouldReactionByOwnersVerdict_1()  {
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
        when(volunteerMock.verifyReport(ownerReport1)).thenReturn(111);
        assertEquals(("Вы забыли прислать фото питомца. Заполните, пожалуйста, это поле"),
                volunteer.reportExpertise(ownerReport1));
    }

}