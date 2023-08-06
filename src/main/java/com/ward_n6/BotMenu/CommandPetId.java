package com.ward_n6.BotMenu;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.ward_n6.entity.reports.OwnerReport;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import com.ward_n6.service.BotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class CommandPetId implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);
    {
    }
    private final TelegramBot telegramBot;
    private final BotMessageService botMessageService;
    private final OwnerReport ownerReport;


    public CommandPetId(TelegramBot telegramBot, BotMessageService botMessageService, OwnerReport ownerReport) {
        this.telegramBot = telegramBot;
        this.botMessageService = botMessageService;
        this.ownerReport = ownerReport;
    }




    public void petId(Update update) {

        Message message = update.message(); // получаем сообщение из текущего обновления
        Long chatId = message.chat().id(); // получаем идентификатор чата, к которому относится апдейт
        String messageText = message.text(); // получаем текст сообщения

        sendMessage(chatId, "Укажите ID Вашего питомца");
        String idMessage = message.text();
        if (idMessage.matches("\\d+")) {
            long petsId = Long.parseLong(messageText.split(" ")[1]);
            ownerReport.setPetId(petsId);
            sendMessage(chatId, "ID питомца записан.");
        } else {
            sendMessage(chatId, "Вы не указали ID питомца.");
        }
    }
        private void sendMessage(long chatId, String message) { // выносим отправку в отдельный метод
            SendMessage sendMessage = new SendMessage(chatId, message);
            SendResponse sendResponse = telegramBot.execute(sendMessage); // сохраняем в переменную sendMessage
            if (!sendResponse.isOk()) { // если отправка сообщения не удалась
                logger.error("Ошибка отправки сообщения: {}", sendResponse.description()); // сообщаем об ошибке
            }
        }

    @Override
    public int process(List<Update> list) {
        return 0;
    }
}

