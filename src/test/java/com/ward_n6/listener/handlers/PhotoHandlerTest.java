package com.ward_n6.listener.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.ward_n6.repository.PhotoRepository;
import com.ward_n6.service.OwnerReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhotoHandlerTest {
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private OwnerReportServiceImpl ownerReportServiceImpl;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализируем моки и спай-объекты
    }
    @InjectMocks
    private PhotoHandler photoHandler = new PhotoHandler(telegramBot, photoRepository, ownerReportServiceImpl);
    Update update = mock(Update.class);

    private final PhotoSize[] photoSizes = new PhotoSize[1];


    @Test
    public void handleTest() throws IOException, TelegramApiException {
        when(update.message().photo()).thenReturn(photoSizes);
        when(update.message().chat().id()).thenReturn(123L);
        when(photoSizes.length).thenReturn(1);
        when(photoSizes[0].fileId()).thenReturn("photo_file_id");

        // вызов метода
        photoHandler.handle(update);

        // проверка вызова ожидаемых методов
        verify(telegramBot).execute(new SendMessage(123L, "Фото загружено."));
        verify(telegramBot).execute(new GetFile("photo_file_id"));
        verify(telegramBot).getFullFilePath(any());
        verify(photoRepository).save(any());
    }
}
