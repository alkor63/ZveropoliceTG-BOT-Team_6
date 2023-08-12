package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.BotMessaging;
import com.ward_n6.entity.owners.OwnerReport;
import com.ward_n6.service.BotMessageService;
import org.springframework.stereotype.Service;
@Service
public class GiveReport {
    private final BotMessageService botMessageService;
    private final TelegramBot telegramBot;
    private OwnerReport ownerReport;

    public GiveReport(BotMessageService botMessageService, TelegramBot telegramBot) {
        this.botMessageService = botMessageService;
        this.telegramBot = telegramBot;
    }

    /**
     * Отчёт:
     * имя
     * фото
     * рацион
     * поведение
     * id питомца
     */
    boolean startReport = false;

    public OwnerReport giveReport(long chatId, String message) {
        OwnerReport ownerReport = new OwnerReport();
        BotMessaging botMessaging = new BotMessaging();

        startReport = true;
        SendMessage sendMessage = new SendMessage(chatId, "Для отправки отчёта следуйте рекомендациям бота. " +
                "Нажмите кнопку /report");
        SendMessage sendMessage1 = new SendMessage(chatId, "Опишите поведение питомца");
        ownerReport.setPetsBehavior(message);

        if (!message.isEmpty()) {
            SendMessage sendMessage2 = new SendMessage(chatId, "Опишите самочувствие питомца");
            ownerReport.setPetsHealth(message);

        }
        if (!message.isEmpty()) {
            SendMessage sendMessage3 = new SendMessage(chatId, "Опишите рацион питомца");
            ownerReport.setNutrition(message);

        }

        botMessaging.setChatId(chatId);
        botMessaging.setBotMessage(ownerReport.toString());


        return ownerReport;

    }


}

