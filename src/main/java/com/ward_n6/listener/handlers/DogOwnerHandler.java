package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.PetsOwner;
import com.ward_n6.entity.pets.Dog;
import com.ward_n6.listener.ChatMessager;
import com.ward_n6.listener.PetsOwnerFactories;
import com.ward_n6.repository.pets.DogRepository;
import com.ward_n6.service.owners.PetsOwnerServiceImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.Consumer;

import static com.ward_n6.listener.MessageStringsConstants.PET_ID_REQUEST_FOR_PET_BOOKING;
@Component
public class DogOwnerHandler implements EventHandler {
    private final PetsOwnerServiceImpl petsOwnerServiceImpl;
    private final TelegramBot telegramBot;
    private PetsOwner petsOwner = new PetsOwner();
    private Dog dog = new Dog();
    private final DogRepository dogRepository;
    private PetsOwnerFactories petsOwnerFactories;
    private Consumer<Update> actionOnNextMessage;
    private ChatMessager chatMessager;
    private String PET_NOT_FOUND = "Питомец с указанным ID отсутствует в нашем приюте. Уточните ID интересующего Вас питомца. \n /ID";

    public DogOwnerHandler(PetsOwnerServiceImpl petsOwnerServiceImpl, TelegramBot telegramBot, DogRepository dogRepository, PetsOwnerFactories petsOwnerFactories, ChatMessager chatMessager) {
        this.petsOwnerServiceImpl = petsOwnerServiceImpl;
        this.telegramBot = telegramBot;

        this.dogRepository = dogRepository;
        this.petsOwnerFactories = petsOwnerFactories;
        this.chatMessager = chatMessager;
    }

    private static long petId;

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
                        if (!idMessage.matches("\\d+")) {
                            telegramBot.execute(new SendMessage(update.message().chat().id(),
                                    "Формат ID неверный, введите числовое значение ID выбранного питомца, " +
                                            "нажмите или введите /ID"));
                        }// проверяем, что число
                        else {
                            petId = Long.parseLong(idMessage); // парсим строку в число
                            dog = petsOwnerFactories.dogFactory(petId, ownerId);
                            if (dog != null && (dog.getOwnerId() == null || dog.getOwnerId() == 0)) { // проверяем, что такая собака есть в приюте
                                dog.setOwnerId(ownerId); // записываем id овнера питомцу
                                petsOwner.setId(petId); // записываем ID питомца

                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        """
                                                Если Вы уверены в своём решении введите или нажмите команду
                                                /Booking
                                                """));
                            } else if (petsOwnerFactories.petsOwnerFactory(petId) != null) {
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        """
                                                Этот питомец был забронирован ранее. Проверьте ID питомца или свяжитесь с волонтёром
                                                /Volunteer
                                                /ID
                                                """));

                            } else if (dog == null) {
                                telegramBot.execute(new SendMessage(update.message().chat().id(),
                                        PET_NOT_FOUND));
                            }
                        }
                    };
                    break;
                case "/Booking":
                    bookingDog(update);
                    return true;
            }
        }
        return false;
    }
    // ************* методы для бронирования
    private Dog bookingDog (Update update) {
        long ownerId = update.message().chat().id();
        petsOwner.setOwnerId(ownerId); // присваиваем ID пользователя
        // записываем ID питомца
        petsOwner.setStartDate(LocalDate.now());
        petsOwner.setEndDate(LocalDate.now().plusDays(30));
        petsOwnerServiceImpl.save(petsOwner);
        dogRepository.save(dog);
        telegramBot.execute(new SendMessage(update.message().chat().id(),
                "Питомец " + dog.toString() +
                        " забронирован за Вами. Скоро с Вами свяжется волонтёр, чтобы " +
                        "обсудить подробности переезда питомца в Ваш дом и " +
                        "оформить документы!"));
        return dog;
    }
}

