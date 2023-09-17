package com.ward_n6.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.entity.BotMessaging;
import com.ward_n6.repository.BotMessagingRepository;
import com.ward_n6.service.BotMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(ChatMessager.class)
class ChatMessagerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private TelegramBot telegramBot;
    @MockBean
    BotMessageService botMessageService;
    @Mock
    BotMessaging botMessaging;
    @Mock
    BotMessagingRepository botMessagingRepository;

    @Autowired
    ChatMessager chatMessager;
    @Captor
    private ArgumentCaptor<SendMessage> captor;




    //    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
////        telegramBot = new TelegramBot("6390193296:AAHILFc14KHNZxGQ-7s9GRr6ndZMJheBJyI");
//        Long chatId = 111L;
//        String text = "Сообщение";
//        telegramBot = mock(TelegramBot.class);
//    }
// ******************* отправка сообщения **********
    @Test
    public void testSendMessage() {
       long chatId=11L;
        String text="Hello, world!";
        ChatMessager chatMessager = new ChatMessager(telegramBot, botMessageService);
//        SendMessage sendMessage = new SendMessage(chatId, text);
//        SendResponse sendResponse = mock(SendResponse.class);
//        when(telegramBot.execute( sendMessage)).thenReturn(sendResponse.);
        chatMessager.sendMessage(chatId, text);
        verify(telegramBot, times(1)).execute(captor.capture());

        var sendMessage = captor.getValue();

        assertEquals(sendMessage.getParameters().get("Сообщение"), text);
        assertEquals(sendMessage.getParameters().get("111L"), chatId);
    }

    // ******************* сохранение сообщения **********
    @Test
    public void testSaveMessagesWhenMessageTextIsNotEmpty() {
        long chatId = 123456789;
        String messageText = "text!";
        BotMessaging botMessaging = new BotMessaging( );
        when(botMessagingRepository.save(botMessaging)).thenReturn(botMessaging);

        chatMessager.saveMessages(chatId, messageText);

        verify(botMessageService, times(1)).save(any(BotMessaging.class));
    }

    @Test
    public void testSaveMessagesWhenMessageTextIsEmpty() {

        long chatId = 123456789;
        String messageText = "";
        BotMessaging botMessaging = new BotMessaging( );
//        doNothing()
        chatMessager.saveMessages(chatId, messageText);

//        verify(telegramBot, times(1)).execute(captor.capture());
        verify(botMessageService, never()).save(botMessaging);
//        verify(chatMessager, times(1)).sendMessage(chatId, "Неверный формат сообщения");

    }

}

