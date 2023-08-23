package com.ward_n6.service;

import com.ward_n6.entity.PetWithOwner;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.exception.PutToMapException;
import com.ward_n6.repository.OwnerReportRepository;
import com.ward_n6.repository.PetsOwnerArchiveRepository;
import com.ward_n6.repository.PetsOwnerRepository;
import com.ward_n6.service.interfaces.PetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class VolunteerService {
    // класс с функционалом волонтёра
    // метод добавление усыновителя+животного в БД == createCatsOwner и createDogsOwner
    // метод беседы с усыновителем == callVolunteer
    // номер телефона как ID ???? - нет, только для связи
    // метод просмотр отчётов (точнее подсчета) == viewAllReports
    // Бот отслеживает испытательный срок и если срок подошел к концу - обращается к волонтёру за приговором
    // приговор в методе endOfProbationPeriod
    // вызов этого метода из Бота и его обработка - ниже, в ownersVerdict


    private final PetsOwnerRepository petsOwnerRepository;
    private final PetsOwnerArchiveRepository petsOwnerArchiveRepository;
    @Resource
    private final OwnerReportRepository ownerReportRepository;
    private final PetService petService;
    private final PetsOwnerServiceImpl petsOwnerService;



    public VolunteerService(PetsOwnerRepository petsOwnerRepository,
                            PetsOwnerArchiveRepository petsOwnerArchiveRepository,
                            OwnerReportRepository ownerReportRepository, PetService petService, PetsOwnerServiceImpl petsOwnerService) {
        this.petsOwnerRepository = petsOwnerRepository;
        this.petsOwnerArchiveRepository = petsOwnerArchiveRepository;
        this.ownerReportRepository = ownerReportRepository;
        this.petService = petService;

        this.petsOwnerService = petsOwnerService;
    }


    public String callVolunteer(String firstName) {
        // Волонтёр откликается на просьбу о связи от пользователя с именем firstName
        return "Привет, " + firstName + "! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы";
    }

    public PetWithOwner createPetsOwner(Owner owner, Pet pet) {
        // собираем в одну таблицу PetWithOwner усыновителя owner, животное pat
        // и время начала и окончания испытательного срока
        try {
            PetWithOwner petWithOwner = new PetWithOwner(owner, pet, LocalDate.now(), LocalDate.now().plusDays(30));
            return petsOwnerService.addToPetWithOwner(petWithOwner);
        } catch (PutToMapException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

//    public Boolean removePetWithOwnerToArchive(PetWithOwner petWithOwner) {
//        // убираем парочку из таблицы PetWithOwner и заносим в одноименный архив
//        // после успешного прохождения (окончания) испытательного срока
//        try {
//            petsOwnerArchiveRepository.addToArchivePetWithOwner(petWithOwner);
//            petsOwnerService.deletePetWithOwnerByValue(petWithOwner);
//            return true;
//        } catch (PutToMapException | DeleteFromMapException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }


    public int viewAllReports(LocalDate date) {
        // просмотр всех отчетов за прошедшие сутки (с 21:00 предыдущего дня по 21:00 дня = date
        int num = 0;
        LocalTime time = LocalTime.of(21, 00);
        // запрос на просмотр отчетов может прийти, например, в 21:01
        // чтоб не потерять отчеты, пришедшие с 21:00 по 21:01, время задаём здесь
        LocalDateTime startTime = LocalDateTime.of(date.minusDays(1), time);
        LocalDateTime stopTime = LocalDateTime.of(date, time).plusNanos(1); // т.к. методы isAfter и isBefore не включают equals
//        List<OwnerReport> ownerReportList = new ArrayList<>();
        //(ownerReportRepository.getAllOwnerReports());

        List<OwnerReport> allOwnerReports = ownerReportRepository.findAll();
        System.out.println("ownerReportList = " + allOwnerReports);
        for (OwnerReport ownerReport : allOwnerReports) {
            LocalDateTime dateTime = ownerReport.getReportDateTime();
            if (dateTime.isAfter(startTime) && dateTime.isBefore(stopTime))
                num++; // есть отчёт в искомом интервале времени
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
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
        // foreach для всех отчетов
        for (OwnerReport ownerReport : ownerReportRepository.findAll()) {
            LocalDate date = ownerReport.getReportDateTime().toLocalDate();
            if (ownerReport.getPetId() == petId) {
                if (date.isBefore(before30) && date.isAfter(before60)) {
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
//    public String ownersVerdict(PetWithOwner petWithOwner) {
//        int rating = endOfProbationPeriod(petWithOwner);
//        switch (rating) {
//            case 0:
//                // вернуть животное в приют
//                //         petsOwnerRepository.deletePetWithOwnerByValue(petWithOwner); ==> удалить запись из таблицы
//                return "За 30 дней Вы ни разу не прислали отчет. Вы должны вернуть животное в приют!";
//            case 1:
//                // продлить испытательный срок на 30 дней
//                int idFromMap = petsOwnerService.idByValue(petWithOwner);
//                LocalDate newEndDate30 = LocalDate.now().plusDays(30);
//                petWithOwner.setEndDate(newEndDate30);   //увеличили дату окончания исп. срока на 30 дней
//                try {
//                    petsOwnerService.editPetWithOwnerById(idFromMap, petWithOwner);//перезаписали с новой датой окончания исп.срока
//                } catch (EditMapException e) {
//                    System.out.println(e.getMessage());
//                }
//                return "Вы очень редко присылали отчеты Испытательный срок продлен на 30 дней";
//            case 2:
//                // продлить испытательный срок на 14 дней
//                LocalDate newEndDate14 = LocalDate.now().plusDays(14);
//                idFromMap = petsOwnerService.idByValue(petWithOwner);
//                petWithOwner.setEndDate(newEndDate14);   //увеличили дату окончания исп.срока на 14 дней
//                try {
//                    petsOwnerService.editPetWithOwnerById(idFromMap, petWithOwner);//перезаписали с новой датой окончания исп.срока
//                } catch (EditMapException e) {
//                    System.out.println(e.getMessage());
//                }
//                return "Вы очень редко присылали отчеты Испытательный срок продлен на 14 дней";
//            case 3:
//                // поздравить с успешным прохождением испытательного срока
//                try {
//                    removePetWithOwnerToArchive(petWithOwner);
//                    // method is Boolean
////              перемещаем запись о животном и хозяине в архив
////              удаляем запись о животном и хозяине из таблицы с испытательным сроком
////              удаляем запись о животном из таблицы Pet - этого животного больше нет в приюте
//                    petService.deletePetByValue(petWithOwner.getPet());
//                } catch (DeleteFromMapException e) {
//                    System.out.println(e.getMessage());
//                }
//                return "Поздравляем с завершением испытательного срока! Ваш питомец остаётся с Вами навсегда";
//            default:
//                return "Некорректное значение rating. Разработчик что-то накосячил";
//        }
//    }

    public int verifyReport(OwnerReport ownerReport) {
// метод проверяет заполнено каждое из 4 полей в отчете или нет
// возвращает целое число (до 4 знаков), собранное по бинарному принципу:
// 1111 - все поля заполнены, 1010 - заполнены 1 и 3, а 2 и 4 - пустые; 0 - пустой отчет
        int n = 0;
        if (ownerReport.isHavePhoto()) n = n + 1000;
        if (!nullString(ownerReport.getNutrition())) n = n + 100;
        if (!nullString(ownerReport.getPetsHealth())) n = n + 10;
        if (!nullString(ownerReport.getPetsBehavior())) n = n + 1;
        return n;
    }

    public String reportExpertise(OwnerReport ownerReport){
        int n = verifyReport(ownerReport);
        switch (n){
            case 0:
                // все поля пустые
                return "Вы прислали отчет. Потрудитесь заполнить все поля!";
            case 1:
                // заполнено только поле "поведение"
                return "Вы рассказали только о поведении питомца. Заполните, пожалуйста, остальные поля";
            case 10:
                // заполнено только поле "самочувствие"
                return "Вы рассказали только о самочувствии питомца. Заполните, пожалуйста, остальные поля";
            case 100:
                // заполнено только поле "питание"
                return "Вы рассказали только о питании питомца. Заполните, пожалуйста, остальные поля";
            case 1000:
                // заполнено только поле "фото"
                return "Вы прислали только фото питомца. Заполните, пожалуйста, остальные поля";

            case 11:
                // заполнены поля "поведение" и "самочувствие"
                return "Вы рассказали только о поведении питомца. Заполните, пожалуйста, остальные поля";
            case 101:
                // заполнены поля "поведение" и "питание"
                return "Вы рассказали только о самочувствии питомца. Заполните, пожалуйста, остальные поля";
            case 1001:
                // заполнены поля "поведение" и "фото"
                return "Вы рассказали о поведении питомца и прислали фото. Заполните, пожалуйста, остальные поля";
            case 110:
                // заполнены поля "питание" и "самочувствие"
                return "Вы рассказали о питании и самочувствие питомца. Заполните, пожалуйста, остальные поля";
            case 1010:
                // заполнены поля "самочувствие" и "фото"
                return "Вы рассказали о самочувствии питомца и прислали фото. Заполните, пожалуйста, остальные поля";
            case 1100:
                // заполнены поля "питание" и "фото"
                return "Вы рассказали о питании питомца и прислали фото. Заполните, пожалуйста, остальные поля";

            case 111:
                // заполнены все поля кроме "фото"
                return "Вы забыли прислать фото питомца. Заполните, пожалуйста, это поле";
            case 1011:
                // заполнены все поля кроме "питание"
                return "Вы забыли рассказать о питании питомца. Заполните, пожалуйста, это поле";
            case 1101:
                // заполнены все поля кроме "самочувствие"
                return "Вы забыли рассказать о самочувствии питомца. Заполните, пожалуйста, это поле";
            case 1110:
                // заполнены все поля кроме "поведение"
                return "Вы забыли рассказать о поведении питомца. Заполните, пожалуйста, это поле";

            case 1111:
                // заполнены все поля
                return "Отличный отчёт!";
            default:
                return "Некорректное значение кода отчета. Разработчик что-то накосячил";
        }
    }
    public static boolean nullString(String s) {
        return (s == null || s.isEmpty() || s.isBlank());
    }
}

