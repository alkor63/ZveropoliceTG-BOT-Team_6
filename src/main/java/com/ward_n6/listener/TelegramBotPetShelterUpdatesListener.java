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
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.service.BotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class TelegramBotPetShelterUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);

    private final BotMessageService botMessageService;
    private final TelegramBot telegramBot;

    private PetShelter petShelter = new PetShelter() {
    };



    public TelegramBotPetShelterUpdatesListener(TelegramBot telegramBot, BotMessageService botMessageService) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    boolean startSelected = false; // переменная для подтверждения старта

    /** ОСНОВНОЙ МЕТОД
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
                        case "ИНФО_СОБАКИ":{
                            dogInfoButton(chatId);
                            break;
                        }
                        case "ИНФО_КОШКИ":{
                            catInfoButton(chatId);
                            break;
                        }
                        case "КАК_ЗАБРАТЬ_СОБАКУ":{
                            wantToTakeDogButton(chatId);
                            break;
                        }
                        case "КАК_ЗАБРАТЬ_КОШКУ":{
                            wantToTakeCatButton(chatId);
                        }
                        case "ВОЛОНТЁР":{
                            callVoluntier(chatId);
                        }
                        case "ОТЧЁТ":{
                            sendOwnerHowReport(chatId);
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
                        afterStartMenu(chatId);
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
                    case "/volunteer":
                        sendMessage(chatId,
                                "Вы позвали волонтёра приюта. Ожидайте, с Вами свяжутся " +
                                        "в течение 30 мин.");
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

}
