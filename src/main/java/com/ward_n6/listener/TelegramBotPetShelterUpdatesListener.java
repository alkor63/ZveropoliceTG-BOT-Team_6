package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;
import com.ward_n6.entity.BotMessaging;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.repository.Impl.OwnerServiceImpl;
import com.ward_n6.repository.OwnerRepository;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.service.BotMessageService;
import com.ward_n6.service.PhotoService;
import com.ward_n6.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
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
    private OwnerRepository ownerRepository;

    private final OwnerServiceImpl ownerServiceImpl;

    private EventHandler currentHandler = null;

    private Owner owner = new Owner();

    public TelegramBotPetShelterUpdatesListener(BotMessageService botMessageService, TelegramBot telegramBot,
                                                ReportService reportService,
                                                OwnerRepository ownerRepository, OwnerServiceImpl ownerServiceImpl) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
        this.reportService = reportService;
        this.ownerRepository = ownerRepository;
        this.ownerServiceImpl = ownerServiceImpl;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private boolean startSelected = false; // переменная для подтверждения старта
    private boolean reportSelect = false;
    private boolean reportTextSelect = false;
    private boolean reportPhotoSelect = false;
    protected static boolean catSelect = false;
    protected static boolean dogSelect = false;
    protected static boolean getOwnerDataSelect = false;

    boolean reportSelect = false;
    boolean catSelect = false;
    boolean dogSelect = false;
    boolean getOwnerDataSelect = false;

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
                // новый блок - подсказка
                if (currentHandler != null) { // добавляем в метож хэндлер
                    if (currentHandler.handle(update)) {
                        currentHandler = null;
                    }
                }
                if (update.callbackQuery() != null) {
                    Long chatId = update.callbackQuery().message().chat().id();
                    CallbackQuery callbackQuery = update.callbackQuery();
                    String data = callbackQuery.data();
                    switch (data) {
                        case "КНОПКА_ПРИЮТ_ДЛЯ_СОБАК":
                            dogSelect = true;
                            dogButton(chatId);
                            break;

                        case "КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК":
                            catSelect = true;
                            catButton(chatId);
                            break;

                        case "ИНФО_СОБАКИ":
                            dogInfoButton(chatId);
                            break;

                        case "ИНФО_КОШКИ":
                            catInfoButton(chatId);
                            break;

                        case "КАК_ЗАБРАТЬ_СОБАКУ":
                            wantToTakeDogButton(chatId);
                            break;

                        case "КАК_ЗАБРАТЬ_КОШКУ":
                            wantToTakeCatButton(chatId);

                        case "ВОЛОНТЁР":
                            callVoluntier(chatId);

                        case "ОТЧЁТ":
                            sendOwnerHowReport(chatId);
                            break;
                    }
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
                        dogSelect = true;
                        if (startSelected) {
                            sendMessage(chatId, "Вас приветствует приют для собак. " +
                                    "Выберите интересующий Вас пункт меню.");
                        }
                        break;
                    case "/cats":
                        catSelect = true;
                        if (startSelected) {
                            sendMessage(chatId, "Вас приветствует приют для кошек. " +
                                    "Выберите интересующий Вас пункт меню.");
                        }
                        break;

                    case "/photo":
                        sendMessage(chatId, "Отправьте фото Вашего питомца для отчёта.");
//                        currentHandler = new PhotoHandler(telegramBot);
                        getPhoto(update);
////
                        break;

                    case "/report":
                        reportTextSelect = true;
                        currentHandler = new ReportHandler(reportService, telegramBot);
                        sendMessage(chatId, """
                                Для загрузки отчёта следуйте указаниям бота. Пожалуйста, заполните все пункты отчёта.
                                Команды для отчёта:\s
                                1. /ID - указать id питомца
                                2. /action - отчёт о поведении питомца
                                3. /health - отчёт о здоровье питомца
                                4. /feed - отчёт о питании питомца
                                5. /save - сохранить отчёт""");
                        break;

                    case "/myData":
                        getOwnerDataSelect = true;
                        currentHandler = new OwnerHandler(ownerServiceImpl, telegramBot);
                        sendMessage(chatId, """
                                Пожалуйста, укажите Ваши данные:
                                1. /ln - указать фамилию
                                2. /fn - указать имя
                                3. /phone - указать номер телефона
                                4. /save - сохранить мои контактные данные в базе приюта
                                5. /delete - удалить мои данные
                                """);
                        break;

                    case "/volunteer":
                        sendMessage(chatId,
                                petShelter.getCallVolonteer());
                        break;
                }
