package com.ward_n6.Timer;
import com.pengrad.telegrambot.TelegramBot;
import com.ward_n6.repository.BotMessagingRepository;
import org.springframework.stereotype.Component;

@Component
public class OwnerTrialPriodTimer {
    // класс для отслкживания испытательного срока
    private final BotMessagingRepository botMessagingRepository;
    private final TelegramBot telegramBot;


    public OwnerTrialPriodTimer(BotMessagingRepository botMessagingRepository, TelegramBot telegramBot) {
        this.botMessagingRepository = botMessagingRepository;
        this.telegramBot = telegramBot;
    }

}

