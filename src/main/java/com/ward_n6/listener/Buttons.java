package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

import static com.ward_n6.listener.MessageStringsConstants.*;
@Component
public class Buttons {
    private final TelegramBot telegramBot;

    public Buttons(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void afterStartMenu(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Здравствуйте. " +
                "Это чат-бот приюта для животных. " +
                "Какой приют Вас интересует?");
        InlineKeyboardButton chooseDogHouseButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Приют для собак" + ":dog:"));
        chooseDogHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_СОБАК");
        InlineKeyboardButton chooseCatHouseButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Приют для кошек" + ":cat:"));
        chooseCatHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК");
        Keyboard keyboard = new InlineKeyboardMarkup(chooseDogHouseButton, chooseCatHouseButton);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    // КОШКИ
    public void catButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для кошек!\n" + CAT_DESCRIPTION);
        InlineKeyboardButton catHouseInfoButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        catHouseInfoButton.callbackData("ИНФО_КОШКИ");
        InlineKeyboardButton userRegisterData = new InlineKeyboardButton("РЕГИСТРАЦИЯ В НАШЕЙ БАЗЕ ДАННЫХ");
        userRegisterData.callbackData("РЕГИСТРАЦИЯ_ПОЛЬЗОВАТЕЛЯ");
        InlineKeyboardButton catHouseHowToTakeButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Как взять кошку из приюта " + ":cat:"));
        catHouseHowToTakeButton.callbackData("КАК_ЗАБРАТЬ_КОШКУ");
        InlineKeyboardButton catHouseOwnerReportButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Прислать отчёт о питомце " + ":memo:"));
        catHouseOwnerReportButton.callbackData("ОТЧЁТ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(catHouseInfoButton)
                .addRow(catHouseHowToTakeButton)
                .addRow(catHouseOwnerReportButton)
                .addRow(userRegisterData)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //СОБАКИ
    public void dogButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для собак!\n" + DOG_DESCRIPTION);
        InlineKeyboardButton dogHouseInfoButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Информация о приюте " + ":information_source:"));
        dogHouseInfoButton.callbackData("ИНФО_СОБАКИ");
        InlineKeyboardButton dogHouseHowToTakeButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Как взять собаку из приюта " + ":dog:"));
        dogHouseHowToTakeButton.callbackData("КАК_ЗАБРАТЬ_СОБАКУ");
        InlineKeyboardButton dogHouseOwnerReportButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Прислать отчёт о питомце " + ":memo:"));
        dogHouseOwnerReportButton.callbackData("ОТЧЁТ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        InlineKeyboardButton userRegisterData = new InlineKeyboardButton("РЕГИСТРАЦИЯ В НАШЕЙ БАЗЕ ДАННЫХ");
        userRegisterData.callbackData("РЕГИСТРАЦИЯ_ПОЛЬЗОВАТЕЛЯ");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(dogHouseInfoButton)
                .addRow(dogHouseHowToTakeButton)
                .addRow(dogHouseOwnerReportButton)
                .addRow(userRegisterData)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //INFO DOG
    public void dogInfoButton(long chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, petShelter.getDescription()+"\n"+petShelter.getShelterAddress()+"\n"+petShelter.getSecurityContact()+"\n"+petShelter.getSafetyAdvice()+"\n"+petShelter.getHelpShelter());
        SendMessage sendMessage = new SendMessage(chatId, SHELTER_INFO);
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    // INFO CAT
    public void catInfoButton(long chatId) {
//        SendMessage sendMessage = new SendMessage(chatId, petShelter.getDescription()+"\n"+petShelter.getShelterAddress()+"\n"+petShelter.getSecurityContact()+"\n"+petShelter.getSafetyAdvice()+"\n"+petShelter.getHelpShelter());
        SendMessage sendMessage = new SendMessage(chatId, SHELTER_INFO);
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //TAKE PET DOG
    public void wantToTakeDogButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_TO_TAKE_PET));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        InlineKeyboardButton recommendationDog = new InlineKeyboardButton(EmojiParser.parseToUnicode(":point_up: Рекомендации перед тем, как завести собаку! :point_up:"));
        recommendationDog.callbackData("РЕКОМЕНДАЦИЯ_СОБАКИ");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(recommendationDog)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //TAKE PET CAT
    public void wantToTakeCatButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_TO_TAKE_PET));
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        InlineKeyboardButton recommendationCat = new InlineKeyboardButton(EmojiParser.parseToUnicode(":point_up: Рекомендации перед тем, как завести кошку! :point_up:"));
        recommendationCat.callbackData("РЕКОМЕНДАЦИЯ_КОШКИ");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(recommendationCat)
                .addRow(callVoluntier);
        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);
    }

    //ВОЛОНТЁР
    public void callVoluntier(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(":white_check_mark:Через " +
                "некоторое время с Вами свяжется волонтёр и ответит на интересующие вопросы!:wink:"));
        telegramBot.execute(sendMessage);
    }
    public void dogRecommendation(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(DOG_RECOMMEDATION));
        telegramBot.execute(sendMessage);

    }
    public void catRecommendation(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(CAT_RECOMMENDATION));
        telegramBot.execute(sendMessage);

    }
    public void reservePet(long chatId){
        SendMessage sendMessage = new SendMessage(chatId, "Чтобы забронировать питомца, введите или нажмите команду:\n /takePet");
        telegramBot.execute(sendMessage);
    }

    public void registerUser(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Чтобы зарегистрироваться в нашей базе данных введите или нажмите команду:\n /myData");
        telegramBot.execute(sendMessage);
    }

    // ОТЧЁТ
    public void sendOwnerHowReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_REPORT));
//        InlineKeyboardButton textButton = new InlineKeyboardButton(EmojiParser
//                .parseToUnicode("Отправить текст :memo:"));
//        InlineKeyboardButton photoButton = new InlineKeyboardButton(EmojiParser
//                .parseToUnicode("Отправить фото :camera:"));
//        textButton.callbackData("ОТЧЁТ");
//        photoButton.callbackData("ОТЧЁТ_ФОТО");
//        Keyboard keyboard = new InlineKeyboardMarkup().addRow(textButton).addRow(photoButton);
//        sendMessage.replyMarkup(keyboard);
        telegramBot.execute(sendMessage);

    }



}
