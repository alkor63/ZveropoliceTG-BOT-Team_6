package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.repository.Impl.OwnerServiceImpl;

import java.util.function.Consumer;
import java.util.regex.Pattern;

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
        var text = update.message().text();
        switch (text) {
            case "/ln":
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Укажите Вашу фамилию с заглавной буквы"));
                actionOnNextMessage = upd -> {
                    owner.setLastName(upd.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Фамилия записана!" +
                            """
                                    Команды для записи контактных данных:
                                1. /ln - указать фамилию
                                2. /fn - указать имя
                                3. /phone - указать номер телефона
                                4. /save - сохранить мои контактные данные в базе приюта
                                5. /delete - удалить мои данные
                                        """
                    ));
                };
                break;

            case "/fn":
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Укажите Ваше имя с заглавной буквы"));
                actionOnNextMessage = upd -> {
                    owner.setFirstName(upd.message().text());
                    telegramBot.execute(new SendMessage(update.message().chat().id(), "Имя записано!" +
                            """
                                    Команды для записи контактных данных:
                                1. /ln - указать фамилию
                                2. /fn - указать имя
                                3. /phone - указать номер телефона
                                4. /save - сохранить мои контактные данные в базе приюта
                                5. /delete - удалить мои данные
                                        """
                    ));
                };
                break;

            case "/phone":
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Укажите Ваш телефон для связи в формате: " +
                        "" + "\n" + "8-XXX-XXX-XX-XX" +
                        """
                                Команды для записи контактных данных:
                                1. /ln - указать фамилию
                                2. /fn - указать имя
                                3. /phone - указать номер телефона
                                4. /save - сохранить мои контактные данные в базе приюта
                                5. /delete - удалить мои данные
                                """));
                actionOnNextMessage = upd -> {
                    var phoneMessage = upd.message().text();
                     if (phoneMessage.matches("8-d{3}-d{3}-d{2}-d{2}")) {
                        owner.setPhoneNumber(upd.message().text());
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Номер записан!"));
                    } else {
                        telegramBot.execute(new SendMessage(update.message().chat().id(), "Телефон указан неверно, попробуйте ещё раз"));
                    }
                };
                break;

            case "/save":

                owner.setOwnerId(update.message().chat().id().longValue());
                ownerServiceImpl.save(owner);
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши данные " + "\n" + owner.toString()
                        + "добавлены в нашу базу. Для удаления данных из нашей базы обратитесь в волонтёру"));

                return true;


            case "/delete":
//                telegramBot.execute(new SendMessage(update.message().chat().id(), "К сожалению, эта функция пока не работает. " +
//                        "Для удаления данных из нашей базы обратитесь в волонтёру."));
                long ownerId = update.message().chat().id();

                ownerServiceImpl.deleteById(ownerId);
                telegramBot.execute(new SendMessage(update.message().chat().id(), "Ваши данные удалены из базы."));
                return true;

        }

        return false;
    }
}

