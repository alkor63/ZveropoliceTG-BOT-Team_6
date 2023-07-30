package com.ward_n6.Keyboard;


import com.ward_n6.enums.Buttons;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    // создаём лист кнопок из енума кнопок
    private static final List<String> MENU_BUTTONS = Arrays.asList(Buttons.DOGS.getTitle(), Buttons.CATS.getTitle(), Buttons.VOLUNTEER.getTitle());

    public void createMenu(long chatId) {
        // список кнопок меню
        List<String> menuButtons = MENU_BUTTONS;

        // создаем объект клавиатуры
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        // создаем список строк кнопок
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String button : menuButtons) {
            KeyboardRow row = new KeyboardRow();
            row.add(button);
            keyboard.add(row);
        }

        // добавляем список кнопок в клавиатуру
        keyboardMarkup.setKeyboard(keyboard);

        // отправляем сообщение с меню
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText("Выберите пункт меню:")
                .setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private Buttons commands;

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    // попытка создать клавиатуру
    public static SendMessage buttons(long chatId, boolean isStart) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton(); // добавляем кнопку /volun
        inlineKeyboardButton1.setText("/dogs");
        inlineKeyboardButton1.setCallbackData("Вас приветствует приют для собак. " +
                "Выберите интересующий Вас пункт меню.");
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("/cats").setCallbackData("Вас приветствует приют для кошек. " +
                "Выберите интересующий Вас пункт меню."));
        keyboardButtonsRow2.add(inlineKeyboardButton3); // добавляем кнопку /volun в новую строку
        keyboardButtonsRow2.add(new InlineKeyboardButton().setText("/cats").setCallbackData("Вы позвали волонтёра приюта. Ожидайте, с Вами свяжутся " +
                "в течение 30 мин."));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        if (isStart) {
            return new SendMessage(chatId, "Выберите интересующий Вас пункт меню:");
        } else {
            return new SendMessage(chatId, "Позвать волонтёра");
        }
    }


}
