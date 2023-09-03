package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.ward_n6.service.BotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatMessagerTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private BotMessageService botMessageService;
    @Captor
    private ArgumentCaptor<SendMessage> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализируем моки и спай-объекты

        telegramBot = mock(TelegramBot.class);
    }
    @InjectMocks
    private ChatMessager chatMessager;
    Long id = 111L;
    String text = "Сообщение";


    @Test
    void sendMessage() {

        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(111L);

        SendResponse sendResponse = mock(SendResponse.class);
        when(sendResponse.isOk()).thenReturn(true); // Возвращаем true для метода isOk()

        when(telegramBot.execute(any(SendMessage.class))).thenReturn(sendResponse); // Настройка мока telegramBot

        chatMessager.sendMessage(id, text);

        verify(telegramBot, times(1)).execute(captor.capture());
        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("text"), text);
        assertEquals(sendMessage.getParameters().get("chat_id"), id);
    }
}
