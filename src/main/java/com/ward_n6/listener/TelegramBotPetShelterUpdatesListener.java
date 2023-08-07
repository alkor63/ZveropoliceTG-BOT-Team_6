package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import com.ward_n6.entity.BotMessaging;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.enums.PetsType;
import com.ward_n6.service.BotMessageService;
import com.ward_n6.service.OwnerService;
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
    private final PetShelter petShelter = new PetShelter() {
    };
    private PhotoService photoService;

    private final ReportService reportService;


    private final OwnerService ownerService;

    private static final Pattern ID_PATTERN = Pattern.compile("(\\d{1,5})");


    public TelegramBotPetShelterUpdatesListener(BotMessageService botMessageService, TelegramBot telegramBot,
                                                ReportService reportService, OwnerService ownerService) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
        this.reportService = reportService;
        this.ownerService = ownerService;

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
                            dogButton(chatId);
                            break;
                        }
                        case "КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК": {
                            catButton(chatId);
                            break;
                        }
                        case "ИНФО_СОБАКИ": {
                            dogInfoButton(chatId);
                            break;
                        }
                        case "ИНФО_КОШКИ": {
                            catInfoButton(chatId);
                            break;
                        }
                        case "КАК_ЗАБРАТЬ_СОБАКУ": {
                            wantToTakeDogButton(chatId);
                            break;
                        }
                        case "КАК_ЗАБРАТЬ_КОШКУ": {
                            wantToTakeCatButton(chatId);
                        }
                        case "ВОЛОНТЁР": {
                            callVoluntier(chatId);
                        }
                        case "ОТЧЁТ": {
                            sendOwnerHowReport(chatId);
                        }
                    }
                    continue;
                }
                // МЕНЮ КОМАНД:
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


                        saveOwnerReportToDB(updates);

                        break;

                    case "/myData":
                        addOwnerToDB(updates);
                        break;
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
     * метод покрыть тестом -> получить результат из обоих условий оператора
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
        SendMessage sendMessage = new SendMessage(chatId, "Здравствуйте. " +
                "Это чат-бот приюта для животных. " +
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
    private void catButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для кошек");
        InlineKeyboardButton catHouseInfoButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        catHouseInfoButton.callbackData("ИНФО_КОШКИ");
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
                .addRow(catHouseInfoButton)
                .addRow(catHouseHowToTakeButton)
                .addRow(catHouseOwnerReportButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //СОБАКИ
    private void dogButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для собак");
        InlineKeyboardButton dogHouseInfoButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        dogHouseInfoButton.callbackData("ИНФО_СОБАКИ");
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
                .addRow(dogHouseInfoButton)
                .addRow(dogHouseHowToTakeButton)
                .addRow(dogHouseOwnerReportButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void dogInfoButton(long chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, petShelter.getDescription()+"\n"+petShelter.getShelterAddress()+"\n"+petShelter.getSecurityContact()+"\n"+petShelter.getSafetyAdvice()+"\n"+petShelter.getHelpShelter());
        SendMessage sendMessage = new SendMessage(chatId, "Мы приветствуем вас в приюте «Мечта». Наш приют основан в 2015 году в г. Астана. За это время через наши руки прошло более 500 собак и 200 кошек. Мы являемся некоммерческой организацией, поэтому все средства к нашему существованию – это пожертвования неравнодушных жителей. Мы очень рады, что вы заглянули к нам!\n" +
                "«Мечта» и его жители находятся по адресу: ул. Защитников Животных д.1. Мы открыты для посещений каждый день с 11:00 до 18:00.\n\n" +
                "Борис на связи +77894561230. Он поможет вам оформить пропуск, а также ответить на интересующие вас вопросы.\n\n" +
                "Напоминаем, что во время нахождения на территории приюта посетители обязаны следовать указаниям сотрудников. Безопасность питомцев и посетителей в наших с вами руках.\n\n" +
                "Приют нуждается в физической и материальной помощи. Требуются сотрудники для создания благоприятных условий. Приют нуждается в кормах и медикаментах, а также хозяйственных принадлежностях – тряпках, полотенцах, одеялах, амуниции и игрушек для животных. Пиар приветствуется. Помочь приюту можно переводом денежных средств по реквизитам карт в банках, номерам электронных кошельков. Для согласования оказания физической и материальной поддержки свяжитесь с волонтёром. Подробнее о нашей деятельности вы можете узнать в социальных сетях. Пожалуйста, присоединяйтесь к нашим сообществам Вконтакте, Одноклассники и т.д.");
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser.parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void catInfoButton(long chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, petShelter.getDescription()+"\n"+petShelter.getShelterAddress()+"\n"+petShelter.getSecurityContact()+"\n"+petShelter.getSafetyAdvice()+"\n"+petShelter.getHelpShelter());
        SendMessage sendMessage = new SendMessage(chatId, "Мы приветствуем вас в приюте «Мечта». Наш приют основан в 2015 году в г. Астана. За это время через наши руки прошло более 500 собак и 200 кошек. Мы являемся некоммерческой организацией, поэтому все средства к нашему существованию – это пожертвования неравнодушных жителей. Мы очень рады, что вы заглянули к нам!\n" +
                "«Мечта» и его жители находятся по адресу: ул. Защитников Животных д.2. Мы открыты для посещений каждый день с 11:00 до 18:00.\n\n" +
                "Борис на связи +77894561230. Он поможет вам оформить пропуск, а также ответить на интересующие вас вопросы.\n\n" +
                "Напоминаем, что во время нахождения на территории приюта посетители обязаны следовать указаниям сотрудников. Безопасность питомцев и посетителей в наших с вами руках.\n\n" +
                "Приют нуждается в физической и материальной помощи. Требуются сотрудники для создания благоприятных условий. Приют нуждается в кормах и медикаментах, а также хозяйственных принадлежностях – тряпках, полотенцах, одеялах, амуниции и игрушек для животных. Пиар приветствуется. Помочь приюту можно переводом денежных средств по реквизитам карт в банках, номерам электронных кошельков. Для согласования оказания физической и материальной поддержки свяжитесь с волонтёром. Подробнее о нашей деятельности вы можете узнать в социальных сетях. Пожалуйста, присоединяйтесь к нашим сообществам Вконтакте, Одноклассники и т.д.");
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser.parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void wantToTakeDogButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode("Рады, что вы заинтересованы стать обладателем собаки! Прежде чем оформить документы, необходимо познакомиться с питомцем. Встреча и общение с животным проходят в присутствии и под наблюдением ответственного сотрудника приюта. Для оформления договора потребуется ваш паспорт РФ. Испытательный срок составляет 30 дней. В течение этого срока хозяин обязан присылать ежедневные отчеты (в чат-бот, на электронную почту, на WhatsApp и пр.). Выбранный способ отчетности также фиксируется в договоре.\n\n " +
                ":no_entry:Почему мы можем отказать в усыновлении?:no_entry:\n" +
                ":one:\tЖивотное не пошло с вами на контакт.\n" +
                ":two:\tУсловия будущего проживания с вами не соответствуют необходимым для конкретного питомца.\n" +
                ":three:\tСемейные разногласия – нередко один член семьи готов завести питомца, а другой (другие) против. В таком случае мы отказываем.\n" +
                ":four:\tОтказ усыновителя оформить документы и пройти испытательный срок. Желание ускорить процесс будет для нас «красным маркером».\n" +
                ":five:\tЧасто мы отказываем в усыновлении, если в семье уже имеется какое-нибудь животное или несколько животных. В зависимости от характера нашего питомца, мы пристраиваем его в семьи без животных, либо с животными.\n"));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void wantToTakeCatButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode("Рады, что вы заинтересованы стать обладателем кошки! Прежде чем оформить документы, необходимо познакомиться с питомцем. Встреча и общение с животным проходят в присутствии и под наблюдением ответственного сотрудника приюта. Для оформления договора потребуется ваш паспорт РФ. Испытательный срок составляет 30 дней. В течение этого срока хозяин обязан присылать ежедневные отчеты (в чат-бот, на электронную почту, на WhatsApp и пр.). Выбранный способ отчетности также фиксируется в договоре.\n\n " +
                ":no_entry:Почему мы можем отказать в усыновлении?:no_entry:\n" +
                ":one:\tЖивотное не пошло с вами на контакт.\n" +
                ":two:\tУсловия будущего проживания с вами не соответствуют необходимым для конкретного питомца.\n" +
                ":three:\tСемейные разногласия – нередко один член семьи готов завести питомца, а другой (другие) против. В таком случае мы отказываем.\n" +
                ":four:\tОтказ усыновителя оформить документы и пройти испытательный срок. Желание ускорить процесс будет для нас «красным маркером».\n" +
                ":five:\tЧасто мы отказываем в усыновлении, если в семье уже имеется какое-нибудь животное или несколько животных. В зависимости от характера нашего питомца, мы пристраиваем его в семьи без животных, либо с животными.\n"));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void callVoluntier(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(":white_check_mark:Через некоторое время с Вами свяжется волонтёр и ответит на интересующие вопросы!:wink:"));
        telegramBot.execute(sendMessage);
    }

    private void sendOwnerHowReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode("Для отчёта нужно отправить текст :memo: описывающий: самочувствие питомца, рацион питания питомца, в каких условиях живёт питомец; и фото питомца :camera:"));
        InlineKeyboardButton textButton = new InlineKeyboardButton(EmojiParser.parseToUnicode("Отправить текст :memo:"));
        InlineKeyboardButton photoButton = new InlineKeyboardButton(EmojiParser.parseToUnicode("Отправить фото :camera:"));
        textButton.callbackData("ОТЧЁТ_ТЕКСТ");
        photoButton.callbackData("ОТЧЁТ_ФОТО");
        Keyboard keyboard = new InlineKeyboardMarkup().addRow(textButton).addRow(photoButton);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }


    /**
     * СОХРАНЕНИЕ ОТЧЁТА
     * проработать выбор / заполнение типа питомца
     * (в зависимости от выбора приюта)
     */
    private int saveOwnerReportToDB(List<Update> updates) {
        try {
            updates.stream() // получаем поток апдейтов из листа
                    .filter(update -> update.message() != null)// фильтруем по тем, что не нулл
                    .forEach(update -> { // итерируемся по ним
                        logger.info("Processing update: {}", update); // записываем лог апдейтов на уровне инфо
                        Message message = update.message(); // получаем сообщение из текущего обновления
                        Long chatId = message.chat().id(); // получаем идентификатор чата, к которому относится апдейт
                        String messageText = message.text(); // получаем текст сообщения
                        sendMessage(chatId, """
                                Для загрузки отчёта следуйте указаниям бота. Пожалуйста, заполните все пункты отчёта.
                                Команды для отчёта:\s
                                1. /ID - указать id питомца
                                2. /action - отчёт о поведении питомца
                                3. /health - отчёт о здоровье питомца
                                4. /feed - отчёт о питании питомца
                                5. /save - сохранить отчёт""");
                        OwnerReport ownerReport = new OwnerReport();
                        ownerReport.setChatId(chatId);
                        ownerReport.setReportDateTime(LocalDateTime.now());
                        ownerReport.setPetsType(PetsType.DOG); //ИСПРАВИТЬ!
                        switch (messageText) {
                            case "/ID":
                                sendMessage(chatId, "Укажите ID Вашего питомца");
                                String idMessage = message.text();
                                if (idMessage.matches("\\d+")) { // проверяем, что указано число
                                    long petsId = Long.parseLong(messageText.split(" ")[0]); // парсим сообщение в формат ID, 0 - без пробелов
                                    ownerReport.setPetId(petsId);
                                    sendMessage(chatId, "ID питомца записан.");
                                }

                            case "/action":
                                sendMessage(chatId, "Опишите кратко поведение питомца");
                                String actionMessage = message.text(); // сохраняем текстовое сообщение в переменную.
                            if (actionMessage.length() > 10) {
                                ownerReport.setPetsBehavior(actionMessage);
                                sendMessage(chatId, "Отчёт о поведении питомца записан");
                            }

                            case "/health":
                                sendMessage(chatId, "Опишите кратко самочувствие питомца");
                                String healthMessage = message.text();
                            if (healthMessage != null) {
                                ownerReport.setPetsHealth(healthMessage);
                            }

                            case "/feed":
                                sendMessage(chatId, "Опишите рацион питомца");
                                String feedMessage = message.text();
                            if (feedMessage != null) {
                                ownerReport.setNutrition(feedMessage);
                            }

                            case "/save":
                                reportService.save(ownerReport);
                                sendMessage(chatId, "Ваш отчёт загружен");

                            default:
                                if (messageText != null) {
                                   saveMessages(chatId, messageText);
                                }
                        }
                    });
        } catch (Exception e) {
            logger.error(e.getMessage()); // ловим ошибку
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // успешно завершаем метод, без падения
    }

    // СОХРАНЕНИЕ ПЕРСОНЫ в БД
    private int addOwnerToDB(List<Update> updates) {
        try {
            for (Update update : updates) {// итерируемся по ним
                logger.info("Processing update: {}", update);

                Message message = update.message();
                Long chatId = message.chat().id();
                String messageText = message.text();

                sendMessage(chatId, """
                        Укажите Вашу фамилию с заглавной буквы. Команды для заполнения данных:
                        1. /ln - указать фамилию
                        2. /fn -  указать имя
                        3. /phone -  указать номер телефона""");
                Owner owner = new Owner();
                switch (messageText) {
                    case "ln" -> {
                        sendMessage(chatId, "Укажите Вашу фамилию с заглавной буквы");
                        if (messageText != null) {
                            owner.setLastName(messageText);

                        } else {
                            owner.setLastName("auto: не указано");
                        }
                    }
                    case "/fn" -> {
                        sendMessage(chatId, "Укажите Ваше имя с заглавной буквы");
                        if (messageText != null) {
                            owner.setFirstName(messageText);
                        } else {
                            owner.setLastName("auto: не указано");
                        }
                    }
                    case "/phone" -> {
                        sendMessage(chatId, "Укажите Ваш телефон для связи в формате: " + "\n" +
                                "8-XXX-XXX-XX-XX");
                        if (messageText != null) {
                            owner.setPhoneNumber(messageText);
                        } else {
                            owner.setLastName("auto: не указано");
                        }
                    }
                }

            }
        } catch (Exception e) {
            logger.error(e.getMessage()); // ловим ошибку
        } return UpdatesListener.CONFIRMED_UPDATES_ALL; // успешно завершаем метод, без падения
    }
}

