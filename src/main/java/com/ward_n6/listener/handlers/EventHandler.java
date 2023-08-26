package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.model.Update;

public interface EventHandler {

    boolean handle(Update update);
}
