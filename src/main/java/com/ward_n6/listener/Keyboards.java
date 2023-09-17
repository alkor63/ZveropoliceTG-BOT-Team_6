package com.ward_n6.listener;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.vdurmont.emoji.EmojiParser;

public class Keyboards extends Keyboard {

    public Keyboards() {
    }

    public Keyboard afterStartMenuKeyboard() {
        InlineKeyboardButton chooseDogHouseButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Приют для собак" + ":dog:"));
        chooseDogHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_СОБАК");
        InlineKeyboardButton chooseCatHouseButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Приют для кошек" + ":cat:"));
        chooseCatHouseButton.callbackData("КНОПКА_ПРИЮТ_ДЛЯ_КОШЕК");
        Keyboard keyboard = new InlineKeyboardMarkup(chooseDogHouseButton, chooseCatHouseButton);

        return keyboard;
    }

    public Keyboard catButtonKeyboard() {
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

        InlineKeyboardButton chooseOwnerDataButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Оставить контакты" + ":white_check_mark:"));
        chooseOwnerDataButton.callbackData("ЗАРЕГИСТРИРОВАТЬСЯ_В_ПРИЮТЕ");

        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(catHouseInfoButton)
                .addRow(catHouseHowToTakeButton)
                .addRow(catHouseOwnerReportButton)
                .addRow(callVoluntier)
                .addRow(chooseOwnerDataButton);
        return keyboard;

    }

    public Keyboard dogButtonKeyboard() {
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

        InlineKeyboardButton chooseOwnerDataButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Оставить контакты" + ":white_check_mark:"));
        chooseOwnerDataButton.callbackData("ЗАРЕГИСТРИРОВАТЬСЯ_В_ПРИЮТЕ");

        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(dogHouseInfoButton)
                .addRow(dogHouseHowToTakeButton)
                .addRow(dogHouseOwnerReportButton)
                .addRow(callVoluntier)
                .addRow(chooseOwnerDataButton);
        return keyboard;

    }

    public Keyboard dogInfoButtonKeyboard() {
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        return keyboard;

    }

    public Keyboard catInfoButtonKeyboard() {
        InlineKeyboardButton wantToHelpButton = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":two_hearts: " + "Я хочу пожертвовать в приют ".toUpperCase() + ":two_hearts:"));
        wantToHelpButton.callbackData("СДЕЛАТЬ_ПОЖЕРТВОВАНИЕ");
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(wantToHelpButton)
                .addRow(callVoluntier);
        return keyboard;

    }

    public Keyboard wantToTakeDogButtonKeyboard() {
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        InlineKeyboardButton recommendationDog = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":point_up: Рекомендации перед тем, как завести собаку! :point_up:"));
        recommendationDog.callbackData("РЕКОМЕНДАЦИЯ_СОБАКИ");

        InlineKeyboardButton chooseDog = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Забрать собаку" + ":dog:"));
        chooseDog.callbackData("ЗАБРОНИРОВАТЬ_ПИТОМЦА");
        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(recommendationDog)
                .addRow(callVoluntier)
                .addRow(chooseDog);
        return keyboard;

    }

    public Keyboard wantToTakeCatButtonKeyboard() {
        InlineKeyboardButton callVoluntier = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Позвать волонтёра " + ":necktie:"));
        callVoluntier.callbackData("ВОЛОНТЁР");
        InlineKeyboardButton recommendationCat = new InlineKeyboardButton(EmojiParser
                .parseToUnicode(":point_up: Рекомендации перед тем, как завести кошку! :point_up:"));
        recommendationCat.callbackData("РЕКОМЕНДАЦИЯ_КОШКИ");

        InlineKeyboardButton chooseCat = new InlineKeyboardButton(EmojiParser
                .parseToUnicode("Забрать кошку" + ":cat:"));
        chooseCat.callbackData("ЗАБРОНИРОВАТЬ_ПИТОМЦА");

        Keyboard keyboard = new InlineKeyboardMarkup()
                .addRow(recommendationCat)
                .addRow(callVoluntier)
                .addRow(chooseCat);
        return keyboard;

    }
}
