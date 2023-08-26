package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.listener.ChatMessager;
import com.ward_n6.listener.PetsOwnerFactories;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.service.OwnerServiceImpl;
import com.ward_n6.service.PetsOwnerServiceImpl;
import com.ward_n6.service.pets.PetServiceImpl;

import java.time.LocalDate;
import java.util.function.Consumer;

import static com.ward_n6.listener.MessageStringsConstants.PET_ID_REQUEST_FOR_PET_BOOKING;

/**
 * класс для обработки и сохранения связки овнер-питомец и отслеживания испытательгых сроков
 * НЕ РАБОТАЕТ!!!!!
 */
public class PetsOwnerHandler implements EventHandler {
    private final PetsOwnerServiceImpl petsOwnerServiceImpl;
    private final TelegramBot telegramBot;

    private final PetsOwner petsOwner = new PetsOwner();
    private PetServiceImpl petService;
    private OwnerServiceImpl ownerService;
    private final CatRepository catRepository;
    private final DogRepository dogRepository;
    private PetsOwnerFactories petsOwnerFactories;

    public PetsOwnerHandler(PetsOwnerServiceImpl petsOwnerServiceImpl, TelegramBot telegramBot, CatRepository catRepository, DogRepository dogRepository) {
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.telegramBot = telegramBot;

        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
    }

    private Consumer<Update> actionOnNextMessage;
    private ChatMessager chatMessager;
private String PET_NOT_FOUND = "Питомец с указанным ID отсутствует в нашем приюте. Уточните ID интересующего Вас питомца.";

    @Override
    public boolean handle(Update update) {
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
        var text = update.message().text();
        switch (text) {
            case "/ID":
                telegramBot.execute(new SendMessage(update.message().chat().id(),
                        PET_ID_REQUEST_FOR_PET_BOOKING));
                actionOnNextMessage = upd -> {
                    var ownerId = update.message().chat().id();
                    var idMessage = upd.message().text();
                    if (idMessage.matches("\\d+")) { // проверяем, что число
                        long petId = Long.parseLong(idMessage); // парсим строку в число

                        // КОШКИ
                        if (TelegramBotPetShelterUpdatesListener.catSelect) { // если кошачий приют, ищем в кошках
//                            petsOwner.setPetsType(PetsType.CAT); // присваиваем тип животного
                            if (petsOwnerFactories.catFactory(petId, ownerId) != null) {
                           //     petsOwner.setPetId(petId);
                                petsOwnerFactories.catFactory(petId, ownerId).setOwnerId(ownerId);
                                chatMessager.sendMessage(ownerId, "Кошка " + petsOwnerFactories.catFactory(petId, ownerId).toString() +
                                        " забронирована за Вами");
                            } else {
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        PET_NOT_FOUND));
                            }
                        }

                            // СОБАКИ
                         else if (TelegramBotPetShelterUpdatesListener.dogSelect) {
//                            petsOwner.setPetsType(PetsType.DOG);
                            if (petsOwnerFactories.dogFactory(petId, ownerId) != null) { // проверяем, что такая собака есть в приюте
//                                petsOwner.setPet(petsOwnerFactories.dogFactory(petId, ownerId));
                                petsOwnerFactories.dogFactory(petId, ownerId).setOwnerId(ownerId);
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        "Собака " + petsOwnerFactories.catFactory(petId, ownerId).toString()+ "забронирована за Вами."));

                            } else {
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        PET_NOT_FOUND));
                            }
                        }
                        petsOwner.setOwnerId(ownerId); // присваиваем ID пользователя
                        petsOwner.setPetId(petId); // записываем ID питомца
                        petsOwner.setStartDate(LocalDate.now());
                        petsOwnerServiceImpl.save(petsOwner);
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Спасибо за Вашу доброту. Скоро с Вами свяжется волонтёр для, чтобы оформить " +
                                        "обсудить побробности перезда питомца в Ваш дом и" +
                                        "оформить документы!"));
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Формат ID неверный, введите числовое значение ID выбранного питомца, " +
                                        "нажмите или введите /ID"));
                    }
                };return true;
        }  return false;
    }
}