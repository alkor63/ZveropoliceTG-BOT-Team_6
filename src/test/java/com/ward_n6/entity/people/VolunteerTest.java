package com.ward_n6.entity.people;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.CatOwner;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.service.OwnerReportService;
import com.ward_n6.service.PetService;
import com.ward_n6.service.PetsOwnerArchiveService;
import com.ward_n6.service.PetsOwnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.ward_n6.enums.PetsSex.*;
import static com.ward_n6.enums.PetsType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerTest {
    private static int i;
    String firstName = "Саня";

    private static Owner owner = new CatOwner(110L, "testName", "testLastName", "testPhoneNumber");
    private static Owner owner1 = new CatOwner(111L, "testName1", "testLastName1", "testPhoneNumber1");
    private OwnerReport ownerReport1= new OwnerReport(11L, LocalDateTime.now(), false, "nutrition1", "health1", "bahavior1", 1L);
    private static Pet pet = new Cat(99L, CAT, MALE, "Атос", LocalDate.of(2020, 5, 11), "mixBread");
    private PetsOwnerService petsOwnerService;
    OwnerReportService ownerReportService;
    @Mock
    private PetService petService;
    @Mock
    private PetsOwnerService petsOwnerServiceMock;
    @Mock
    private PetWithOwner petWithOwnerMock;
    @Mock
    private PetsOwnerArchiveService petsOwnerArchiveService;
    @Spy
    final Volunteer volunteerMock = spy(new Volunteer(petsOwnerService, petsOwnerArchiveService, ownerReportService, petService));

    @InjectMocks
    private Volunteer volunteer;

    PetWithOwner petWithOwner = new PetWithOwner(owner1, pet, LocalDate.now().minusDays(30), LocalDate.now());


    @Test
    void shouldCallVolunteer() {
        assertEquals("Привет, Саня! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы",
                volunteer.callVolunteer(firstName), "Ждём приветствие");
    }
//@Test
//public void testPutToMapExceptionFromVolunteer(){
//        assertThrows(PutToMapException.class, () -> petService.addPet(null));
//}
    @Test
    public void shouldAddPetAndOwnerToPetsOwnerDB() throws PutToMapException {
//        when(petWithOwnerMock. = new PetWithOwner()).thenReturn(petWithOwner);
//        when(volunteerMock.petsOwnerService.addToPetWithOwner(petWithOwner)).thenReturn(petWithOwner);
//        assertDoesNotThrow((Executable) new PetWithOwner(owner,pet));
//        verify(new ,atLeast(1));
//             собираем в одну таблицу PetWithOwner усыновителя owner, животное pat
//             и время начала и окончания испытательного срока
    }

    @Test
    void removePetWithOwnerToArchive() {
    }

//    @Test
//    void viewAllReports() {
//        assertEquals(1,
//                volunteer.viewAllReports(LocalDate.of(2023, 8, 7)));
//    }

    @Test
    void endOfProbationPeriod() {
        assertEquals(0, volunteer.endOfProbationPeriod(petWithOwner));
    }

    @Test
    void shouldReactionByOwnersVerdict_0() {
        assertEquals("За 30 дней Вы ни разу не прислали отчет. Вы должны вернуть животное в приют!",
                volunteer.ownersVerdict(petWithOwner), "Ждём Verdict");
    }
    @Test
    void shouldBinaryCodeAfterVerifyReport(){
        assertEquals(111, volunteer.verifyReport(ownerReport1));
    }

    @Test
    void shouldStringAnswerOnBinaryCodeAfterVerifyReport(){
 //       VolunteerTest verifyReportMock = new VolunteerTest();
        when(volunteerMock.verifyReport(ownerReport1)).thenReturn(111);
        assertEquals(("Вы забыли прислать фото питомца. Заполните, пожалуйста, это поле"),
                volunteer.reportExpertise(ownerReport1));
    }

}