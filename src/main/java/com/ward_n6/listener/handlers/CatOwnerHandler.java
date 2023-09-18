package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Cat;
import com.ward_n6.listener.PetsOwnerFactories;
import com.ward_n6.repository.pets.CatRepository;
import com.ward_n6.service.owners.PetsOwnerServiceImpl;
import org.springframework.jdbc.core.BatchUpdateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Consumer;

import static com.ward_n6.listener.MessageStringsConstants.PET_ID_REQUEST_FOR_PET_BOOKING;
@Component
public class CatOwnerHandler implements EventHandler {
    private final PetsOwnerServiceImpl petsOwnerServiceImpl;
    private final TelegramBot telegramBot;

    private final CatRepository catRepository;
    private PetsOwnerFactories petsOwnerFactories;

    private Consumer<Update> actionOnNextMessage;
    private String PET_NOT_FOUND = "Питомец с указанным ID отсутствует в нашем приюте. Уточните ID интересующего Вас питомца. \n /ID";
    private Cat cat = new Cat();
    private PetsOwner petsOwner = new PetsOwner();

    public CatOwnerHandler(PetsOwnerServiceImpl petsOwnerServiceImpl, TelegramBot telegramBot,
                           CatRepository catRepository, PetsOwnerFactories petsOwnerFactories) {
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.telegramBot = telegramBot;
        this.catRepository = catRepository;
        this.petsOwnerFactories = petsOwnerFactories;

    }

    @Override
    public boolean handle(Update update) {
        if (actionOnNextMessage != null) {
            actionOnNextMessage.accept(update);
            actionOnNextMessage = null;
            return false;
        }
        if (update.message() != null && update.message().text() != null) { // проверка, чтобы не "зависали" кнопки
            var text = update.message().text();
            switch (text) {
                case "/ID":
                    telegramBot.execute(new SendMessage(update.message().chat().id(),
                            PET_ID_REQUEST_FOR_PET_BOOKING));
                    actionOnNextMessage = upd -> {
                        var ownerId = update.message().chat().id().longValue();
                        var idMessage = upd.message().text();
                        if (idMessage.matches("\\d+")) { // проверяем, что число
                            long petId = Long.parseLong(idMessage); // парсим строку в число
                            cat = petsOwnerFactories.catFactory(petId, ownerId);
                            if (cat != null && (cat.getOwnerId() == null || cat.getOwnerId() == 0)) {
                                cat.setOwnerId(ownerId);
                                catRepository.save(cat);
                                petsOwner.setId(petId);
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        """
                                                Если Вы уверены в своём решении введите или нажмите команду
                                                /Booking
                                                """));
                            } else if (cat.getOwnerId() != null) {
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        """
                                                Этот питомец был забронирован ранее. Проверьте ID питомца или свяжитесь с волонтёром
                                                /Volunteer
                                                /ID
                                                """));
                            } else if (cat == null) {
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        PET_NOT_FOUND));

                            }
                        } else {
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Формат ID неверный, введите числовое значение ID выбранного питомца, " +
                                            "нажмите или введите /ID"));
                        }
                    };
                    break;

                case "/Booking":
                    bookingCat(update);
                    return true;
            }
        }
        return false;
    }
    private Cat bookingCat (Update update) {
        petsOwner.setOwnerId(update.message().chat().id()); // присваиваем ID пользователя
        petsOwner.setStartDate(LocalDate.now());
        petsOwner.setEndDate(LocalDate.now().plusDays(30));
        petsOwnerServiceImpl.save(petsOwner);
        catRepository.save(cat); // обновляем кошку
        telegramBot.execute(new SendMessage(update.message().chat().id(),
                "Питомец " + cat.toString() + "\n" +
                        " забронирован за Вами. Скоро с Вами свяжется волонтёр, чтобы " +
                        "обсудить подробности переезда питомца в Ваш дом и " +
                        "оформить документы!"));
        return cat;
    }
}

