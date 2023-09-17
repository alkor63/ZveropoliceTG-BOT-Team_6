package com.ward_n6.service;

import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.owner.PetsOwnerRepository;
import com.ward_n6.repository.pets.PetBaseRepository;
import com.ward_n6.service.interfaces.OwnerReportService;
import com.ward_n6.service.interfaces.PetsOwnerService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 *
 */
@Service
public class VolunteerService {
    // класс с функционалом волонтёра
    // метод беседы с усыновителем == callVolunteer
    // метод просмотр отчётов (точнее подсчета) == viewAllReports
    // Таймер отслеживает испытательный срок и если срок подошел к концу - обращается к волонтёру за приговором
    // приговор в методе endOfProbationPeriod
    // вызов этого метода из Таймера и его обработка - ниже, в ownersVerdict

    private final PetsOwnerRepository petsOwnerRepository;
    private final PetsOwnerService petsOwnerService;
    //        @Resource
    private final OwnerReportService ownerReportService;
    @Resource
    private final PetBaseRepository petBaseRepository;

    public VolunteerService(PetsOwnerRepository petsOwnerRepository,
                            PetsOwnerService petsOwnerService, OwnerReportService ownerReportService,
                            PetBaseRepository petBaseRepository) {
        this.petsOwnerRepository = petsOwnerRepository;
        this.petsOwnerService = petsOwnerService;
        this.ownerReportService = ownerReportService;
        this.petBaseRepository = petBaseRepository;
    }


    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public String callVolunteer(String firstName) {
        // Волонтёр откликается на просьбу о связи от пользователя с именем firstName
        return "Привет, " + firstName + "! Я Игорь - волонтёр приюта для животных. Готов ответить на все вопросы";
    }


    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public String viewAllReports(LocalDate date) {
        // просмотр всех отчетов за прошедшие сутки (с 21:00 предыдущего дня по 21:00 дня = date
        int num = 0;
        LocalTime time = LocalTime.of(21, 0);
        // запрос на просмотр отчетов может прийти, например, в 21:01
        // чтоб не потерять отчеты, пришедшие с 21:00 по 21:01, время задаём здесь
        LocalDateTime startTime = LocalDateTime.of(date.minusDays(1), time);
        LocalDateTime stopTime = LocalDateTime.of(date, time).minusNanos(1); // т.к. методы isAfter и isBefore не включают equals

        List<OwnerReport> allOwnerReports = ownerReportService.getAllOwnerReports();
        for (OwnerReport ownerReport : allOwnerReports) {
            LocalDateTime dateTime = ownerReport.getReportDateTime();
            if (dateTime.isAfter(startTime) && dateTime.isBefore(stopTime))
                num++; // есть отчёт в искомом интервале времени
        }
//        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String ss = "";
        if (num > 0) ss = ", все отчеты обработаны";
        return "С " + startTime + " по " + stopTime.plusNanos(1) + " поступило " + num + " отчетов усыновителей" + ss;
        // num - количество отчетов за 24 часа до 21:00 указанной даты
    }

