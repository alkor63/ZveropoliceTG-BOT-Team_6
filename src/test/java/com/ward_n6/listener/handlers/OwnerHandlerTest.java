package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.service.OwnerServiceImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class OwnerHandlerTest {


    @Test
    public void testHandle() {
        // Создание моков для зависимостей
        TelegramBot telegramBot = mock(TelegramBot.class);
        OwnerServiceImpl ownerServiceImpl = mock(OwnerServiceImpl.class);


        // Создание экземпляра класса, который содержит метод handle(Update update)
        OwnerHandler ownerHandler = new OwnerHandler(ownerServiceImpl, telegramBot);

        // Создание мок объекта Update
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/ln");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123456789L);

        // Вызов метода для тестирования
        ownerHandler.handle(update);

        // Проверка вызовов методов для зависимостей
        verify(telegramBot).execute(any(SendMessage.class));
        verify(ownerServiceImpl).save(any(Owner.class));

    }
}