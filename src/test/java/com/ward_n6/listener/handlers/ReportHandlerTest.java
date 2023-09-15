package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ReportHandlerTest {
    @Mock
    private TelegramBot telegramBot;
    @InjectMocks
    private ReportHandler reportHandler;

    @BeforeEach
    void setUp() {
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(mock(SendResponse.class));
    }
    @Test
    public void testHandle() {
        // Создание моков для зависимостей
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        // Установка поведения моков
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/ID");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123456789L);

        // Вызов метода для тестирования
        reportHandler.handle(update);
        // Проверка вызовов методов для зависимостей
        verify(telegramBot).execute(any(SendMessage.class));
    }
}