    public int endOfProbationPeriod(PetsOwner petWithOwner) {

// Исходим из того, что запрос на "приговор" приходит в день окончания испытательного срока
// т.е. дата = localDate.now()
// просматриваем все отчеты этого усыновителя за испытательный период и присваиваем ему рейтинг

        long petId = petWithOwner.getId();
        int numReport = 0;
        int numOldReport = 0;
        LocalDate today = LocalDate.now();
        LocalDate before30 = today.minusDays(30);
        LocalDate before60 = today.minusDays(61);
        // list всех отчетов
        // foreach для всех отчетов
        for (OwnerReport ownerReport : ownerReportService.getAllOwnerReports()) {
            LocalDate date = ownerReport.getReportDateTime().toLocalDate();
            if (petId == ownerReport.getPetId()) {
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

    // метод обработки "приговора", вызывающий endOfProbationPeriod() - перенести в Timer
    public String ownersVerdict(PetsOwner petWithOwner) {
        int rating = endOfProbationPeriod(petWithOwner);

        switch (rating) {
            case 0:
                // вернуть животное в приют
                //         petsOwnerRepository.deletePetWithOwnerByValue(petWithOwner); ==> удалить запись из таблицы
                return "За 30 дней Вы ни разу не прислали отчет. Вы должны вернуть животное в приют!";
            case 1:
                // продлить испытательный срок на 30 дней
                LocalDate newEndDate30 = LocalDate.now().plusDays(30);
                //увеличили дату окончания испытательного срока на 30 дней
                try {
                    petsOwnerService.editDateEndPetsOwnerById(Math.toIntExact(petWithOwner.getId()), newEndDate30);//перезаписали с новой датой окончания испытательного срока
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }

                return "Вы очень редко присылали отчеты. Испытательный срок продлен на 30 дней";
            case 2:
                // продлить испытательный срок на 14 дней
                LocalDate newEndDate14 = LocalDate.now().plusDays(14);
                //увеличили дату окончания испытательного срока на 14 дней
                try {
                    petsOwnerService.editDateEndPetsOwnerById(Math.toIntExact(petWithOwner.getId()), newEndDate14);//перезаписали с новой датой окончания испытательного срока
                } catch (NotFoundException e) {
                    throw new RuntimeException(e);
                }
                return "Вы очень редко присылали отчеты. Испытательный срок продлен на 14 дней";
            case 3:
                // поздравить с успешным прохождением испытательного срока
                try {

//              удаляем запись о животном и хозяине из таблицы с испытательным сроком
//              удаляем запись о животном из таблицы Pet - этого животного больше нет в приюте

                    petsOwnerRepository.deleteById(petWithOwner.getId());
                    petBaseRepository.deleteById(petWithOwner.getId());

                    return "Поздравляем с завершением испытательного срока! Ваш питомец остаётся с Вами навсегда";
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            default:
                return "Некорректное значение rating. Разработчик что-то накосячил";
        }
    }

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

    public String reportExpertise(OwnerReport ownerReport) {
        // проверяем как заполнены поля отчёта и возвращаем соответствующий ответ
        int n = verifyReport(ownerReport);
        return switch (n) {
            case 0 ->
                // все поля пустые
                    "Вы прислали отчет. Потрудитесь заполнить все поля!";
            case 1 ->
                // заполнено только поле "поведение"
                    "Вы рассказали только о поведении питомца. Заполните, пожалуйста, остальные поля";
            case 10 ->
                // заполнено только поле "самочувствие"
                    "Вы рассказали только о самочувствии питомца. Заполните, пожалуйста, остальные поля";
            case 100 ->
                // заполнено только поле "питание"
                    "Вы рассказали только о питании питомца. Заполните, пожалуйста, остальные поля";
            case 1000 ->
                // заполнено только поле "фото"
                    "Вы прислали только фото питомца. Заполните, пожалуйста, остальные поля";
            case 11 ->
                // заполнены поля "поведение" и "самочувствие"
                    "Вы рассказали только о поведении питомца. Заполните, пожалуйста, остальные поля";
            case 101 ->
                // заполнены поля "поведение" и "питание"
                    "Вы рассказали только о самочувствии питомца. Заполните, пожалуйста, остальные поля";
            case 1001 ->
                // заполнены поля "поведение" и "фото"
                    "Вы рассказали о поведении питомца и прислали фото. Заполните, пожалуйста, остальные поля";
            case 110 ->
                // заполнены поля "питание" и "самочувствие"
                    "Вы рассказали о питании и самочувствие питомца. Заполните, пожалуйста, остальные поля";
            case 1010 ->
                // заполнены поля "самочувствие" и "фото"
                    "Вы рассказали о самочувствии питомца и прислали фото. Заполните, пожалуйста, остальные поля";
            case 1100 ->
                // заполнены поля "питание" и "фото"
                    "Вы рассказали о питании питомца и прислали фото. Заполните, пожалуйста, остальные поля";
            case 111 ->
                // заполнены все поля кроме "фото"
                    "Вы забыли прислать фото питомца. Заполните, пожалуйста, это поле";
            case 1011 ->
                // заполнены все поля кроме "питание"
                    "Вы забыли рассказать о питании питомца. Заполните, пожалуйста, это поле";
            case 1101 ->
                // заполнены все поля кроме "самочувствие"
                    "Вы забыли рассказать о самочувствии питомца. Заполните, пожалуйста, это поле";
            case 1110 ->
                // заполнены все поля кроме "поведение"
                    "Вы забыли рассказать о поведении питомца. Заполните, пожалуйста, это поле";
            case 1111 ->
                // заполнены все поля
                    "Отличный отчёт!";
            default -> "Некорректное значение кода отчета. Разработчик что-то накосячил";
        };
    }

    public static boolean nullString(String s) {
        return (s == null || s.isEmpty() || s.isBlank());
    }
}
