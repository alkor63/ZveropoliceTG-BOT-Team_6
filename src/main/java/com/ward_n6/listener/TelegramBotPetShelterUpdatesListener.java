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
import com.ward_n6.entity.Photo;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.repository.PhotoRepository;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.repository.pets.PetBaseRepository;
import com.ward_n6.service.BotMessageService;
import com.ward_n6.service.OwnerReportServiceImpl;
import com.ward_n6.service.OwnerServiceImpl;
import com.ward_n6.service.PetsOwnerServiceImpl;
import com.ward_n6.service.pets.PetServiceImpl;
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

import static com.ward_n6.listener.MessageStringsConstants.*;


@Component
public class TelegramBotPetShelterUpdatesListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);

    private final BotMessageService botMessageService;
    private final TelegramBot telegramBot;
    private final PetsOwnerServiceImpl petsOwnerServiceImpl;
    private final PetShelter petShelter = new PetShelter() {
    };

    private final OwnerReportServiceImpl ownerReportServiceImpl;

    private final OwnerServiceImpl ownerServiceImpl;

    private EventHandler currentHandler = null;

    private Owner owner = new Owner();
    private final PetServiceImpl petService;
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final PetBaseRepository petBaseRepository;
    private final PhotoRepository photoRepository;
    Photo photos = new Photo();




    public TelegramBotPetShelterUpdatesListener(BotMessageService botMessageService, TelegramBot telegramBot,
                                                PetsOwnerServiceImpl petsOwnerServiceImpl, OwnerReportServiceImpl ownerReportServiceImpl,
                                                OwnerServiceImpl ownerServiceImpl, PetServiceImpl petService, CatRepository catRepository, DogRepository dogRepository, PetBaseRepository petBaseRepository, PhotoRepository photoRepository) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.ownerReportServiceImpl = ownerReportServiceImpl;
        this.ownerServiceImpl = ownerServiceImpl;
        this.petService = petService;
        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
        this.petBaseRepository = petBaseRepository;
        this.photoRepository = photoRepository;
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
                            break;

                        case "ВОЛОНТЁР":
                            callVoluntier(chatId);
                            break;

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
                        if (dogSelect || catSelect) {
                            currentHandler = new PhotoHandler(telegramBot, photoRepository);
                            sendMessage(chatId, "Отправьте фото Вашего питомца для отчёта.");
                        }
                        break;

                    case "/report":
                        if (dogSelect || catSelect) {
                            reportTextSelect = true;
                            currentHandler = new ReportHandler(ownerReportServiceImpl, telegramBot, petService,
                                    catRepository, dogRepository, petBaseRepository);
                            sendMessage(chatId, EmojiParser.parseToUnicode(REPORT));
                        } else sendMessage(chatId, "Пожалуйста, сначала выберите приют");
                        break;

                    case "/myData":
                        getOwnerDataSelect = true;
                        currentHandler = new OwnerHandler(ownerServiceImpl, telegramBot);
                        sendMessage(chatId, PERSONAL_DATA_REQUEST);
                        break;

                    case "/takePet":
                        if (dogSelect || catSelect) {
                            currentHandler = new PetsOwnerHandler(petsOwnerServiceImpl, telegramBot, catRepository,
                                    dogRepository);
//                            Owner ownerCheck = ownerServiceImpl.getOwnerById(chatId);
//                            if (ownerCheck != null) { // проверяем, что пользователь есть в базе

                            sendMessage(chatId, PET_ID_REQUEST_FOR_PET_BOOKING);
                        }
                        break;
                        // проверка метода
                    case "/1":

                      sendMessage(chatId, catRepository.getById(1L).toString());
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
        SendMessage sendMessage = new SendMessage(chatId, SHELTER_INFO);
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
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
        SendMessage sendMessage = new SendMessage(chatId, SHELTER_INFO);
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
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
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_TO_TAKE_PET));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void wantToTakeCatButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_TO_TAKE_PET));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    private void callVoluntier(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(":white_check_mark:Через " +
                "некоторое время с Вами свяжется волонтёр и ответит на интересующие вопросы!:wink:"));
        telegramBot.execute(sendMessage);
    }


    private void sendOwnerHowReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_REPORT));
//        InlineKeyboardButton textButton = new InlineKeyboardButton(EmojiParser
//                .parseToUnicode("Отправить текст :memo:"));
//        InlineKeyboardButton photoButton = new InlineKeyboardButton(EmojiParser
//                .parseToUnicode("Отправить фото :camera:"));
//        textButton.callbackData("ОТЧЁТ");
//        photoButton.callbackData("ОТЧЁТ_ФОТО");
//        Keyboard keyboard = new InlineKeyboardMarkup().addRow(textButton).addRow(photoButton);
//        sendMessage.replyMarkup(keyboard);
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


    //**************************************** фОТО ******************************
    @Value("${path.to.file}")
    String folderPath; // путь к файлам

    private void getPhoto(Update update) {
        PhotoSize[] messagePhoto = update.message().photo(); // получаем сообщение из текущего обновления
        Long chatId = update.message().chat().id(); // получаем идентификатор чата, к которому относится апдейт
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