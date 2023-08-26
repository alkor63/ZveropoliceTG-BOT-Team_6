package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.repository.pets.PetBaseRepository;
import com.ward_n6.service.OwnerReportServiceImpl;
import com.ward_n6.service.pets.PetServiceImpl;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.function.Consumer;
/**
 * класс для обработки и сохранения фото - хоть бы как загрузить уже -> лучше добавить в отчёт
 * НЕ РАБОТАЕТ!!!!!
 */
public class ReportHandler implements EventHandler {

    private OwnerReport ownerReport = new OwnerReport();

    private final OwnerReportServiceImpl ownerReportServiceImpl;
    private final TelegramBot telegramBot;
    private final PetServiceImpl petService;
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final PetBaseRepository petBaseRepository;

    public ReportHandler(OwnerReportServiceImpl ownerReportServiceImpl, TelegramBot telegramBot, PetServiceImpl petService, CatRepository catRepository, DogRepository dogRepository, PetBaseRepository petBaseRepository) {

        this.ownerReportServiceImpl = ownerReportServiceImpl;
        this.telegramBot = telegramBot;
        this.petService = petService;
        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
        this.petBaseRepository = petBaseRepository;
    }

    private Consumer<Update> actionOnNextMessage; // переменная для определения действий над поступаемым сообщением
    boolean isId = false;
    boolean isHealth = false;
    boolean isFeed = false;
    boolean isAction = false;

    @Value("${path.to.file}")
    String folderPath; // путь к файлам

    @Override
    public boolean handle(Update update) {
//
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
        var text = update.message().text();
        //var messagePhoto = update.message().photo();
        switch (text) {
            case "/ID":
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Укажите ID Вашего питомца"));
                actionOnNextMessage = upd -> {
                    var idMessage = upd.message().text();
                    long id = Long.parseLong(idMessage);
                    if (idMessage.matches("\\d+") && (catRepository.getById(id) != null || dogRepository.getById(id) != null)) { // проверяем, что указано число
                        ownerReport.setPetId(id);
                        telegramBot.execute(new SendMessage(upd.message().chat().id(),
                                "ID питомца записан."));
                        isId = true;
                    } else {
                        telegramBot.execute(new SendMessage(upd.message().chat().id(),
                                "ID питомца указан неверно. Попробуйте ещй раз: /ID"));
                    }
                };
                break;

            case "/health":
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Опишите кратко самочувствие питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setPetsHealth(upd.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Записано в отчёт!"));
                    isHealth = true;
                };
                break;

            case "/feed":
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Опишите рацион питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setNutrition(upd.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Рацион питомца записан в отчёт!"));
                    isFeed = true;
                };
                break;

            case "/action":
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Опишите кратко поведение питомца"));
                actionOnNextMessage = upd -> {
                    ownerReport.setPetsBehavior(upd.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Записано в отчёт!"));
                    isAction = true;
                };
                break;

            case "/save":
                ownerReport.setReportDateTime(LocalDateTime.now());
                ownerReport.setOwnerId(update.message().chat().id());
                if (TelegramBotPetShelterUpdatesListener.dogSelect) {
                    ownerReport.setPetsType(PetsType.DOG);
                } else if (TelegramBotPetShelterUpdatesListener.catSelect) {
                    ownerReport.setPetsType(PetsType.CAT);
                }
                ownerReportServiceImpl.save(ownerReport);
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваш отчёт загружен"));
                return true; // возвращаем true - это значит, что контекст завершен.
        }
        return false;
    }
}
