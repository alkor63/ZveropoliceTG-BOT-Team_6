package com.ward_n6.entity.people;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.CatOwner;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.service.PetService;
import com.ward_n6.service.PetsOwnerArchiveService;
import com.ward_n6.service.PetsOwnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.ward_n6.enums.PetsSex.*;
import static com.ward_n6.enums.PetsType.*;

class VolunteerTest {

    private static int i;
    String firstName = "Саня";
    private final Volunteer volunteer;
    private final PetService petService;
    private final PetsOwnerService petsOwnerService;
    private final PetsOwnerArchiveService petsOwnerArchiveService;

    public VolunteerTest(Volunteer volunteer, PetService petService, PetsOwnerService petsOwnerService, PetsOwnerArchiveService petsOwnerArchiveService) {
        this.volunteer = volunteer;
        this.petService = petService;
        this.petsOwnerService = petsOwnerService;
        this.petsOwnerArchiveService = petsOwnerArchiveService;
    }

    private static Owner owner = new CatOwner(111L,"testName", "testLastName", "testPhoneNumber");
    private static Pet pet = new Cat(99L, CAT, MALE, "Атос", LocalDate.of(2020, 5, 11), "mixBread");
    PetWithOwner petWithOwner = new PetWithOwner(owner,pet,LocalDate.now().minusDays(30),LocalDate.now());

    @Test
    void shouldCallVolunteer() {
        Assertions.assertEquals("Привет, Саня! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы",
                volunteer.callVolunteer(firstName),"Ждём приветствие");
    }

    @Test
    void shouldCreatePetsOwner() {

//        Assertions.assertEquals("Атос", volunteer.createPetsOwner(owner,pet).getPet().getPetName(), "any message from volunteer.createPetsOwner method");
    }

    @Test
    void removePetWithOwnerToArchive() {
    }

    @Test
    void viewAllReports() {
        Assertions.assertEquals(1,
                volunteer.viewAllReports(LocalDate.of(2023,8,7)));
    }

    @Test
    void endOfProbationPeriod() {
        Assertions.assertEquals(0,volunteer.endOfProbationPeriod(petWithOwner));
    }

    @Test
    void shouldReactionByOwnersVerdict_0() {
        Assertions.assertEquals("За 30 дней Вы ни разу не прислали отчет. Вы должны вернуть животное в приют!",
                volunteer.ownersVerdict(petWithOwner),"Ждём Verdict");
    }
}