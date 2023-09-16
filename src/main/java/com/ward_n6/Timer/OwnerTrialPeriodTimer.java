package com.ward_n6.Timer;

import com.pengrad.telegrambot.TelegramBot;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.BotMessagingRepository;
import com.ward_n6.repository.reports.OwnerReportRepository;
import com.ward_n6.repository.owner.PetsOwnerRepository;
import com.ward_n6.service.VolunteerService;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

//@Component
public class OwnerTrialPeriodTimer {
    // класс для отслеживания испытательного срока
    private final BotMessagingRepository botMessagingRepository;
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final PetsOwnerRepository petsOwnerRepository;
    private final OwnerReportRepository ownerReportRepository;

    private Logger logger = (Logger) LoggerFactory.getLogger(OwnerTrialPeriodTimer.class);

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
    // удаление из БД после ИС
    // завершение испытательного срока
    // проверка качества отчётов


    @Scheduled(cron = "11 13 21 * * *") //вызов каждый день в мм чч на время отладки

    public void task() {
//        long chatId = 1L;  // заменить на owner.getId()

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //проверяем метод Позвать волонтёра
        //System.out.println(volunteerService.callVolunteer("Будущий лучший хозяин"));

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        // Волонтёр просматривает все отчеты за указанную дату
        String svr = volunteerService.viewAllReports(LocalDate.now());
        logger.info(" \n *** " + svr);
//                long chatId = petsOwner.getOwnerId();
//        telegramBot.execute(new SendMessage(chatId, svr));

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// просматриваем все отчёты, оцениваем качество каждого отчета
        // можно выбрать только отчёты с конкретным id животного и/или хозяина
        List<OwnerReport> allOwnerReports = ownerReportRepository.findAll();
        for (OwnerReport ownerReport : allOwnerReports) {
            String verdict = "Verdict " + ownerReport.getId() + " = " + volunteerService.reportExpertise(ownerReport);
            logger.info(" \n *** " + verdict);
//            long chatId = petsOwner.getOwnerId();
//            telegramBot.execute(new SendMessage(chatId, verdict));
        }

// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        List<PetsOwner> allPetOwners = petsOwnerRepository.findAll();
        for (PetsOwner petsOwner : allPetOwners) {
            if (petsOwner.getEndDate().equals(LocalDate.now())) {
//если сегодня день окончания испытательного срока, Волонтёр выносит "приговор"
                String finalVerdict = volunteerService.ownersVerdict(petsOwner);
//                long chatId = petsOwner.getOwnerId();
//                telegramBot.execute(new SendMessage(chatId, finalVerdict));
                logger.info("Сообщение для хозяина с id= "
                        + petsOwner.getOwnerId() + " : \n *** " + finalVerdict);
            }
        }
    }
}
