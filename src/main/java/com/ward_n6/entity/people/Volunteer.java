package entity.people;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwnerArchive;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.service.Impl.DeleteFromMapException;
import com.ward_n6.service.Impl.PutToMapException;
import com.ward_n6.service.PetsOwnerArchiveService;
import com.ward_n6.service.PetsOwnerService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Volunteer {
    // класс с функционалом волонтёра
    // метод добавление усыновителя+животного в БД == createCatsOwner и createDogsOwner
    // метод беседы с усыновителем == callVolunteer
    // номер телефона как ID ???? - нет, только для связи
    // метод просмотр отчётов (точнее подсчета) == viewAllReports
    // Бот отслеживает испытательный срок и если срок подошел к концу - обращается к волонтёру за приговором
    // приговор в методе endOfProbationPeriod
    // вызов этого метода из Бота и его обработка - ниже, в ownersVerdict
    private final PetsOwnerService petsOwnerService;
    private final PetsOwnerArchiveService petsOwnerArchiveService;

    public Volunteer(PetsOwnerService petsOwnerService, PetsOwnerArchiveService petsOwnerArchiveService) {
        this.petsOwnerService = petsOwnerService;
        this.petsOwnerArchiveService = petsOwnerArchiveService;
    }
//    public Volunteer(PetsOwnerArchiveService petsOwnerArchiveService) {
//        this.petsOwnerArchiveService = petsOwnerArchiveService;
//    }
    public String callVolunteer(String firstName) {
        // Волонтёр откликается на просьбу о связи от пользователя с именем firstName
        return "Привет, " + firstName + "! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы";
    }

    public PetWithOwner createPetsOwner(Owner owner, Pet pet) throws PutToMapException {
        // собираем в одну таблицу PetWithOwner усыновителя owner, животное pat
        // и время начала и окончания испытательного срока
        PetWithOwner petWithOwner = new PetWithOwner(owner, pet, LocalDate.now(), LocalDate.now().plusDays(30));
        return petsOwnerService.addToPetWithOwner(petWithOwner);
    }

    public PetWithOwner removePetWithOwnerToArchive(PetWithOwner petWithOwner) throws PutToMapException, DeleteFromMapException {
        // убираем парочку из таблицы PetWithOwner и заносим в одноименный архив
        // после успешного прохождения (окончания) испытательного срока
        petsOwnerArchiveService.addToArchivePetWithOwner(petWithOwner);
        petsOwnerService.deletePetWithOwnerByValue(petWithOwner);
        return petWithOwner; // может лучше return boolean или что-то более полезное?
    }


    public int viewAllReports(LocalDate date) {
        // просмотр всех отчеиов за пршедшие сутки (с 21:00 предыдущего дня по 21:00 дня = date
        int num = 0;
        LocalTime time = LocalTime.of(21, 00);
        // запрос на просмотр отчетов может прийти, например, в 21:01
        // чтоб не потерять отчеты, пришедшие с 21:00 по 21:01, время задаём здесь
        LocalDateTime startTime = LocalDateTime.of(date.minusDays(1), time);
        LocalDateTime stopTime = LocalDateTime.of(date, time).plusNanos(1); // т.к. методы isAfter и isBefore не включают equals
        List<OwnerReport> ownerReportList = new ArrayList<>();
        for (OwnerReport ownerReport : ownerReportList) {
            LocalDateTime dateTime = ownerReport.getReportDateTime();
            if (dateTime.isAfter(startTime) && dateTime.isBefore(stopTime))
                num++; // есть отчёт в искомом интервале времени
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
        System.out.println("c " + startTime.format(fmt) + " по " + stopTime.format(fmt) + " поступило " + num + " отчетов усыновителей");
        if (num > 0) System.out.println("все отчеты обработаны");
        return num; // количество отчетов за 24 часа до 21:00 указанной даты
    }
}

