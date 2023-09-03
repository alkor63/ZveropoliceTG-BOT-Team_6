package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.repository.PhotoRepository;
import com.ward_n6.service.OwnerReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.mockito.Mockito.*;
//ТЕСТЫ В РАБОТЕ
class PhotoHandlerTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private OwnerReportServiceImpl ownerReportServiceImpl;

    @Test
    public void handleTest() throws IOException, TelegramApiException {
        // create mock objects
        telegramBot = mock(TelegramBot.class);
        photoRepository = mock(PhotoRepository.class);
        Update update = mock(Update.class);
        PhotoSize[] photoSizes = new PhotoSize[1];
        photoSizes[0] = mock(PhotoSize.class);
        when(update.message().photo()).thenReturn(photoSizes);

        // provide values for mocked objects
        when(update.message().chat().id()).thenReturn(123456789L);
        when(photoSizes.length).thenReturn(1);
        when(photoSizes[0].fileId()).thenReturn("photo_file_id");

        // create instance of the class under test
        PhotoHandler photoHandler = new PhotoHandler(telegramBot, photoRepository, ownerReportServiceImpl);

        // call the method to be tested
        photoHandler.handle(update);

        // verify that the expected methods were called with the expected arguments
        verify(telegramBot).execute(new SendMessage(123456789L, "Фото загружено."));
        verify(telegramBot).execute(new GetFile("photo_file_id"));
        verify(telegramBot).getFullFilePath(any());
        verify(photoRepository).save(any());
    }
}
