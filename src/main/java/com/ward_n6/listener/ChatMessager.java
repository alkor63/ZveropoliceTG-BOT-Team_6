package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.ward_n6.entity.BotMessaging;
import com.ward_n6.service.BotMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ChatMessager {
    private final TelegramBot telegramBot;
    private final BotMessageService botMessageService;

    public ChatMessager(TelegramBot telegramBot, BotMessageService botMessageService) {
        this.telegramBot = telegramBot;
        this.botMessageService = botMessageService;
    }

    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);

    // сохаранение сообщения в БД
    public void saveMessages(long chatId, String messageText) {
        if (messageText.isEmpty()) { // обрабатываем нулловое значение из парсинга
            sendMessage(chatId, "Неверный формат сообщения");
        } else {
            BotMessaging botMessaging = new BotMessaging();
            botMessaging.setChatId(chatId);
            botMessaging.setBotMessage(messageText);
            botMessageService.save(botMessaging); // сохранили наше сообщение в БД
        }
    }

    // ОТПРАВКА ОТВЕТА БОТА:
    public void sendMessage(long chatId, String message) { // выносим отправку в отдельный метод
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse sendResponse = telegramBot.execute(sendMessage); // сохраняем в переменную sendMessage
        if (!sendResponse.isOk()) { // если отправка сообщения не удалась
            logger.error("Ошибка отправки сообщения: {}", sendResponse.description()); // сообщаем об ошибке
        }
    }
}

