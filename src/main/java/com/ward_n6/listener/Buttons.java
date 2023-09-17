package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

import static com.ward_n6.listener.MessageStringsConstants.*;
@Component
public class Buttons extends Keyboards {
    private final TelegramBot telegramBot;
    public Buttons(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;


    }

    public void afterStartMenu(long chatId) {
        Keyboards keyboards = new Keyboards();
        SendMessage sendMessage = new SendMessage(chatId, "Здравствуйте. Это чат-бот приюта для животных. \n Какой приют Вас интересует?");
        afterStartMenuKeyboard();
        sendMessage.replyMarkup(keyboards.afterStartMenuKeyboard());
        telegramBot.execute(sendMessage);
    }

    // КОШКИ
    public void catButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для кошек!\n" + CAT_DESCRIPTION);
        Keyboards keyboards = new Keyboards();
        catButtonKeyboard();
        sendMessage.replyMarkup(keyboards.catButtonKeyboard());
        telegramBot.execute(sendMessage);
    }

    //СОБАКИ
    public void dogButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Вы выбрали приют для собак!\n" + DOG_DESCRIPTION);
        Keyboards keyboards = new Keyboards();
        dogButtonKeyboard();
        sendMessage.replyMarkup(keyboards.dogButtonKeyboard());
        telegramBot.execute(sendMessage);
    }

    //INFO DOG
    public void dogInfoButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, SHELTER_INFO);
        Keyboards keyboards = new Keyboards();
        dogInfoButtonKeyboard();
        sendMessage.replyMarkup(keyboards.dogInfoButtonKeyboard());
        telegramBot.execute(sendMessage);
    }

    // INFO CAT
    public void catInfoButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, SHELTER_INFO);
        Keyboards keyboards = new Keyboards();
        catInfoButtonKeyboard();
        sendMessage.replyMarkup(keyboards.catInfoButtonKeyboard());
        telegramBot.execute(sendMessage);
    }

    //TAKE PET DOG
    public void wantToTakeDogButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_TO_TAKE_PET));
        Keyboards keyboards = new Keyboards();
        wantToTakeDogButtonKeyboard();
        sendMessage.replyMarkup(keyboards.wantToTakeDogButtonKeyboard());
        telegramBot.execute(sendMessage);
    }

    //TAKE PET CAT
    public void wantToTakeCatButton(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_TO_TAKE_PET));
        Keyboards keyboards = new Keyboards();
        wantToTakeCatButtonKeyboard();
        sendMessage.replyMarkup(keyboards.wantToTakeCatButtonKeyboard());
        telegramBot.execute(sendMessage);
    }

    //ВОЛОНТЁР
    public void callVoluntier(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(":white_check_mark:Через " +
                "некоторое время с Вами свяжется волонтёр и ответит на интересующие вопросы!:wink:"));
        telegramBot.execute(sendMessage);
    }

    // ОТЧЁТ
    public void sendOwnerHowReport(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(HOW_REPORT));
        TelegramBotPetShelterUpdatesListener.reportSelect = true;
        telegramBot.execute(sendMessage);
    }

    // ДАННЫЕ УСЫНОВИТЕЛЯ
    public void sendOwnerData(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode("""
                :white_check_mark: Введите или нажмите команду
                 /myData"""
        ));
        TelegramBotPetShelterUpdatesListener.getOwnerDataSelect = true;
        telegramBot.execute(sendMessage);
    }

    // БРОНИРОВАНИЕ ПИТОМЦА
    public void reservePet(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "Чтобы забронировать питомца, " +
                "введите или нажмите команду:\n /takePet");
        telegramBot.execute(sendMessage);
    }

    // РЕКОМЕНДАЦИИ ДЛЯ КОШЕК
    public void dogRecommendation(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(DOG_RECOMMEDATION));
        telegramBot.execute(sendMessage);
    }

    // РЕКОМЕНДАЦИИ ДЛЯ СОБАК
    public void catRecommendation(long chatId) {
        SendMessage sendMessage = new SendMessage(chatId, EmojiParser.parseToUnicode(CAT_RECOMMENDATION));
        telegramBot.execute(sendMessage);

    }



}
