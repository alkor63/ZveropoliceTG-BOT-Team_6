package com.ward_n6.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.vdurmont.emoji.EmojiParser;
import com.ward_n6.entity.Photo;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.entity.shelters.PetShelter;
import com.ward_n6.factory.HibernateSessionFactoryUtil;
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
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private Photo photos = new Photo();
    private final HibernateSessionFactoryUtil hibernateSessionFactoryUtil;
    private Buttons buttons;
    private Messager messager;


    public TelegramBotPetShelterUpdatesListener(BotMessageService botMessageService, TelegramBot telegramBot,
                                                PetsOwnerServiceImpl petsOwnerServiceImpl, OwnerReportServiceImpl ownerReportServiceImpl,
                                                OwnerServiceImpl ownerServiceImpl, PetServiceImpl petService, CatRepository catRepository, DogRepository dogRepository, PetBaseRepository petBaseRepository, PhotoRepository photoRepository, HibernateSessionFactoryUtil hibernateSessionFactoryUtil) {
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
        this.hibernateSessionFactoryUtil = hibernateSessionFactoryUtil;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    private static boolean startSelected = false; // переменная для подтверждения старта
    private static boolean reportSelect = false;
    private static boolean reportTextSelect = false;
    private static boolean reportPhotoSelect = false;
    protected static  boolean catSelect = false;
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
                            buttons.dogButton(chatId);
                            break;

                        case "КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК":
                            catSelect = true;
                            buttons.catButton(chatId);
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

                        case "ВОЛОНТЁР":
                            buttons.callVoluntier(chatId);
                            break;

                        case "ОТЧЁТ":
                            buttons.sendOwnerHowReport(chatId);
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
                        buttons.afterStartMenu(chatId, "/start");
                        break;
                    case "/dogs":
                        dogSelect = true;
                        if (startSelected) {
                            messager.sendMessage(chatId, "Вас приветствует приют для собак. " +
                                    "Выберите интересующий Вас пункт меню.");
                        }
                        break;
                    case "/cats":
                        catSelect = true;
                        if (startSelected) {
                            messager.sendMessage(chatId, "Вас приветствует приют для кошек. " +
                                    "Выберите интересующий Вас пункт меню.");
                        }
                        break;

                    case "/photo":
                        if (dogSelect || catSelect) {
                            currentHandler = new PhotoHandler(telegramBot, photoRepository);
                            messager.sendMessage(chatId, "Отправьте фото Вашего питомца для отчёта.");
                        }
                        break;

                    case "/report":
                        if (dogSelect || catSelect) {
                            reportTextSelect = true;
                            currentHandler = new ReportHandler(ownerReportServiceImpl, telegramBot, petService,
                                    catRepository, dogRepository, petBaseRepository);
                            messager.sendMessage(chatId, EmojiParser.parseToUnicode(REPORT));
                        } else messager.sendMessage(chatId, "Пожалуйста, сначала выберите приют");
                        break;

                    case "/myData":
                        getOwnerDataSelect = true;
                        currentHandler = new OwnerHandler(ownerServiceImpl, telegramBot);
                        messager.sendMessage(chatId, PERSONAL_DATA_REQUEST);
                        break;

                    case "/takePet":
                        if (dogSelect || catSelect) {
                            currentHandler = new PetsOwnerHandler(petsOwnerServiceImpl, telegramBot, catRepository,
                                    dogRepository);
//                            Owner ownerCheck = ownerServiceImpl.getOwnerById(chatId);
//                            if (ownerCheck != null) { // проверяем, что пользователь есть в базе

                            messager.sendMessage(chatId, PET_ID_REQUEST_FOR_PET_BOOKING); // запрос ID для бронирования
                        }
                        break;
                    // ПРОВЕРКА РАБОТЫ МЕТОДА -- > ТРЕНИРОВКА НА КОШКАХ!
                    case "/1":
                        long petId = 27L;
                        Pet cat =catRepository.getById(petId);
                        if (cat != null) {
                            messager.sendMessage(chatId, cat.toString());
                        } else {
                            messager.sendMessage(chatId, "такой кошки нет");
                        }
                        break;

                    case "/volunteer":
                        messager.sendMessage(chatId,
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
}