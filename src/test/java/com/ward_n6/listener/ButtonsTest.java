package com.ward_n6.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ButtonsTest {

    @Mock
    TelegramBot bot;

          @InjectMocks
    TelegramBotPetShelterUpdatesListener listener;

            @Captor
            ArgumentCaptor<SendMessage> captor;

            @Test
            public void testInit() {
                listener.init();
            }

            @Test
    void name() {
                var upd = Mockito.<Update>mock(Update.class);
                var callback = mock(CallbackQuery.class);
                var message = mock(Message.class);
                var chat = mock(Chat.class);
                when(upd.callbackQuery()).thenReturn(callback);
                when(callback.data()).thenReturn("КНОПКА_ПРИЮТ_ДЛЯ_СОБАК");
                when(callback.message()).thenReturn(message);
                when(message.chat()).thenReturn(chat);
                when(chat.id()).thenReturn(1L);

                listener.process(List.of(upd));

                verify(bot).execute(captor.capture());

                var value = captor.getValue();

                assertEquals(value.getParameters().get("chat_id"), 1L); // проверили, что чат айди поставился верно
                assertEquals(value.getParameters().get("text"), "Вы выбрали приют для собак"); // проверили, что текст верный, однако сейчас текст записан строкой прям в коде, поэтому имеет смысл вынести все тексты сообщений в отдельные класс констант, в тестах будет проще
                assertEquals(value.getParameters().get("reply_markup"), ""); // проверили, что клавиатура поставилась верно, аналогичный момент, можно вынести создание клавиатуры в отдельный метод, чтобы в тестах можно было вызвать этот метод
                // вставить вторым аргументов вызов метода, который создает клавиатуру
            }
}