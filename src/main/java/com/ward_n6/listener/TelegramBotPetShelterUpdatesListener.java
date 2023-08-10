package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import com.ward_n6.entity.BotMessaging;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.enums.PetsType;
import com.ward_n6.service.BotMessageService;
import com.ward_n6.service.PhotoService;
import com.ward_n6.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TelegramBotPetShelterUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);

    private final BotMessageService botMessageService;
    private final TelegramBot telegramBot;
    private PetShelter petShelter = new PetShelter() {
    };
    private PhotoService photoService;
    private GiveReport giveReport;
    private ReportService reportService;
    private static final Pattern ID_PATTERN = Pattern.compile("(\\d{1,5})");

    public TelegramBotPetShelterUpdatesListener(TelegramBot telegramBot, BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    boolean startSelected = false; // переменная для подтверждения старта
    boolean reportStart = false;

    /**
     * ОСНОВНОЙ МЕТОД
     * "прослушиваине бота, добавляет кнопки на экран и меню с первыми командами
     */
    @Override
    public int process(List<Update> updates) {
        // кнопки
        try {
            for (Update update : updates) {// итерируемся по ним
                logger.info("Processing update: {}", update); // записываем лог апдейтов на уровне инфо

                if (update.callbackQuery() != null) {
                    Long chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    switch (data) {
                        case "КНОПКА_ПРИЮТ_ДЛЯ_СОБАК": {
                            dogButton(chatId, "КНОПКА_ПРИЮТ_ДЛЯ_СОБАК");
                            break;
                        }
                        case "КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК": {
                            catButton(chatId, "КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК");
                            break;
                        }
                    }
                    continue;
                }
                // меню команд:
                Message message = update.message(); // получаем сообщение из текущего обновления
                Long chatId = message.chat().id(); // получаем идентификатор чата, к которому относится апдейт
                String messageText = message.text(); // получаем текст сообщения

                switch (messageText) {
                    case "/start":
                        startSelected = true;
                        afterStartMenu(chatId, "/start");
                        break;
                    case "/dogs":
                        if (startSelected) {
                            sendMessage(chatId, "Вас приветствует приют для собак. " +
                                    "Выберите интересующий Вас пункт меню.");
                        }
                        break;
                    case "/cats":
                        if (startSelected) {
                            sendMessage(chatId, "Вас приветствует приют для кошек. " +
                                    "Выберите интересующий Вас пункт меню.");
                        }
                        break;
//                    case "/photo":
//                        sendMessage(chatId, "Загрузите фото.");
//                        // photoService.getPhotos(update);
//                        if (message.photo().length > 0) {
//                            sendMessage(chatId, "Фото загружено");
//                        }
//                        break;

                    case "/report":
                        reportStart = true;
                        sendMessage(chatId, "Загрузите отчёт.");
                        break;

  //                      if reportStart { saveOwnerReportToDB(chatId, messageText);}

                    case "/volunteer":
                        sendMessage(chatId,
                                petShelter.getCallVolonteer());
                        break;

                    default:
                        if (messageText != null) { // проверяем, не пустой ли текст
                            saveMessages(chatId, messageText);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage()); // ловим ошибку
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // успешно завершаем метод, без падения
    }
//***************************** МЕТОДЫ *******************************************

    /**
     * метод покрыть тестом -> получить результат из обоих учловий оператора
     */
    private void saveMessages(long chatId, String messageText) {
        if (messageText.isEmpty()) { // обрабатываем нулловое значение из парсинга
            sendMessage(chatId, "Неверный формат сообщения");
        } else {
            BotMessaging botMessaging = new BotMessaging(); // создаём новое напоминание
            botMessaging.setChatId(chatId); // присваиваем созданному напоминанию значения из нашего апдейта
            botMessaging.setBotMessage(messageText);
            botMessageService.save(botMessaging); // сохранили наше сообщение в БД
        }
    }

    /**
     * метод покрыть тестом ->
     */
    // ОТПРАВКА ОТВЕТА БОТА:
    private void sendMessage(long chatId, String message) { // выносим отправку в отдельный метод
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage); // сохраняем в переменную sendMessage
        if (!sendResponse.isOk()) { // если отправка сообщения не удалась
            logger.error("Ошибка отправки сообщения: {}", sendResponse.description()); // сообщаем об ошибке
        }
    }

    // *****************************
    //МЕТОДЫ ДЛЯ КНОПОК:

    //После старта:
    private void afterStartMenu(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, "Здравствуйте. Это чатбот приюта для животных. " +
                "Какой приют Вас интересует?");
        InlineKeyboardButton chooseDogHouseButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Приют для собак" + ":dog:"));
        chooseDogHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_СОБАК");
        InlineKeyboardButton chooseCatHouseButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Приют для кошек" + ":cat:"));
        chooseCatHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК");
        Keyboard keyboard = new InlineKeyboardMarkup(chooseDogHouseButton, chooseCatHouseButton);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //КОШКИ:
    private void catButton(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для кошек");
        InlineKeyboardButton catHouseInfoButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        catHouseInfoButton.callbackData("ИНФО");
        InlineKeyboardButton catHouseHowToTakeButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Как взять кошку из приюта " + ":cat:"));
        catHouseHowToTakeButton.callbackData("КАК_ЗАБРАТЬ_КОШКУ");
        InlineKeyboardButton catHouseOwnerReportButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Прислать отчёт о питомце " + ":memo:"));
        catHouseOwnerReportButton.callbackData("ОТЧЁТ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(catHouseHowToTakeButton, catHouseInfoButton)
                .addRow(catHouseOwnerReportButton, callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //СОБАКИ
    private void dogButton(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для собак");
        InlineKeyboardButton dogHouseInfoButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        dogHouseInfoButton.callbackData("ИНФО");
        InlineKeyboardButton dogHouseHowToTakeButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Как взять собаку из приюта " + ":dog:"));
        dogHouseHowToTakeButton.callbackData("КАК_ЗАБРАТЬ_СОБАКУ");
        InlineKeyboardButton dogHouseOwnerReportButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Прислать отчёт о питомце " + ":memo:"));
        dogHouseOwnerReportButton.callbackData("ОТЧЁТ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(dogHouseHowToTakeButton, dogHouseInfoButton)
                .addRow(dogHouseOwnerReportButton, callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }


// СОХРАНЕНИЕ ОТЧЁТА
    public void saveOwnerReportToDB(long chatId, String messageText) {

        SendMessage sendMessage = new SendMessage(chatId, "Следуйте указаниям бота.");
        OwnerReport ownerReport = new OwnerReport();
        switch (messageText) {
            case "/petId" -> {
                sendMessage(chatId, "Укажите ID питомца");
                if (messageText.matches("/report \\d+")) {

                    long petsId = Long.parseLong(messageText.split(" ")[1]);

                    ownerReport.setPetId(petsId);
                } else {
                    return;
                }
            }
            case "/action" -> {
                sendMessage(chatId, "Опишите кратко поведение питомца");
                if (!messageText.isEmpty() && messageText.length() > 10) {

                    ownerReport.setPetsBehavior(messageText);
                } else {
                    sendMessage(chatId, "Вы не описали поведение питомца. Неполный отчёт не будет засчитан.");
                    ownerReport.setPetsBehavior("autoReport: не указано");
                    return;
                }
            }
            case "/helth" -> {
                sendMessage(chatId, "Опишите самочувствие питомца");
                if (!messageText.isEmpty()) {

                    ownerReport.setPetsHealth(messageText);
                } else {
                    sendMessage(chatId, "Вы не описали самочувствие питомца. Неполный отчёт не будет засчитан.");
                    ownerReport.setPetsHealth("autoReport: не указано");
                    return;
                }
            }
            case "/feed" -> {
                sendMessage(chatId, "Опишите рацион питомца");
                if (!messageText.isEmpty()) {

                    ownerReport.setNutrition(messageText);
                    // Устанавливаем флаг, что рацион был описан
                } else {
                    sendMessage(chatId, "Вы не описали рацион питомца. Неполный отчёт не будет засчитан.");
                    ownerReport.setNutrition("autoReport: не указано");
                    return;
                }
            }
        }
        ownerReport.setChatId(chatId);
        ownerReport.setReportDateTime(LocalDateTime.now());
        ownerReport.setPetsType(PetsType.DOG);
        reportService.save(ownerReport);
        saveMessages(chatId, "Ваш отчёт загружен");

    }

    private Update getNextUpdate() {
        while (true) {
            return telegramBot.execute(new GetUpdates().limit(1).offset(1)).updates().get(1);
        }
    }
}

