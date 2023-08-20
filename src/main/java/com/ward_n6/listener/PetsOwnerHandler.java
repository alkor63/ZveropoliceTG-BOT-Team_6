package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.entity.pets.Pet;
import com.ward_n6.enums.PetsType;
import com.ward_n6.service.OwnerServiceImpl;
import com.ward_n6.service.PetServiceImpl;
import com.ward_n6.service.PetsOwnerServiceImpl;

import java.util.function.Consumer;

public class PetsOwnerHandler implements EventHandler {
private final PetsOwnerServiceImpl petsOwnerServiceImpl;
    private final TelegramBot telegramBot;

    private final PetsOwner petsOwner = new PetsOwner();
    private PetServiceImpl petService;
    private OwnerServiceImpl ownerService;

    public PetsOwnerHandler(PetsOwnerServiceImpl petsOwnerServiceImpl, TelegramBot telegramBot) {
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.telegramBot = telegramBot;

    }

    private Consumer<Update> actionOnNextMessage;

    @Override
    public boolean handle(Update update) {
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
        var text = update.message().text();
        actionOnNextMessage = upd -> {
            var ownerId = update.message().chat().id();

            petsOwner.setOwnerId(ownerId); // присваиваем ID пользователя
            Owner owner = ownerService.getOwnerById(ownerId);
            petsOwner.setOwner(owner);

            if (text.matches("\\d+")) { // проверяем, что число
                long petId = Long.parseLong(text); // парсим строку в число
                petsOwner.setPetId(petId);

                // КОШКИ
                if (TelegramBotPetShelterUpdatesListener.catSelect) { // если кошачий приют, ищем в кошках
                    petsOwner.setPetsType(PetsType.CAT); // присваиваем тип животного
                    Cat cat = petService.getCatById(petId);

                    if (cat.getId() != null) { // проверяем на кошках, что такой ID есть
                        petsOwner.setPet(cat);
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Спасибо за то, что Вы хороший человек. " +
                                        "В течение двух дней с Вами свяжется волонтёр для организации " +
                                        "переезда питомца в Ваш дом."));
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Питомец с указанным ID отсутствует в нашем приюте. " +
                                        "Уточните ID интересующего Вас питомца."));
                    }
                    // СОБАКИ
                } else if (TelegramBotPetShelterUpdatesListener.dogSelect) {
                    petsOwner.setPetsType(PetsType.DOG);
                    Pet dog = petService.getDogById(petId);
                    if (dog.getId() != null) { // проверяем, что такая собака есть в приюте
                        petsOwner.setPet(dog);
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Спасибо за то, что Вы хороший человек. " +
                                        "В течение двух дней с Вами свяжется волонтёр для организации " +
                                        "переезда питомца в Ваш дом."));

                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(),
                                "Питомец с указанным ID отсутствует в нашем приюте. " +
                                        "Уточните ID интересующего Вас питомца."));
                    }
                }
                petsOwnerServiceImpl.save(petsOwner);

            } else  telegramBot.execute(new SendMessage(update.message().chat().id(),
                    "Формат ID неверный, введите числовое значение ID выбранного питомца"));
        }; return true;

    }
}