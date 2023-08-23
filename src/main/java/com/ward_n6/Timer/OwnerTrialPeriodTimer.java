package com.ward_n6.Timer;
import com.pengrad.telegrambot.TelegramBot;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.*;
import com.ward_n6.service.VolunteerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OwnerTrialPeriodTimer {
    // класс для отслкживания испытательного срока
    private final BotMessagingRepository botMessagingRepository;
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final PetsOwnerRepository petsOwnerRepository;

    private final OwnerReportRepository ownerReportRepository;


    public OwnerTrialPeriodTimer(BotMessagingRepository botMessagingRepository,
                                 TelegramBot telegramBot,
                                 VolunteerService volunteerService,
                                 PetsOwnerRepository petsOwnerRepository,
                                 OwnerReportRepository ownerReportRepository) {
        this.botMessagingRepository = botMessagingRepository;
        this.telegramBot = telegramBot;
        this.volunteerService = volunteerService;
        this.petsOwnerRepository = petsOwnerRepository;
        this.ownerReportRepository = ownerReportRepository;
    }

    // продление испытательного срока
    // @Scheduled
    // удаление из БД / перенос в чёрный список после ИС
    //завершение испытательного срока


//    @Scheduled(cron = "1 00 21 * * *") //вызов каждый день в 21:00
    @Scheduled(cron = "1 12 23 * * *") //вызов каждый день в мм чч на время отладки

    public void task() {
        long chatId = 1L;  // заменить на owner.getId()

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //проверяем метод Позвать волонтёра
        System.out.println(volunteerService.callVolunteer("Будущий лучший хозяин"));

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Волонтёр просматривает все отчеты за указанную дату
        String svr = volunteerService.viewAllReports(LocalDate.now());
        System.out.println(svr);
//        telegramBot.execute(new SendMessage(chatId, svr));

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// просматриваем все отчёты, оцениваем качество каждого отчета
        // можно выбрать только отчёты с конкретным id животного и/или хозяина
        List<OwnerReport> allOwnerReports = ownerReportRepository.findAll();
        for (OwnerReport ownerReport : allOwnerReports) {
            String verdict = "Verdict "+ownerReport.getId()+" = " + volunteerService.reportExpertise(ownerReport);
            System.out.println(verdict);
//            telegramBot.execute(new SendMessage(chatId, verdict));
        }

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<PetsOwner> allPetOwners = petsOwnerRepository.findAll();
        for (PetsOwner petsOwner : allPetOwners) {
            if (petsOwner.getDateEnd().equals(LocalDate.now())) {
//если сегодня день окончания испытательного срока, Волонтёр выносит "приговор"
                String finalVerdict = volunteerService.ownersVerdict(petsOwner);
//                telegramBot.execute(new SendMessage(chatId, finalVerdict));
                System.out.println("Сообщение для хозяина с id= " + petsOwner.getOwnerId() + " :/n" + finalVerdict);
            }
        }
        return;
//      выполнение зациклено, нужно как-то прервать
    }

}
