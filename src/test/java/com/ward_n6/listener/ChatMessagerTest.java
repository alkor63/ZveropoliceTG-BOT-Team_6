package com.ward_n6.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.service.BotMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


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
    private ChatMessager chatMessager = new ChatMessager(telegramBot, botMessageService);
    @Captor
    private ArgumentCaptor<SendMessage> captor;


    private long chatId;
    private String text;

//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
////        telegramBot = new TelegramBot("6390193296:AAHILFc14KHNZxGQ-7s9GRr6ndZMJheBJyI");
//        Long chatId = 111L;
//        String text = "Сообщение";
//        telegramBot = mock(TelegramBot.class);
//    }




    @Test
    public void testSendMessage() {
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
}

