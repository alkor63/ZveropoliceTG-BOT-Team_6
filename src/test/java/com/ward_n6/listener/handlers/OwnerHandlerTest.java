package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import com.ward_n6.entity.owners.Owner;
import com.ward_n6.listener.MessageStringsConstants;
import com.ward_n6.service.OwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerHandlerTest {
    // Создание моков для зависимостей
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private OwnerServiceImpl ownerServiceImpl;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    private MessageStringsConstants messageStringsConstants;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализируем моки и спай-объекты
//        telegramBot = mock(TelegramBot.class);

//        message = mock(Message.class);
//        chat = mock(Chat.class);
//        ownerServiceImpl = mock(OwnerServiceImpl.class);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(mock(SendResponse.class));
    }
//    @InjectMocks
//    private OwnerHandler ownerHandler = new OwnerHandler(ownerServiceImpl, telegramBot);

    @Test
    public void testHandle() {
        Update update = mock(Update.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/ln");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123456789L);
        OwnerHandler ownerHandler = new OwnerHandler(ownerServiceImpl, telegramBot);
        // Вызов метода для тестирования
        ownerHandler.handle(update);

        // Проверка вызовов методов для зависимостей
        verify(telegramBot).execute(any(SendMessage.class));
        verify(ownerServiceImpl).save(any(Owner.class));

    }
}