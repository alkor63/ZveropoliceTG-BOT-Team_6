package com.ward_n6.Timer;
import com.pengrad.telegrambot.TelegramBot;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.owners.PetsOwnerArchive;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.repository.*;
import com.ward_n6.service.VolunteerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OwnerTrialPriodTimer {
    // класс для отслкживания испытательного срока
    private final BotMessagingRepository botMessagingRepository;
    private final TelegramBot telegramBot;
    private final VolunteerService volunteerService;
    private final PetsOwnerRepository petsOwnerRepository;

    private final OwnerReportRepository ownerReportRepository;


    public OwnerTrialPriodTimer(BotMessagingRepository botMessagingRepository,
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


//    @Scheduled(cron = "* 0 21 * * *") //вызов каждый день в 21:00
    @Scheduled(cron = "* 45 20 * * *") //вызов каждый день в 21:00

    public void task() {
        //проверяем метод Позвать волонтёра
        System.out.println(volunteerService.callVolunteer("Хочу собачку"));

        // Волонтёр просматривает все отчеты за указанную дату
        String svr = volunteerService.viewAllReports(LocalDate.now());
        System.out.println(svr);
// просматриваем все отчёты, оцениваем качество каждого отчета
        // можно выбрать только отчёты с конкретным id животного и/или хозяина
        List<OwnerReport> allOwnerReports = ownerReportRepository.findAll();
        for (OwnerReport ownerReport : allOwnerReports) {
            System.out.println("Verdict "+ownerReport.getId()+" = " + volunteerService.reportExpertise(ownerReport));
        }

        List<PetsOwner> allPetOwners = petsOwnerRepository.findAll();
        for (PetsOwner petsOwner : allPetOwners) {
            if (petsOwner.getDateEnd().equals(LocalDate.now())) {
//если сегодня день окончания испытательного срока, Волонтёр выносит "приговор"
                String verdict = volunteerService.ownersVerdict(petsOwner);
                System.out.println("Сообщение для хозяина с id= " + petsOwner.getOwnerId() + " :/n" + verdict);
            }
        }
        return;
    }

}
