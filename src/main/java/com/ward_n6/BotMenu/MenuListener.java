package com.ward_n6.BotMenu;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.ward_n6.listener.TelegramBotPetShelterUpdatesListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MenuListener implements UpdatesListener {
    private Logger logger = LoggerFactory.getLogger(TelegramBotPetShelterUpdatesListener.class);
    {
    }

    @Override
    public int process(List<Update> list) {
        return 0;
    }
}