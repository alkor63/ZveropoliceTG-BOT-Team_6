package com.ward_n6.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.vdurmont.emoji.EmojiParser;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.reports.Photo;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.listener.handlers.*;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.repository.reports.PhotoRepository;
import com.ward_n6.service.BotMessageService;
import com.ward_n6.service.owners.OwnerReportServiceImpl;
import com.ward_n6.service.owners.OwnerServiceImpl;
import com.ward_n6.service.owners.PetsOwnerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.ward_n6.listener.MessageStringsConstants.PERSONAL_DATA_REQUEST;
import static com.ward_n6.listener.MessageStringsConstants.REPORT;
import static com.ward_n6.listener.handlers.ReportHandler.isCatId;
import static com.ward_n6.listener.handlers.ReportHandler.isDogId;


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
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private final PhotoRepository photoRepository;
    private final Buttons buttons;
    private final ChatMessager chatMessager;
    private final PetsOwnerFactories petsOwnerFactories;


    private Owner owner = new Owner();
    private PetsOwner petsOwner = new PetsOwner();
    private Photo photos = new Photo();

    private EventHandler currentHandler = null;

    public TelegramBotPetShelterUpdatesListener(BotMessageService botMessageService, TelegramBot telegramBot,
                                                PetsOwnerServiceImpl petsOwnerServiceImpl,
                                                OwnerReportServiceImpl ownerReportServiceImpl,
                                                OwnerServiceImpl ownerServiceImpl,
                                                CatRepository catRepository, DogRepository dogRepository,
                                                PhotoRepository photoRepository, Buttons buttons,
                                                ChatMessager chatMessager, PetsOwnerFactories petsOwnerFactories) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.ownerReportServiceImpl = ownerReportServiceImpl;
        this.ownerServiceImpl = ownerServiceImpl;
        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
        this.photoRepository = photoRepository;
        this.buttons = buttons;
        this.chatMessager = chatMessager;


        this.petsOwnerFactories = petsOwnerFactories;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    //ПЕРЕМЕННЫЕ-ФЛАГИ
    public static boolean startSelected = false; // переменная для подтверждения старта
    public static boolean reportSelect = false;
    public static boolean catSelect = false;
    public static boolean dogSelect = false;
    public static boolean getOwnerDataSelect = false;


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
                if (currentHandler != null) { // добавляем в метод хэндлер
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
                            catSelect = false;
                            isCatId = false;
                            isDogId = false;
                            buttons.dogButton(chatId);
                            break;

                        case "КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК":
                            catSelect = true;
                            dogSelect = false;
                            isCatId = false;
                            isDogId = false;
                            buttons.catButton(chatId);
                            break;

                        case "ЗАРЕГИСТРИРОВАТЬСЯ_В_ПРИЮТЕ":
                            buttons.sendOwnerData(chatId);
                            break;

                        case "ИНФО_СОБАКИ":
                            buttons.dogInfoButton(chatId);
                            break;

                        case "ИНФО_КОШКИ":
                            buttons.catInfoButton(chatId);
                            break;

                        case "КАК_ЗАБРАТЬ_СОБАКУ":
                            buttons.wantToTakeDogButton(chatId);
                            break;

                        case "КАК_ЗАБРАТЬ_КОШКУ":
                            buttons.wantToTakeCatButton(chatId);
                            break;

                        case "РЕКОМЕНДАЦИЯ_СОБАКИ":
                            buttons.dogRecommendation(chatId);
                            break;
                        case "РЕКОМЕНДАЦИЯ_КОШКИ":
                            buttons.catRecommendation(chatId);
                            break;

                        case "ЗАБРОНИРОВАТЬ_ПИТОМЦА":
                            buttons.reservePet(chatId);
                            break;

                        case "ВОЛОНТЁР":
                            buttons.callVoluntier(chatId);
                            break;

                        case "ОТЧЁТ":
                            buttons.sendOwnerHowReport(chatId);
                            break;

                    }

                } else if (update.message() != null && update.message().text() != null) { // проверка, чтобы не было ошибки при нажатии кнопок
                    // меню команд:
                    Message message = update.message(); // получаем сообщение из текущего обновления
                    Long chatId = message.chat().id(); // получаем идентификатор чата, к которому относится апдейт
                    String messageText = message.text(); // получаем текст сообщения

                    switch (messageText) {
                        case "/start":
                            startSelected = true;
                            buttons.afterStartMenu(chatId, "/start");
                            break;

                        case "/dogs":
                            dogSelect = true;
                            catSelect = false;
                            isCatId = false;
                            isDogId = false;
                            if (startSelected) {
                                buttons.dogButton(chatId);
                            }
                            break;

                        case "/cats":
                            catSelect = true;
                            dogSelect = false;
                            isCatId = false;
                            isDogId = false;
                            if (startSelected) {
                                buttons.catButton(chatId);
                            }
                            break;

                        case "/photo":
                            if (isCatId || isDogId) {
                                chatMessager.sendMessage(chatId, "Загрузите фото");
                                currentHandler = new PhotoHandler(telegramBot, photoRepository, ownerReportServiceImpl);
                            } else chatMessager.sendMessage(chatId, "Сначала укажите ID питомца \n /ID");
                            break;

                        case "/report":
                            if (dogSelect || catSelect) {
                                reportSelect = true;

                                currentHandler = new ReportHandler(ownerReportServiceImpl, telegramBot, photoRepository,
                                        petsOwnerFactories);
                                chatMessager.sendMessage(chatId, EmojiParser.parseToUnicode(REPORT));

                            } else {
                                chatMessager.sendMessage(chatId, """
                                         Пожалуйста, сначала выберите приют:
                                        """);
                                buttons.afterStartMenu(chatId, "/start");
                            }
                            break;

                        case "/myData":
                            getOwnerDataSelect = true;
                            currentHandler = new OwnerHandler(ownerServiceImpl, telegramBot);
                            chatMessager.sendMessage(chatId, PERSONAL_DATA_REQUEST);
                            break;

                        case "/takePet":
                            if (dogSelect || catSelect) {
                                if (petsOwnerFactories.ownerFactory(chatId) != null) { // проверяем, что пользователь есть в базе
                                    chatMessager.sendMessage(chatId, """
                                            Введите или нажмите команду:
                                            /ID"""); // запрос ID для бронирования

                                    if (catSelect) {
                                        currentHandler = new CatOwnerHandler(petsOwnerServiceImpl, telegramBot,
                                                catRepository, petsOwnerFactories);

                                    } else if (dogSelect) {
                                        currentHandler = new DogOwnerHandler(petsOwnerServiceImpl, telegramBot,
                                                dogRepository, petsOwnerFactories, chatMessager);
                                    }
                                } else {
                                    chatMessager.sendMessage(chatId, """
                                            Пожалуйста, сначала зарегистрируйтесь в нашем приюте:
                                            /myData""");
                                }
                            }
                            break;

                        case "/volunteer":
                            buttons.callVoluntier(chatId);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage()); // ловим ошибку
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // успешно завершаем метод, без падения
    }
}