package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.reports.Photo;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.enums.PetsType;
import com.ward_n6.listener.PetsOwnerFactories;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.repository.reports.PhotoRepository;
import com.ward_n6.service.owners.OwnerReportServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import static com.ward_n6.listener.MessageStringsConstants.REPORT;
import static com.ward_n6.listener.MessageStringsConstants.REPORT_WITHOUT_ID;
import static com.ward_n6.listener.TelegramBotPetShelterUpdatesListener.catSelect;
import static com.ward_n6.listener.TelegramBotPetShelterUpdatesListener.dogSelect;

@Component
/**
 * класс для обработки и сохранения фото - хоть бы как загрузить уже -> лучше добавить в отчёт
 * НЕ РАБОТАЕТ!!!!!
 */
public class ReportHandler implements EventHandler {

    private OwnerReport ownerReport = new OwnerReport();
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
    public static boolean isCatId = false;
    public static boolean isDogId = false;
    private boolean isHealth = false;
    private boolean isFeed = false;
    private boolean isAction = false;
    public static boolean isPhoto = false;
    public static Long petIdForPhoto = null;
    private Long dogId = null;
    private Long catId = null;


    private String CHOICE_ID = " Сначала введите ID питомца \n " +
            "введите или нажмите команду /ID";


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
                    checkAndSavePetIdToOwnerReportFromTB(update);
                    break;

                case "/health":
                    saveHealthReportToOwnerReport(update);
                    break;

                case "/feed":
                    saveFeedReportToOwnerReport(update);
                    break;

                case "/action":
                    saveActionReportToOwnerReport(update);
                    break;

                case "/save":
                    if ((isCatId || isDogId) && (isPhoto || isAction || isFeed || isHealth)) {
                        saveReportToDBFromTB(update);
                        return true;// возвращаем true - это значит, что контекст завершен.

                    } else telegramBot.execute(new SendMessage(update.message().chat().id(),
                            """
                                    Пустой отчёт не может быть принят
                                    /report"""));
                    return false;
            }
        }
        return false;
    }

    // *********************** сохранение отчёта *****************
    private OwnerReport saveReportToDBFromTB(Update update) {
        ownerReport.setReportDateTime(LocalDateTime.now());
        ownerReport.setOwnerId(update.message().chat().id());
        if (dogSelect) {
            ownerReport.setPetsType(PetsType.DOG);
        } else if (TelegramBotPetShelterUpdatesListener.catSelect) {
            ownerReport.setPetsType(PetsType.CAT);
        }
        ownerReportServiceImpl.save(ownerReport);
        telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваш отчёт загружен. \n" +
                "Для загрузки ещё одного отчёта или фото введите или нажмите команду \n /report \n" +
                "Для возврата к выбору приюта нажмите или введите командку \n" +
                "/start"));
        return ownerReport;// возвращаем true - это значит, что контекст завершен.
    }

    // ************ запись отчёта о поведении ***********
    private void saveActionReportToOwnerReport(Update update) {
        if (isCatId || isDogId) {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Опишите кратко поведение питомца"));
            actionOnNextMessage = upd -> {
                ownerReport.setPetsBehavior(upd.message().text());
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Записано в отчёт \n!" + REPORT_WITHOUT_ID));
                isAction = true;
            };
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    CHOICE_ID));
        }
    }

    //*************** запись отчёта о питании  *************
    private void saveFeedReportToOwnerReport(Update update) {
        if (isCatId || isDogId) {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Опишите рацион питомца"));
            actionOnNextMessage = upd -> {
                ownerReport.setNutrition(upd.message().text());
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Рацион питомца записан в отчёт \n!" + REPORT_WITHOUT_ID));
                isFeed = true;
            };
        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    CHOICE_ID));
        }
    }

    // ***************** запись отчёта о здоровье *********
    private void saveHealthReportToOwnerReport(Update update) {
        if (isCatId || isDogId) {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Опишите кратко самочувствие питомца"));
            actionOnNextMessage = upd -> {
                ownerReport.setPetsHealth(upd.message().text());
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        "Записано в отчёт! \n" + REPORT_WITHOUT_ID));
                isHealth = true;
            };

        } else {
            telegramBot.execute(new SendMessage(update.message().chat().id(),
                    CHOICE_ID));
        }
    }

    //******************** проверка и сохранение ID питомца *********
    private void checkAndSavePetIdToOwnerReportFromTB(Update update) {
        telegramBot.execute(new SendMessage(update.message().chat().id(),
                "Укажите ID Вашего питомца"));
        actionOnNextMessage = upd -> {
            var idMessage = upd.message().text();

            if (idMessage.matches("\\d+")) { // проверяем, что указано число
                long id = Long.parseLong(idMessage);
                if ((dogSelect && petsOwnerFactories.dogFactory(id, // проверка ID
                        update.message().chat().id()) != null)) {
                    ownerReport.setPetId(id);
                    isDogId = true;
                    isCatId = false;
                    dogId = id;
                    telegramBot.execute(new SendMessage(upd.message().chat().id(),
                            "ID питомца записан. \n" + REPORT_WITHOUT_ID));
                } else if
                (catSelect && petsOwnerFactories.catFactory(id,
                                update.message().chat().id()) != null) {
                    ownerReport.setPetId(id);
                    isCatId = true;
                    isDogId = false;
                    catId = id;
                    telegramBot.execute(new SendMessage(upd.message().chat().id(),
                            "ID питомца записан. \n" + REPORT_WITHOUT_ID));
                }
                if (dogSelect && isDogId) {
                    petIdForPhoto = dogId;
                } else if (catSelect || isCatId) {
                    petIdForPhoto = catId;
                }
            } else {
                telegramBot.execute(new SendMessage(upd.message().chat().id(),
                        "Формат ID неверный. Попробуйте ещё раз, введите числовое значение: /ID"));
            }
        };
    }
}
