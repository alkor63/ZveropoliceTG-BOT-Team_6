package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.Photo;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.listener.PetsOwnerFactories;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.repository.PhotoRepository;
import com.ward_n6.service.owners.OwnerReportServiceImpl;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static com.ward_n6.listener.MessageStringsConstants.REPORT;

/**
 * класс для обработки и сохранения фото - хоть бы как загрузить уже -> лучше добавить в отчёт
 * НЕ РАБОТАЕТ!!!!!
 */
public class ReportHandler implements EventHandler {

    private  OwnerReport ownerReport = new OwnerReport();
    private final OwnerReportServiceImpl ownerReportServiceImpl;
    private final TelegramBot telegramBot;
    private final PhotoRepository photoRepository;
    private final PetsOwnerFactories petsOwnerFactories;
    private Photo photos;

    public ReportHandler(OwnerReportServiceImpl ownerReportServiceImpl, TelegramBot telegramBot,
                         PhotoRepository photoRepository, PetsOwnerFactories petsOwnerFactories) {

        this.ownerReportServiceImpl = ownerReportServiceImpl;
        this.telegramBot = telegramBot;
        this.photoRepository = photoRepository;
        this.petsOwnerFactories = petsOwnerFactories;
    }

    private Consumer<Update> actionOnNextMessage; // переменная для определения действий над поступаемым сообщением
    public static boolean isId = false;
    private boolean isHealth = false;
    private boolean isFeed = false;
    private boolean isAction = false;
    public static boolean isPhoto = false;
    public static Long petIdForPhoto = null;


    private String CHOICE_ID = " Сначала введите ID питомца";


    @Value("${path.to.file}")
    String folderPath; // путь к файлам

    @Override
    public boolean handle(Update update) {
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
        if (update.message() != null && update.message().text() != null) { // проверка, чтобы не "зависали" кнопки
            var text = update.message().text();

            switch (text) {
                case "/ID":
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            "Укажите ID Вашего питомца"));
                    actionOnNextMessage = upd -> {
                        var idMessage = upd.message().text();

                        if (idMessage.matches("\\d+")) { // проверяем, что указано число
                            long id = Long.parseLong(idMessage);
                            if ((TelegramBotPetShelterUpdatesListener.dogSelect && petsOwnerFactories.dogFactory(id, // проверка ID
                                    update.message().chat().id()) != null)
                                    || (TelegramBotPetShelterUpdatesListener.catSelect && petsOwnerFactories.catFactory(id,
                                    update.message().chat().id()) != null)) {
                                ownerReport.setPetId(id);
                                telegramBot.execute(new SendMessage(upd.message().chat().id(),
                                        "ID питомца записан."));
                                isId = true;
                                petIdForPhoto = id;


                            }
                        } else {
                            telegramBot.execute(new SendMessage(upd.message().chat().id(),
                                    "Формат ID неверный. Попробуйте ещё раз, введите числовое значение: /ID"));
                        }
                    };
                    break;

                case "/health":
                    if (isId) {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Опишите кратко самочувствие питомца"));
                        actionOnNextMessage = upd -> {
                            ownerReport.setPetsHealth(upd.message().text());
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Записано в отчёт!" + REPORT));
                            isHealth = true;
                        };

                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                CHOICE_ID));

                    }
                    break;

                case "/feed":
                    if (isId) {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Опишите рацион питомца"));
                        actionOnNextMessage = upd -> {
                            ownerReport.setNutrition(upd.message().text());
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Рацион питомца записан в отчёт!" + REPORT));
                            isFeed = true;
                        };
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                CHOICE_ID));
                    }
                    break;

                case "/action":
                    if (isId) {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Опишите кратко поведение питомца"));
                        actionOnNextMessage = upd -> {
                            ownerReport.setPetsBehavior(upd.message().text());
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Записано в отчёт!" + REPORT));
                            isAction = true;
                        };
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                CHOICE_ID));
                    }
                    break;

                case "/save":
                    if (isId && (isPhoto || isAction || isFeed || isHealth)) {

                            ownerReport.setReportDateTime(LocalDateTime.now());
                            ownerReport.setOwnerId(update.message().chat().id());
                            if (TelegramBotPetShelterUpdatesListener.dogSelect) {
                                ownerReport.setPetsType(PetsType.DOG);
                            } else if (TelegramBotPetShelterUpdatesListener.catSelect) {
                                ownerReport.setPetsType(PetsType.CAT);
                            }
                            ownerReportServiceImpl.save(ownerReport);
                            telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваш отчёт загружен"));
                    } else telegramBot.execute(new SendMessage(update.message().chat().id(),
                            """
                                    Пустой отчёт не может быть принят
                                    /report"""));
                    return true; // возвращаем true - это значит, что контекст завершен.
            }
        }
        return false;
    }
}