//                if (reportPhotoSelect) {
//                    getPhoto(update);
//                }

            }
        } catch (
                Exception e) {
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
    private void afterStartMenu(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОД ПРИВЕТСТВУЮЩЕМ СООБЩЕНИЕМ
        SendMessage sendMessage = new SendMessage(chatId, "Здравствуйте. Это чатбот приюта для животных. " + //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
                "Какой приют Вас интересует?");
        InlineKeyboardButton chooseDogHouseButton = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Приют для собак" + ":dog:"));
        chooseDogHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_СОБАК"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton chooseCatHouseButton = new InlineKeyboardButton(EmojiParser// ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Приют для кошек" + ":cat:"));
        chooseCatHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК"); // СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup(chooseDogHouseButton, chooseCatHouseButton); //СОЗДАЁМ КЛАВИАТУРУ
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }

    //КОШКИ:
    private void catButton(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА ПРИЮТА КОШЕК
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для кошек"); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
        InlineKeyboardButton catHouseInfoButton = new InlineKeyboardButton(EmojiParser  // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        catHouseInfoButton.callbackData("ИНФО_КОШКИ");  //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton catHouseHowToTakeButton = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Как взять кошку из приюта " + ":cat:"));
        catHouseHowToTakeButton.callbackData("КАК_ЗАБРАТЬ_КОШКУ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton catHouseOwnerReportButton = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Прислать отчёт о питомце " + ":memo:"));
        catHouseOwnerReportButton.callbackData("ОТЧЁТ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup() //СОЗДАЁМ КЛАВИАТУРУ
                .addRow(catHouseInfoButton)
                .addRow(catHouseHowToTakeButton)
                .addRow(catHouseOwnerReportButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }

    //СОБАКИ
    private void dogButton(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА ПРИЮТА СОБАК
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для собак"); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
        InlineKeyboardButton dogHouseInfoButton = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        dogHouseInfoButton.callbackData("ИНФО_СОБАКИ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton dogHouseHowToTakeButton = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Как взять собаку из приюта " + ":dog:"));
        dogHouseHowToTakeButton.callbackData("КАК_ЗАБРАТЬ_СОБАКУ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton dogHouseOwnerReportButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Прислать отчёт о питомце " + ":memo:"));
        dogHouseOwnerReportButton.callbackData("ОТЧЁТ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup() //СОЗДАЁМ КЛАВИАТУРУ
                .addRow(dogHouseInfoButton)
                .addRow(dogHouseHowToTakeButton)
                .addRow(dogHouseOwnerReportButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }

    private void dogInfoButton(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА КНОПКИ "ИНФО СОБАКИ"
        SendMessage sendMessage = new SendMessage(chatId, petShelter.getDescription()+"\n"+petShelter.getDogShelterAddress()+"\n"+petShelter.getSecurityContact()+"\n"+petShelter.getSafetyAdvice()+"\n"+petShelter.getHelpShelter()); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
//        SendMessage sendMessage = new SendMessage(chatId, "Мы приветствуем вас в приюте «Мечта». Наш приют основан в 2015 году в г. Астана. За это время через наши руки прошло более 500 собак и 200 кошек. Мы являемся некоммерческой организацией, поэтому все средства к нашему существованию – это пожертвования неравнодушных жителей. Мы очень рады, что вы заглянули к нам!\n"+
//                "«Мечта» и его жители находятся по адресу: ул. Защитников Животных д.1. Мы открыты для посещений каждый день с 11:00 до 18:00.\n\n"+
//                "Борис на связи +77894561230. Он поможет вам оформить пропуск, а также ответить на интересующие вас вопросы.\n\n"+
//                "Напоминаем, что во время нахождения на территории приюта посетители обязаны следовать указаниям сотрудников. Безопасность питомцев и посетителей в наших с вами руках.\n\n"+
//                "Приют нуждается в физической и материальной помощи. Требуются сотрудники для создания благоприятных условий. Приют нуждается в кормах и медикаментах, а также хозяйственных принадлежностях – тряпках, полотенцах, одеялах, амуниции и игрушек для животных. Пиар приветствуется. Помочь приюту можно переводом денежных средств по реквизитам карт в банках, номерам электронных кошельков. Для согласования оказания физической и материальной поддержки свяжитесь с волонтёром. Подробнее о нашей деятельности вы можете узнать в социальных сетях. Пожалуйста, присоединяйтесь к нашим сообществам Вконтакте, Одноклассники и т.д.");
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser.parseToUnicode(":two_hearts: "+"Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:")); // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup() //СОЗДАЁМ КЛАВИАТУРУ
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }
    private void catInfoButton(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА КНОПКИ "ИНФО КОШКИ"
        SendMessage sendMessage = new SendMessage(chatId, petShelter.getDescription()+"\n"+petShelter.getCatShelterAddress()+"\n"+petShelter.getSecurityContact()+"\n"+petShelter.getSafetyAdvice()+"\n"+petShelter.getHelpShelter());//ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
//        SendMessage sendMessage = new SendMessage(chatId, "Мы приветствуем вас в приюте «Мечта». Наш приют основан в 2015 году в г. Астана. За это время через наши руки прошло более 500 собак и 200 кошек. Мы являемся некоммерческой организацией, поэтому все средства к нашему существованию – это пожертвования неравнодушных жителей. Мы очень рады, что вы заглянули к нам!\n"+
//                "«Мечта» и его жители находятся по адресу: ул. Защитников Животных д.2. Мы открыты для посещений каждый день с 11:00 до 18:00.\n\n"+
//                "Борис на связи +77894561230. Он поможет вам оформить пропуск, а также ответить на интересующие вас вопросы.\n\n"+
//                "Напоминаем, что во время нахождения на территории приюта посетители обязаны следовать указаниям сотрудников. Безопасность питомцев и посетителей в наших с вами руках.\n\n"+
//                "Приют нуждается в физической и материальной помощи. Требуются сотрудники для создания благоприятных условий. Приют нуждается в кормах и медикаментах, а также хозяйственных принадлежностях – тряпках, полотенцах, одеялах, амуниции и игрушек для животных. Пиар приветствуется. Помочь приюту можно переводом денежных средств по реквизитам карт в банках, номерам электронных кошельков. Для согласования оказания физической и материальной поддержки свяжитесь с волонтёром. Подробнее о нашей деятельности вы можете узнать в социальных сетях. Пожалуйста, присоединяйтесь к нашим сообществам Вконтакте, Одноклассники и т.д.");
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser.parseToUnicode(":two_hearts: "+"Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:")); // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup() //СОЗДАЁМ КЛАВИАТУРУ
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }

    private void wantToTakeDogButton(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА КНОПКИ "КАК ЗАБРАТЬ СОБАКУ"
        SendMessage sendMessage = new SendMessage(chatId,EmojiParser.parseToUnicode( petShelter.getTakePet()+"\n\n"+petShelter.getProhibitedTakePet()+"\n")); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup() //СОЗДАЁМ КЛАВИАТУРУ
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }
    private void wantToTakeCatButton(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА КНОПКИ "КАК ЗАБРАТЬ КОШКУ"
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(petShelter.getTakePet()+"\n\n"+petShelter.getProhibitedTakePet()+"\n")); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
//        SendMessage sendMessage = new SendMessage(chatId,EmojiParser.parseToUnicode( "Рады, что вы заинтересованы стать обладателем кошки! Прежде чем оформить документы, необходимо познакомиться с питомцем. Встреча и общение с животным проходят в присутствии и под наблюдением ответственного сотрудника приюта. Для оформления договора потребуется ваш паспорт РФ. Испытательный срок составляет 30 дней. В течение этого срока хозяин обязан присылать ежедневные отчеты (в чат-бот, на электронную почту, на WhatsApp и пр.). Выбранный способ отчетности также фиксируется в договоре.\n\n "+
//                ":no_entry:Почему мы можем отказать в усыновлении?:no_entry:\n" +
//                ":one:\tЖивотное не пошло с вами на контакт.\n" +
//                ":two:\tУсловия будущего проживания с вами не соответствуют необходимым для конкретного питомца.\n" +
//                ":three:\tСемейные разногласия – нередко один член семьи готов завести питомца, а другой (другие) против. В таком случае мы отказываем.\n" +
//                ":four:\tОтказ усыновителя оформить документы и пройти испытательный срок. Желание ускорить процесс будет для нас «красным маркером».\n" +
//                ":five:\tЧасто мы отказываем в усыновлении, если в семье уже имеется какое-нибудь животное или несколько животных. В зависимости от характера нашего питомца, мы пристраиваем его в семьи без животных, либо с животными.\n"));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser // ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup() //СОЗДАЁМ КЛАВИАТУРУ
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);
    }

    private void callVoluntier(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА КНОПКИ "ПОЗВАТЬ ВОЛОНТЁРА"
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(petShelter.getCallVolonteer())); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
        telegramBot.execute(sendMessage);
    }

    private void sendOwnerHowReport(long chatId) { //МЕТОД ВЫЗОВА КНОПОК ПОСЛЕ ВЫБОРА КНОПКИ "ОТПРАВИТЬ ОТЧЁТ"
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(petShelter.getReportInfo())); //ЗАДАЁМ ТЕКСТ СООБЩЕНИЯ
        InlineKeyboardButton textButton = new InlineKeyboardButton(EmojiParser.parseToUnicode("Отправить текст :memo:"));// ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
        InlineKeyboardButton photoButton = new InlineKeyboardButton(EmojiParser.parseToUnicode("Отправить фото :camera:"));// ИНИЦИАЛИЗИРУЕМ КНОПКУ И ЗАДАЁМ ЕЙ ТЕКСТ
        textButton.callbackData("ОТЧЁТ_ТЕКСТ"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        photoButton.callbackData("ОТЧЁТ_ФОТО"); //СОЗДАЁМ КНОПКУ ПРИСВАИВАЯ ЕЙ СВОЙ ОБРАЗНО id
        Keyboard keyboard = new InlineKeyboardMarkup().addRow(textButton).addRow(photoButton); //СОЗДАЁМ КЛАВИАТУРУ
        sendMessage.replyMarkup(keyboard); //ИНИЦИАЛИЗИРУЕМ КЛАВИАТУРУ В СООБЩЕНИЕ
        telegramBot.execute(sendMessage);

    }

    boolean choice = false;

    private boolean handleButtonPress(Update update) {
        String callbackData = update.callbackQuery().data();

        // Проверяем, какая кнопка была нажата
        if (callbackData.equals("ОТЧЁТ_ТЕКСТ")) {
            // Устанавливаем значение choice в true
            reportTextSelect = true;
            choice = reportTextSelect;
        } else if (callbackData.equals("ОТЧЁТ_ФОТО")) {
            reportPhotoSelect = true;
            choice = reportPhotoSelect;
            // Обработка нажатия кнопки "Отправить фото"
        }
        return choice;
    }


    // ****************************** СОХРАНЯЕМ ОВНЕРА ***********************
    boolean isLastName = false;
    boolean isFirstName = false;
    boolean isPhone = false;

    //    private static final Pattern OWNER_PHONE_NUMBER_PATTERN = Pattern.compile("\\d-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    private static final Pattern OWNER_DATA_PATTERN = Pattern.compile("([А-я])\\s|\\n + ([А-я]) \\s|\\n +" +
            "(\\d-\\d{3}-\\d{3}-\\d{2}-\\d{2})"); //символы и \\s - пробел, в (...) - группы паттерна

    // СОХРАНЕНИЕ ПЕРСОНЫ в БД
    private void saveOwner(long chatId, String messageText) {
        Matcher matcher = OWNER_DATA_PATTERN.matcher(messageText); // поиск соответствия сообщения заданным параметрам
        if (matcher.find()) {
            Owner owner = new Owner();
            String ownerLastName = matcher.group(1);
            String ownerFirstName = matcher.group(2);
            String ownerPhone = matcher.group(3);
            owner.setLastName(ownerLastName);
            owner.setFirstName(ownerFirstName);
            owner.setPhoneNumber(ownerPhone);
            ownerRepository.save(owner);
            sendMessage(chatId, "Ваши данные добавлена в нашу базу: " + owner.toString() +
                    "Для удаления Ваших данных из нашей базы обратитесь к волонтёру");
        } else {
            sendMessage(chatId, "Данные указаны неверно. Пожалуйста, введите Ваши данные в указанном формате.");
        }
    }


    //**************************************** фОТО ******************************
    @Value("${path.to.file}")
    String folderPath ; // путь к файлам

    private void getPhoto(Update update) {
        PhotoSize[] messagePhoto = update.message().photo(); // получаем сообщение из текущего обновления
        Long chatId =update.message().chat().id(); // получаем идентификатор чата, к которому относится апдейт
//       PhotoSize[] messagePhoto = message.photo();
        if (messagePhoto != null) {
            var photo = update.message().photo()[3]; // 3 - самое лучшее качество
            var getFile = telegramBot.execute(new GetFile(photo.fileId()));
            var outFile = new File(folderPath, (photo.fileId() + "-owner-" + chatId + ".jpeg")); // добавлено
            try (var in = new URL(telegramBot.getFullFilePath(getFile.file())).openStream();
                 var out = new FileOutputStream(outFile)) {
                     // для примера просто сделал случайное название файла, лучше прописать путь и расширение
                in.transferTo(out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}