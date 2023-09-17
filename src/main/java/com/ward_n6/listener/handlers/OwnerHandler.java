package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.owners.OwnerServiceImpl;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import static com.ward_n6.listener.MessageStringsConstants.PERSONAL_DATA_REQUEST;
@Getter
@Component
/**
 * класс для обработки и сохранения данных пользователя
 */
public class OwnerHandler implements EventHandler {
    private final OwnerServiceImpl ownerServiceImpl;
    private final TelegramBot telegramBot;
    private Owner owner = new Owner();

    public OwnerHandler(OwnerServiceImpl ownerServiceImpl, TelegramBot telegramBot) {
        this.ownerServiceImpl = ownerServiceImpl;
        this.telegramBot = telegramBot;
    }

    private static final Pattern OWNER_PHONE_NUMBER_PATTERN = Pattern.compile("8-\\d{3}-\\d{3}-\\d{2}-\\d{2}");
    private Consumer<Update> actionOnNextMessage; // переменная для определения действий над поступаемым сообщением

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
                case "/ln":
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Укажите Вашу фамилию с заглавной буквы"));
                    actionOnNextMessage = upd -> {
                        owner.setLastName(upd.message().text());
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Фамилия записана!" +
                                PERSONAL_DATA_REQUEST
                        ));
                    };
                    break;

                case "/fn":
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Укажите Ваше имя с заглавной буквы"));
                    actionOnNextMessage = upd -> {
                        owner.setFirstName(upd.message().text());
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Имя записано!" +
                                PERSONAL_DATA_REQUEST
                        ));
                    };
                    break;

                case "/phone":
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Укажите Ваш телефон для связи в формате: " +
                            "" + "\n" + "8-XXX-XXX-XX-XX " + PERSONAL_DATA_REQUEST));
                    actionOnNextMessage = upd -> {
                        String phoneMessage = upd.message().text();
                        if (phoneMessage.matches("8-\\d{3}-\\d{3}-\\d{2}-\\d{2}")) {
                            owner.setPhoneNumber(upd.message().text());
                            telegramBot.execute(new SendMessage(update.message().chat().id(), "Номер записан!"));
                        } else {
                            telegramBot.execute(new SendMessage(update.message().chat().id(), "Телефон указан неверно, попробуйте ещё раз \n /phone"));
                        }
                    };
                    break;

                case "/save":
                    owner.setId(update.message().chat().id().longValue());

                    ownerServiceImpl.save(owner);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши данные  \n" +
                            owner.toString() + " добавлены в нашу базу. Спасибо за регистрацию. \n" +
                            """ 
                                    Для обновления или удаления данных введите нажмите команду: 
                                    /myData 
                                    Для обновления записи укажите Ваши данные заново."""));

                    return true;

                case "/delete":

                    long ownerId = update.message().chat().id();
                    int id = (int) ownerId;

                    ownerServiceImpl.deleteById(ownerId);
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши данные удалены из базы."));
                    return true;
            }
        }
        return false;
    }
}


