package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsType;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.service.OwnerServiceImpl;
import com.ward_n6.service.pets.PetServiceImpl;
import com.ward_n6.service.PetsOwnerServiceImpl;

import java.util.function.Consumer;
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

    public PetsOwnerHandler(PetsOwnerServiceImpl petsOwnerServiceImpl, TelegramBot telegramBot, CatRepository catRepository, DogRepository dogRepository) {
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.telegramBot = telegramBot;

        this.catRepository = catRepository;
        this.dogRepository = dogRepository;
    }

    private Consumer<Update> actionOnNextMessage;

    @Override
    public boolean handle(Update update) {
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
//        telegramBot.execute(new SendMessage(update.message().chat().id(),
//                """
//                        Для того, чтобы взять из приюта питомца, нужно знать его ID.
//                        Введите или нажмите команду:
//                        /ID
//                        """));
        var text = update.message().text();
//            switch (text) {
//                case "/ID":
//                    telegramBot.execute(new SendMessage(update.message().chat().id(),
//                            """
//                                    Введите номер ID интересующего питомца, который Вам сообщил волонтёр.
//                                    Если Вы не знаете ID питомца, позовите волонтёра:
//                                    /volunteer"""));
                    actionOnNextMessage = upd -> {
                        var ownerId = update.message().chat().id();
                        var idMessage = upd.message().text();
                        if (idMessage.matches("\\d+")) { // проверяем, что число
                            long petId = Long.parseLong(idMessage); // парсим строку в число

                            // КОШКИ
                            if (TelegramBotPetShelterUpdatesListener.catSelect) { // если кошачий приют, ищем в кошках
                                petsOwner.setPetsType(PetsType.CAT); // присваиваем тип животного
                                Pet cat = catRepository.getById(petId);

                                if (cat != null) { // проверяем на кошках, что такой ID есть
                                    petsOwner.setPet(cat);
                                    cat.setOwnerId(ownerId); // заносим ID пользователя в таблицу питомца
                                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                                            "Питомец" + catRepository.getById(petId).getPetsType().getTitle() + " "
                                                    + catRepository.getById(petId).getId() + " "
                                                    + catRepository.getById(petId).getPetName() + "забронирован за Вами"));
                                } else {
                                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                                            "Питомец с указанным ID отсутствует в нашем приюте. " +
                                                    "Уточните ID интересующего Вас питомца."));
                                }

                                // СОБАКИ
                            } else if (TelegramBotPetShelterUpdatesListener.dogSelect) {
                                petsOwner.setPetsType(PetsType.DOG);
                                Pet dog = petService.getDogById(petId);
                                if (dog != null) { // проверяем, что такая собака есть в приюте
                                    petsOwner.setPet(dog);
                                    dog.setOwnerId(ownerId);
                                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                                            "Питомец" + dogRepository.getById(petId).getPetsType().getTitle() + " "
                                                    + dogRepository.getById(petId).getId() + " "
                                                    + dogRepository.getById(petId).getPetName() + "забронирован за Вами."));

                                } else {
                                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                                            "Питомец с указанным ID отсутствует в нашем приюте. " +
                                                    "Уточните ID интересующего Вас питомца."));
                                }
                            }

                            petsOwner.setOwnerId(ownerId); // присваиваем ID пользователя
                            petsOwner.setPetId(petId); // записываем ID питомца
                            Owner owner = ownerService.getOwnerById(ownerId);
                            petsOwner.setOwner(owner);
                            petsOwnerServiceImpl.save(petsOwner);
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Спасибо. Скоро с Вами свяжется волонтёр!"));
                        } else {
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Формат ID неверный, введите числовое значение ID выбранного питомца, нажмите или введите /ID"));
                        }
                    };return true;
        }

}