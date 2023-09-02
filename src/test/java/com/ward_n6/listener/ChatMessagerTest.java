package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//ТЕСТЫ В РАБОТЕ
class ChatMessagerTest {
    @Mock
    private TelegramBot telegramBot;

   ChatMessager chatMessager = mock(ChatMessager.class);

    @Captor
    private ArgumentCaptor<SendMessage> captor;
    Long id = 111L;
    String text = "Сообщение";

    @Test
    void sendMessage() {
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(111L);
        chatMessager.sendMessage(id, text);
        verify(telegramBot, times(1)).execute(captor.capture());
        var sendMessage = captor.getValue();
        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chatId"),id);
    }

}