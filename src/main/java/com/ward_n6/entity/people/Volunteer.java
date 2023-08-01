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

    public int endOfProbationPeriod(PetWithOwner petWithOwner) {

// Исходим из того, что запрос на "приговор" приходит в день окончания испытательного срока
// т.е. дата = localDate.now()
// просматриваем все отчеты этого усыновителя за испытательный период и присваиваем ему рейтинг
        long petId = petWithOwner.getPet().getId();
        int numReport = 0;
        int numOldReport = 0;
        LocalDate today = LocalDate.now();
        LocalDate before30 = today.minusDays(30);
        LocalDate before60 = today.minusDays(61);
        // list всех отчетов
        List<OwnerReport> ownerReportList = new ArrayList<>();
        // foreach для всех отчетов
        for (OwnerReport ownerReport : ownerReportList) {
            LocalDate date = ownerReport.getReportDateTime().toLocalDate();
            if (ownerReport.getPetId() ==  petId) {
                if(date.isBefore(before30) && date.isAfter(before60)) {
                    numOldReport++;
                    break;
                }
                if (date.isAfter(before30.minusDays(1))) numReport++;
            }
        }
        if (numReport == 0) return 0;
        else if (numReport <= 10 && numOldReport == 0) return 1;
        else if (numReport <= 20 && numOldReport == 0) return 2;
        else return 3;
    }

    // метод обработки "приговора", вызывающий endOfProbationPeriod() - перенести в listener Боту
    public void ownersVerdict(PetWithOwner petWithOwner) throws PutToMapException, DeleteFromMapException {
        int rating = endOfProbationPeriod(petWithOwner);
        switch (rating) {
            case 0:
                // вернуть животное в приют
                System.out.println("За 30 дней Вы ни разу не прислали отчет. Вы должны вернуть животное в приют!");
                //         petsOwnerService.deletePetWithOwnerByValue(petWithOwner); ==> удалить запись из таблицы
                break;
            case 1:
                // продлить испытательный срок на 30 дней
                System.out.println("Вы очень редко присылали отчетыю Испытательный срок продлен на 30 дней");
                LocalDate newEndDate30 = LocalDate.now().plusDays(30);
                petWithOwner.setEndDate(newEndDate30);   // узнать его id
                petsOwnerService.editPetWithOwnerById();  //лучше по value
                        PetWithOwner editPetWithOwnerById(int recordId, PetWithOwner petWithOwner)
                break;
            case 2:
                // продлить испытательный срок на 14 дней
                System.out.println("Вы присылали отчеты не регулярно. Испытательный срок продлен на 14 дней");
                LocalDate newEndDate14 = LocalDate.now().plusDays(14);
                petWithOwner.setEndDate(newEndDate30);   // узнать его id

                break;
            case 3:
                // поздравить с успешным прохождением испытательношо срока
                System.out.println("Поздравляем с завершением испытательного срока! Ваш питомец остаётся с Вами навсегда");
                PetWithOwner petToOwner = removePetWithOwnerToArchive(petWithOwner);
//                delete pet from pet_table
                break;
            default:
                System.out.println("Некорректное значение rating. Разработчик что-то накосячил");
        }
    }

}

