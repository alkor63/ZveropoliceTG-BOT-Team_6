package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.OwnerService;

import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OwnerHandler implements EventHandler {
    private final OwnerService ownerService;
    private final TelegramBot bot;
    private Owner owner1 = new Owner();

    public OwnerHandler(OwnerService ownerService, TelegramBot bot) {
        this.ownerService = ownerService;
        this.bot = bot;
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
                bot.execute(new SendMessage(update.message().chat().id(), "Укажите Вашу фамилию с заглавной буквы"));
                actionOnNextMessage = upd -> {
                    owner1.setLastName(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Фамилия записана!"));
                };
                break;

            case "/fn":
                bot.execute(new SendMessage(update.message().chat().id(), "Укажите Ваше имя с заглавной буквы"));
                actionOnNextMessage = upd -> {
                    owner1.setFirstName(upd.message().text());
                    bot.execute(new SendMessage(update.message().chat().id(), "Имя записано!"));
                };
                break;


            case "/phone":
                Matcher matcher = OWNER_PHONE_NUMBER_PATTERN.matcher(update.message().text());
                bot.execute(new SendMessage(update.message().chat().id(), "Укажите Ваш телефон для связи в формате: " +
                        "" + "\n" + "8-XXX-XXX-XX-XX"));
                actionOnNextMessage = upd -> {
//                    if (matcher.matches()) {
                        owner1.setPhoneNumber(upd.message().text());
                        bot.execute(new SendMessage(update.message().chat().id(), "Номер записан!"));
//                    } else {
//                        bot.execute(new SendMessage(update.message().chat().id(), "Телефон указан неверно, попробуйте ещё раз"));
//                    }
                };
                break;

            case "/save":

                owner1.setOwnerId( update.message().chat().id().longValue());
                ownerService.save(owner1);
                bot.execute(new SendMessage(update.message().chat().id(), "Ваши данные " + owner1.toString() + "добавлены в нашу базу. " +
                        "Для удаления данных из нашей базы обратитесь в волонтёру"));
                return true;

//            case "/delete" -> {
//              int id = Integer.parseInt(update.message().chat().id());
//                ownerService.deleteOwnerById(update.message().chat().id());
//                bot.execute(new SendMessage(update.message().chat().id(), "Ваши данные " + owner1.toString() + "добавлены в нашу базу." +
//                        "Для удаления данных из нашей базы обратитесь в волонтёру"));
//                return true;
//            }
        }
        return false;
    }
}


